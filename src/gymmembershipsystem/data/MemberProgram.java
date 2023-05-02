package gymmembershipsystem.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import gymmembershipsystem.core.SQLConnection;
import gymmembershipsystem.table.StaffTableProgram;
import gymmembershipsystem.table.TableProgram;
import gymmembershipsystem.types.PaymentStatus;
import gymmembershipsystem.types.PaymentType;

public class MemberProgram extends SQLConnection {

  private Connection sqlConnection;
  private final String TABLE_NAME = "MemberProgram";

  public MemberProgram() {
    try {
      this.sqlConnection = connect();
    } catch (Exception e) {
      throw new RuntimeException("Connection to database failed");
    }
  }

  public int createMemberProgram(int membershipId, int programId, LocalDate startDate, LocalDate endDate) {

    String query = "INSERT INTO " + TABLE_NAME
        + " (member_id, program_id, start_date, end_date, payment_status, payment_type) VALUES (?, ?, ?, ?, ?, ?)";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, membershipId);
      preparedStatement.setInt(2, programId);
      preparedStatement.setDate(3, java.sql.Date.valueOf(startDate));
      preparedStatement.setDate(4, java.sql.Date.valueOf(endDate));
      preparedStatement.setString(5, PaymentStatus.PENDING.name());
      preparedStatement.setString(6, PaymentType.CASH.name());
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

  public ArrayList<TableProgram> getAllUserProgramByMemberId(int memberId) {
    // SELECT * FROM memberprogram INNER JOIN Program on program_id = Program.id
    // WHERE member_id = 2 ;
    String query = "SELECT * FROM " + TABLE_NAME
        + " INNER JOIN Program ON MemberProgram.program_id = Program.id WHERE member_id = ? ";
    ArrayList<TableProgram> programs = new ArrayList<TableProgram>();

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setInt(1, memberId);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        TableProgram program = new TableProgram(
            resultSet.getInt("id"),
            resultSet.getInt("member_id"),
            resultSet.getInt("program_id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getDate("start_date").toString(),
            resultSet.getDate("end_date").toString(),
            resultSet.getString("amount"),
            resultSet.getString("payment_status"),
            resultSet.getString("payment_type"));
        programs.add(program);
      }
      resultSet.close();
      preparedStatement.close();
      System.out.println("count: " + programs.size());
      return programs;
    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on getting program: " + e.getMessage());
    }
  }

  public ArrayList<String> getAllProgramExceptEnrolled(int memberId) {

    String query = "SELECT * FROM Program WHERE id NOT IN (SELECT program_id FROM MemberProgram WHERE id != ?)";
    ArrayList<String> programs = new ArrayList<String>();

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setInt(1, memberId);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String program = resultSet.getString("name");
        programs.add(program);
      }
      resultSet.close();
      preparedStatement.close();
      return programs;
    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on getting program: " + e.getMessage());
    }
  }

  public void enrollProgram(int memberId, String selectedProgram, LocalDate startDate, LocalDate endDate) {

    try {
      createMemberProgram(memberId, getProgramIdByName(selectedProgram), startDate, endDate);
    } catch (Exception e) {
      throw new RuntimeException("There was an error on getting program: " + e.getMessage());
    }

  }

  public void cancelProgram(int memberId, int programId) throws Exception {
    String query = "DELETE FROM " + TABLE_NAME + " WHERE member_id = ? AND program_id = ?";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, memberId);
      preparedStatement.setInt(2, programId);

      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Removing program failed, no rows affected.");
      }

    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on removing program: " + e.getMessage());
    }
  }

  public float getProgramAmountByName(String name) {

    String query = "SELECT amount FROM Program WHERE name = ?";
    float amount = 0;

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setString(1, name);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        amount = resultSet.getFloat("amount");
      }
      resultSet.close();
      preparedStatement.close();
      return amount;
    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on getting program: " + e.getMessage());
    }

  }

  public int getProgramIdByName(String name) {

    String query = "SELECT id FROM Program WHERE name = ?";
    int id = 0;

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      preparedStatement.setString(1, name);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        id = resultSet.getInt("id");
      }
      resultSet.close();
      preparedStatement.close();
      return id;
    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on getting program: " + e.getMessage());
    }

  }

  public ArrayList<StaffTableProgram> getAllMemberWithProgram() throws Exception {
    String query = "SELECT MemberProgram.id as member_program_id, User.first_name, User.middle_name, User.last_name, Program.name as program_name, start_date, end_date, payment_status, payment_type FROM MemberProgram INNER JOIN Member ON MemberProgram.member_id = Member.id INNER JOIN User ON Member.user_id = User.id INNER JOIN Program ON Program.id = program_id";
    ArrayList<StaffTableProgram> programs = new ArrayList<StaffTableProgram>();

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        StaffTableProgram program = new StaffTableProgram(
            resultSet.getInt("member_program_id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("middle_name"),
            resultSet.getString("program_name"),
            resultSet.getDate("start_date").toString(),
            resultSet.getDate("end_date").toString(),
            resultSet.getString("payment_status"),
            resultSet.getString("payment_type"));
        programs.add(program);
      }
      resultSet.close();
      preparedStatement.close();
      return programs;
    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on getting program: " + e.getMessage());
    }

  }

  public void updatePaymentStatus(int memberProgramId, String paymentStatus) throws Exception {
    String query = "UPDATE MemberProgram SET payment_status = ? WHERE id = ?";

    try {
      PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, paymentStatus);
      preparedStatement.setInt(2, memberProgramId);

      int affectedRows = preparedStatement.executeUpdate();
      // get the created id from the database

      if (affectedRows == 0) {
        throw new Exception("Updating payment status failed, no rows affected.");
      }

    } catch (SQLException e) {
      throw new RuntimeException("There was an error on communicating with the database: " + e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("There was an error on updating payment status: " + e.getMessage());
    }
  }

}
