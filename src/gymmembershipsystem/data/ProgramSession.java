package gymmembershipsystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gymmembershipsystem.core.SQLConnection;
import gymmembershipsystem.table.TableSessionNames;

public class ProgramSession extends SQLConnection {

  private Connection sqlConnection;
  private final String TABLE_NAME = "ProgramSession";

  public ProgramSession() {
    try {
      this.sqlConnection = connect();
    } catch (Exception e) {
      throw new RuntimeException("Connection to database failed");
    }
  }

  public int createProgramSession(String name, int programId) throws Exception {
    String query = "INSERT INTO " + TABLE_NAME + " (name, program_id) VALUES (?, ?)";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query,
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, programId);
      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Creating program session failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          generatedKeys.close();
          preparedStatement.close();
          return id;
        } else {
          throw new Exception("Creating program session failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }

  }

  public ArrayList<TableSessionNames> getSessionNamesOnProgramId(int id) throws Exception {

    String query = "SELECT * FROM " + TABLE_NAME + " WHERE program_id = ? ";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<TableSessionNames> sessions = new ArrayList<TableSessionNames>();

      while (resultSet.next()) {
        TableSessionNames tableSession = new TableSessionNames(resultSet.getString("name"));
        sessions.add(tableSession);
      }

      resultSet.close();
      preparedStatement.close();

      return sessions;

    } catch (SQLException e) {
      throw new Exception("There was an error on communicating with the database: " + e.getMessage());
    }
  }

}
