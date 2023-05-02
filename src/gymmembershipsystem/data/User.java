package gymmembershipsystem.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import gymmembershipsystem.core.SQLConnection;
import gymmembershipsystem.table.TableUser;
import gymmembershipsystem.utilities.Hashing;
import gymmembershipsystem.types.UserType;

// import bcrypt for password hashing

public class User extends SQLConnection {

  private Connection sqlConnection;
  private final String TABLE_NAME = "User";

  public User() {
    try {
      this.sqlConnection = connect();
    } catch (Exception e) {
      throw new RuntimeException("Connection to database failed");
    }
  }

  public int createUser(String username, String password, String firstName, String lastName, String middleName,
      float weightInKg, float heightInFt, LocalDate birthDate, String gender, int role) throws Exception {

    String strongPassword = Hashing.get_SHA_256_SecurePassword(password);

    String query = "INSERT INTO " + TABLE_NAME
        + " (first_name, last_name, middle_name, weight_in_kg, height_in_ft, birthdate, gender, role, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query,
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, firstName);
      preparedStatement.setString(2, lastName);
      preparedStatement.setString(3, middleName);
      preparedStatement.setFloat(4, weightInKg);
      preparedStatement.setFloat(5, heightInFt);
      preparedStatement.setDate(6, Date.valueOf(birthDate));
      preparedStatement.setString(7, gender);
      preparedStatement.setInt(8, role);
      preparedStatement.setString(9, username);
      preparedStatement.setString(10, strongPassword);

      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Creating user failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);

          if (role == 1) {
            // create membership
            Membership membership = new Membership();
            membership.createMembership(id);
          } else {
            // create staff
            Staff staff = new Staff();
            staff.createStaff(id);
          }

          generatedKeys.close();
          preparedStatement.close();
          return id;
        } else {
          throw new Exception("Creating user failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }
  }

  public void updateUser(String firstName, String lastName, String middleName,
      float weightInKg, float heightInFt, LocalDate birthDate, String gender, int userId) throws Exception {

    String query = "UPDATE " + TABLE_NAME
        + " SET first_name = ?, last_name = ?, middle_name = ?, weight_in_kg = ?, height_in_ft = ?, birthdate = ?, gender = ? WHERE id = ?";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setString(1, firstName);
      preparedStatement.setString(2, lastName);
      preparedStatement.setString(3, middleName);
      preparedStatement.setFloat(4, weightInKg);
      preparedStatement.setFloat(5, heightInFt);
      preparedStatement.setDate(6, Date.valueOf(birthDate));
      preparedStatement.setString(7, gender);
      preparedStatement.setInt(8, userId);

      int affectedRows = preparedStatement.executeUpdate();

      if (affectedRows == 0) {
        throw new Exception("Updating user failed, no rows affected.");
      }

      preparedStatement.close();

    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }
  }

  public UserType getUserById(int id) throws Exception {
    String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        UserType user = new UserType();
        user.id = resultSet.getInt("id");
        user.firstName = resultSet.getString("first_name");
        user.lastName = resultSet.getString("last_name");
        user.middleName = resultSet.getString("middle_name");
        user.weightInKg = resultSet.getFloat("weight_in_kg");
        user.heightInFt = resultSet.getFloat("height_in_ft");
        user.gender = resultSet.getString("gender");
        user.birthDate = resultSet.getDate("birthdate").toLocalDate();
        user.role = resultSet.getInt("role");
        user.username = resultSet.getString("username");
        user.createdAt = resultSet.getDate("created_at").toLocalDate();
        user.updatedAt = resultSet.getDate("updated_at").toLocalDate();
        return user;
      }
      throw new Exception("User not found");
    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }
  }

  public UserType validateUserCredentials(String username, String password) throws Exception {
    String protectedPassword = Hashing.get_SHA_256_SecurePassword(password);
    String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, protectedPassword);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        UserType userType = new UserType();
        userType.id = resultSet.getInt("id");
        userType.role = resultSet.getInt("role");
        return userType;
      }
      throw new Exception("Invalid username or password");
    } catch (SQLException e) {
      throw new SQLException("There was an error on communicating with the database: " + e.getMessage());
    }
  }

  public ArrayList<TableUser> getAllUsers() throws Exception {
    // new TableUser("1", "John", "Doe", null, "75", "6.0", "1990-01-01", "Male"),
    // new TableUser("2", "Jane", "Doe", "M.", "65", "5.5", "1995-02-15", "Female")

    String query = "SELECT * FROM " + TABLE_NAME;
    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      ArrayList<TableUser> userLists = new ArrayList<TableUser>();

      while (resultSet.next()) {
        userLists.add(
            new TableUser(resultSet.getString("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("middle_name"),
                resultSet.getString("weight_in_kg"),
                resultSet.getString("height_in_ft"),
                resultSet.getString("birthdate"),
                resultSet.getString("gender")));
      }
      return userLists;
    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }
  }

  public void deleteUser(int userId) {
    String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setInt(1, userId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
