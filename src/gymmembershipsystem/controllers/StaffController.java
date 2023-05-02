/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package gymmembershipsystem.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gymmembershipsystem.data.MemberProgram;
import gymmembershipsystem.data.Membership;
import gymmembershipsystem.data.ProgramSession;
import gymmembershipsystem.data.User;
import gymmembershipsystem.singleton.UserSession;
import gymmembershipsystem.table.StaffTableProgram;
import gymmembershipsystem.table.TableProgram;
import gymmembershipsystem.table.TableSessionNames;
import gymmembershipsystem.table.TableUser;
import gymmembershipsystem.types.PaymentStatus;
import gymmembershipsystem.types.UserType;
import gymmembershipsystem.utilities.AppAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 *
 * @author John Albert Flores
 * 
 *         this is a member home screen page
 */
public class StaffController implements Initializable {

  private int userIdToUpdate;
  private int memberIdToUpdate;

  @FXML
  private TextField firstNameField;
  @FXML
  private TextField lastNameField;
  @FXML
  private TextField middleNameField;
  @FXML
  private TextField weightField;
  @FXML
  private TextField heightField;
  @FXML
  private DatePicker birthdateField;
  @FXML
  private TextField genderField;

  @FXML
  private TextField firstNameField1;
  @FXML
  private TextField lastNameField1;
  @FXML
  private TextField middleNameField1;
  @FXML
  private ChoiceBox programChoice;

  @FXML
  private Button userUpdateBtn;
  @FXML
  private Button userDeleteBtn;

  @FXML
  private Button memberUpdateBtn;
  @FXML
  private Button memberDeleteBtn;

  @FXML
  private TableView<TableUser> usersTable;
  @FXML
  private TableColumn<TableUser, String> uFirstName;
  @FXML
  private TableColumn<TableUser, String> uMiddleName;
  @FXML
  private TableColumn<TableUser, String> uLastName;
  @FXML
  private TableColumn<TableUser, Double> uWeight;
  @FXML
  private TableColumn<TableUser, Double> uHeight;
  @FXML
  private TableColumn<TableUser, LocalDate> uBirthDate;
  @FXML
  private TableColumn<TableUser, String> uGender;

  @FXML
  private TableView<StaffTableProgram> userProgramsTable;
  @FXML
  private TableColumn<StaffTableProgram, String> pFullName;
  @FXML
  private TableColumn<StaffTableProgram, String> pProgramName;
  @FXML
  private TableColumn<StaffTableProgram, LocalDate> pStartDate;
  @FXML
  private TableColumn<StaffTableProgram, LocalDate> pEndDate;
  @FXML
  private TableColumn<StaffTableProgram, String> pPaymentStatus;
  @FXML
  private TableColumn<StaffTableProgram, String> pPaymentType;

