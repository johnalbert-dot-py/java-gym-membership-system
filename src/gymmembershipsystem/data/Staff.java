package gymmembershipsystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gymmembershipsystem.core.SQLConnection;

public class Staff extends SQLConnection {

  private Connection sqlConnection;
  private final String TABLE_NAME = "Staff";

  public Staff() {
    try {
      this.sqlConnection = connect();
    } catch (Exception e) {
      throw new RuntimeException("Connection to database failed");
    }
  }

  public int createStaff(int userId) {
    String query = "INSERT INTO " + TABLE_NAME + " (user_id) VALUES (?)";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, userId);
      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Creating staff failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          generatedKeys.close();
          preparedStatement.close();
          return id;
        } else {
          throw new Exception("Creating staff failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on creating staff: " + e.getMessage());
    }
  }

}
