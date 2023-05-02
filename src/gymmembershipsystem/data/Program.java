package gymmembershipsystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gymmembershipsystem.core.SQLConnection;

public class Program extends SQLConnection {

  private Connection sqlConnection;
  private final String TABLE_NAME = "Program";

  public Program() {
    try {
      this.sqlConnection = connect();
    } catch (Exception e) {
      throw new RuntimeException("Connection to database failed");
    }
  }

  public int createProgram(String programName, String description) throws Exception {
    String query = "INSERT INTO " + TABLE_NAME + " (name, description) VALUES (?, ?)";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query,
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, programName);
      preparedStatement.setString(2, description);
      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Creating program failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          generatedKeys.close();
          preparedStatement.close();
          return id;
        } else {
          throw new Exception("Creating program failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }
  }

  public ArrayList<String> getPrograms() throws Exception {
    String query = "SELECT * FROM " + TABLE_NAME;

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<String> programs = new ArrayList<String>();

      while (resultSet.next()) {
        programs.add(resultSet.getString("name"));
      }

      resultSet.close();
      preparedStatement.close();

      return programs;

    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }
  }

}