  public void initializedUsersTable() {
    User user = new User();
    ArrayList<TableUser> userLists = null;

    try {
      userLists = user.getAllUsers();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ObservableList<TableUser> users = FXCollections.observableArrayList(userLists);
    uFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    uMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
    uLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    uWeight.setCellValueFactory(new PropertyValueFactory<>("weightInKg"));
    uHeight.setCellValueFactory(new PropertyValueFactory<>("heightInFt"));
    uBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    uGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    usersTable.getItems().clear();
    usersTable.setItems(users);

    firstNameField1.setDisable(true);
    lastNameField1.setDisable(true);
    middleNameField1.setDisable(true);

    userUpdateBtn.setOnAction(event -> {
      System.out.println("Clicked");
      User userToUpdate = new User();
      try {
        userToUpdate.updateUser(firstNameField.getText(), lastNameField.getText(), middleNameField.getText(),
            Float.parseFloat(weightField.getText()), Float.parseFloat(heightField.getText()), birthdateField.getValue(),
            genderField.getText(),
            userIdToUpdate);
        AppAlert.showAlert(AlertType.INFORMATION, "Success", "Success", "User updated successfully");
        initializedUsersTable();
      } catch (NumberFormatException e) {
        // alert
        AppAlert.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Please check your input: " + e.getMessage());
        e.printStackTrace();
      } catch (Exception e) {
        AppAlert.showAlert(null, "Error", "Error", "Something went wrong: " + e.getMessage());
        e.printStackTrace();
      }

    });

    userDeleteBtn.setOnAction(event -> {

      // show alert warning

      AppAlert.showAlert(AlertType.CONFIRMATION, "Confirmation", "Confirmation",
          "Are you sure you want to delete this user?");

      User userToDelete = new User();
      try {
        userToDelete.deleteUser(userIdToUpdate);
        AppAlert.showAlert(AlertType.INFORMATION, "Success", "Success", "User deleted successfully");
        initializedUsersTable();
      } catch (Exception e) {
        AppAlert.showAlert(null, "Error", "Error", "Something went wrong: " + e.getMessage());
        e.printStackTrace();
      }
    });

  }

  public void initializedUserProgramsTable() {

    ArrayList<StaffTableProgram> userProgramLists = null;
    MemberProgram userProgram = new MemberProgram();

    try {
      userProgramLists = userProgram.getAllMemberWithProgram();
    } catch (Exception e) {
      e.printStackTrace();
    }
    ObservableList<StaffTableProgram> userPrograms = FXCollections.observableArrayList(userProgramLists);
    userProgramsTable.editableProperty().set(true);
    pFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    pProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
    pStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    pEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    pPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
    pPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

    userProgramsTable.getItems().clear();
    userProgramsTable.setItems(userPrograms);

    memberUpdateBtn.setOnAction(event -> {
      MemberProgram memberProgramToUpdate = new MemberProgram();
      try {
        memberProgramToUpdate.updatePaymentStatus(memberIdToUpdate, programChoice.getValue().toString());
        AppAlert.showAlert(AlertType.INFORMATION, "Success", "Success", "Member Program updated successfully");
        initializedUserProgramsTable();
      } catch (NumberFormatException e) {
        // alert
        AppAlert.showAlert(AlertType.ERROR, "Error", "Invalid Input", "Please check your input: " + e.getMessage());
        e.printStackTrace();
      } catch (Exception e) {
        AppAlert.showAlert(null, "Error", "Error", "Something went wrong: " + e.getMessage());
        e.printStackTrace();
      }
    });

  }

  public void initialize(URL url, ResourceBundle rb) {

    this.initializedUsersTable();
    this.initializedUserProgramsTable();
    programChoice.getItems().addAll("Select Payment Status", PaymentStatus.PAID, PaymentStatus.UNPAID,
        PaymentStatus.PENDING);
    programChoice.setValue("Select Payment Status");

    usersTable.setOnMouseClicked(event -> {
      if (event.getClickCount() == 1) { // check if single click
        TableUser data = usersTable.getSelectionModel().getSelectedItem();
        String userId = data.getId();
        userIdToUpdate = Integer.parseInt(userId);
        System.out.println("userId: " + userId);
        firstNameField.setText(data.getFirstName());
        middleNameField.setText(data.getMiddleName());
        lastNameField.setText(data.getLastName());
        weightField.setText(String.valueOf(data.getWeightInKg()));
        heightField.setText(String.valueOf(data.getHeightInFt()));
        birthdateField.setValue(LocalDate.parse(data.getBirthdate()));
        genderField.setText(data.getGender());
      }
    });

    userProgramsTable.setOnMouseClicked(event -> {
      if (event.getClickCount() == 1) { // check if single click
        StaffTableProgram data = userProgramsTable.getSelectionModel().getSelectedItem();
        // do something with the selected data
        int memberProgramId = data.getId();
        memberIdToUpdate = memberProgramId;
        System.out.println("memberProgramId: " + memberProgramId);
        firstNameField1.setText(data.getFirstName());
        middleNameField1.setText(data.getMiddleName());
        lastNameField1.setText(data.getLastName());
        programChoice.setValue(data.getPaymentStatus());
      }
    });

  }

}
