package gymmembershipsystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gymmembershipsystem.core.SQLConnection;

public class Membership extends SQLConnection {

  private Connection sqlConnection;
  private final String TABLE_NAME = "Member";

  public Membership() {
    try {
      this.sqlConnection = connect();
    } catch (Exception e) {
      throw new RuntimeException("Connection to database failed");
    }
  }

  public int createMembership(int userId) {

    String query = "INSERT INTO " + TABLE_NAME + " (user_id) VALUES (?)";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, userId);
      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Creating membership failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          generatedKeys.close();
          preparedStatement.close();
          return id;
        } else {
          throw new Exception("Creating membership failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on creating membership: " + e.getMessage());
    }
  }

  public int createMembership(int userId,
      int programId) {

    String query = "INSERT INTO " + TABLE_NAME
        + " (user_id, program_id) VALUES (?, ?)";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, programId);
      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Creating membership failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          generatedKeys.close();
          preparedStatement.close();
          return id;
        } else {
          throw new Exception("Creating membership failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on creating membership: " + e.getMessage());
    }

  }

  public int getMemberByUserId(int userId) {
    String query = "SELECT id FROM " + TABLE_NAME + " WHERE user_id = ?";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setInt(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        int id = resultSet.getInt("id");
        resultSet.close();
        preparedStatement.close();
        return id;
      } else {
        throw new Exception("No membership found with user id: " + userId);
      }

    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on getting membership: " + e.getMessage());
    }
  }

}
