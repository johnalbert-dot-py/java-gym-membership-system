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
import gymmembershipsystem.table.TableProgram;
import gymmembershipsystem.table.TableSessionNames;
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
public class HomeScreenController implements Initializable {

    private int userId;
    private int selectedProgramId;
    private int currentMemberId;

    @FXML
    private Label label;

    @FXML
    private TableView<TableProgram> userProgramsTable;

    @FXML
    private TableView<TableSessionNames> sessionTable;

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
    private Label totalProgramLabel;

    @FXML
    private DatePicker birthdateField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField programAmountField;

    @FXML
    private ChoiceBox programChoice;

    @FXML
    private Button userUpdateBtn;

    @FXML
    private Button chooseProgramBtn;

    @FXML
    private VBox selectProgramContainer;

    @FXML
    private DatePicker programStartDateInput;

    @FXML
    private DatePicker programEndDateInput;

    @FXML
    private Button cancelTrainingBtn;

    @FXML
    private TableColumn<TableProgram, String> programNameCol;

    @FXML
    private TableColumn<TableProgram, String> programDescriptionCol;

    @FXML
    private TableColumn<TableProgram, String> programStartDate;

    @FXML
    private TableColumn<TableProgram, String> programEndDate;

    @FXML
    private TableColumn<TableProgram, String> sessionNameCol;

    @FXML
    private TableColumn<TableProgram, String> programAmount;

    @FXML
    private TableColumn<TableProgram, String> programPaymentStatus;

    @FXML
    private TableColumn<TableProgram, String> programPaymentType;

    private void initiatePrograms(int memberId) {
        MemberProgram memberProgram = new MemberProgram();
        ArrayList<String> availablePrograms = memberProgram.getAllProgramExceptEnrolled(memberId);
        programChoice.getItems().clear();
        programChoice.getItems().add("Select Program");
        programChoice.setValue("Select Program");
        programChoice.getItems().addAll(availablePrograms);
        ArrayList<TableProgram> programs = memberProgram.getAllUserProgramByMemberId(memberId);

        totalProgramLabel.setText("Total Programs Your Enrolled: " + programs.size());

        ObservableList<TableProgram> data = FXCollections.observableArrayList(programs);

        // set the value of program name and value of program description
        programNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        programDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        programStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        programEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        programPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        programAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        programPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

        userProgramsTable.setItems(data);
    }

    private void addSessionNamesOnTable(int programId) {

        ProgramSession programSession = new ProgramSession();
        ArrayList<TableSessionNames> sessions = null;
        try {
            sessions = programSession.getSessionNamesOnProgramId(programId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ObservableList<TableSessionNames> data = FXCollections.observableArrayList(sessions);

        sessionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sessionTable.setItems(data);

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        programChoice.getItems().add("Select Program");
        programChoice.setValue("Select Program");
        programAmountField.editableProperty().set(false);
        programAmountField.setText("0.00");

        weightField.setTextFormatter(new TextFormatter<>(change -> {
            try {
                String text = change.getText();
                if (text.matches("[0-9.]*")) {
                    return change;
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }));
        heightField.setTextFormatter(new TextFormatter<>(change -> {
            try {
                String text = change.getText();
                if (text.matches("[0-9.]*")) {
                    return change;
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }));
        birthdateField.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
        programStartDateInput.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });
        programEndDateInput.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });

        cancelTrainingBtn.setDisable(true);

        int memberId = 0;

        try {
            User user = new User();
            UserType userData = user.getUserById(UserSession.getUserId());

            userId = userData.id;
            firstNameField.setText(userData.firstName);
            lastNameField.setText(userData.lastName);
            middleNameField.setText(userData.middleName);
            weightField.setText(String.valueOf(userData.weightInKg));
            heightField.setText(String.valueOf(userData.heightInFt));
            birthdateField.setValue(userData.birthDate);
            genderField.setText(userData.gender);

            Membership member = new Membership();
            memberId = member.getMemberByUserId(UserSession.getUserId());

        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }

        ////////////////////////////////
        // button events and bindings //
        ////////////////////////////////

        programChoice.setOnAction(e -> {
            String choiceValue = programChoice.getValue().toString();
            if (choiceValue != "Select Program") {
                MemberProgram memberProgram = new MemberProgram();
                programAmountField.setText(String.valueOf(memberProgram.getProgramAmountByName(choiceValue)));
            }
        });

        userProgramsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // check if single click
                TableProgram data = userProgramsTable.getSelectionModel().getSelectedItem();
                // do something with the selected data
                int programId = data.getProgramId();
                addSessionNamesOnTable(programId);
                selectedProgramId = programId;
                currentMemberId = data.getMemberId();
                cancelTrainingBtn.setDisable(false);
            }
        });

        cancelTrainingBtn.setOnAction(event -> {
            AppAlert.showAlert(AlertType.CONFIRMATION, "Are you sure you want to continue?",
                    "You're about to cancel this program",
                    "You will be remove from the lists of enrolled in this Gym Program.");
            MemberProgram user = new MemberProgram();
            try {
                user.cancelProgram(currentMemberId, selectedProgramId);
                AppAlert.showAlert(AlertType.INFORMATION, "This program is now removed from your account.",
                        "Cancellation Program Success!", "You are now currently not enrolled to this Gym Program.");
                initiatePrograms(currentMemberId);
                sessionTable.getItems().clear();
            } catch (Exception error) {
                AppAlert.showAlert(AlertType.ERROR, "Error on Update", "Database Error", error.getMessage());
            }
        });

        final int m = memberId;
        chooseProgramBtn.setOnAction(e -> {
            String choiceValue = programChoice.getValue().toString();
            if (choiceValue != "Select Program" && programStartDateInput != null & programEndDateInput != null) {
                MemberProgram memberProgram = new MemberProgram();
                memberProgram.enrollProgram(m, choiceValue, programStartDateInput.getValue(),
                        programEndDateInput.getValue());
                // alert
                AppAlert.showAlert(AlertType.INFORMATION, "You are now succesfully Enrolled to this Program.",
                        "Success!", "You are now currently enrolled to this Gym Program");
                initiatePrograms(m);
            }
        });

        userUpdateBtn.setOnAction(e -> {
            // get the values
            try {

                User user = new User();

                if (birthdateField.getValue() != null && !firstNameField.getText().isBlank()
                        && !lastNameField.getText().isBlank() && !weightField.getText().isBlank()
                        && !heightField.getText().isBlank()) {
                    user.updateUser(
                            firstNameField.getText(),
                            lastNameField.getText(),
                            middleNameField.getText(),
                            Float.parseFloat(weightField.getText()),
                            Float.parseFloat(heightField.getText()),
                            birthdateField.getValue(),
                            genderField.getText(),
                            UserSession.getUserId());
                    AppAlert.showAlert(AlertType.INFORMATION, "Updated Successfully.",
                            "Success!", "Your profile was updated");
                }

            } catch (Exception error) {
                // error alert
                AppAlert.showAlert(AlertType.ERROR, "Error on Update", "Database Error", error.getMessage());
            }
        });

        initiatePrograms(memberId);
    }

}
