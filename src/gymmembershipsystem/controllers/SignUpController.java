package gymmembershipsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import gymmembershipsystem.data.User;
import gymmembershipsystem.singleton.UserSession;
import gymmembershipsystem.utilities.AppAlert;;

public class SignUpController implements Initializable {

  private Stage stage;

  @FXML
  private VBox container;

  @FXML
  private Button loginBtn;

  @FXML
  private Button signUpButton;

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
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private PasswordField confirmPasswordField;

  @FXML
  private Label loginError;

  @FXML
  void signUp(ActionEvent event) {
    System.out.println("From Log In Controller " +
        UserSession.getUserId());
    boolean loggedIn = false;
    if (!passwordField.getText().equals(confirmPasswordField.getText())) {
      loginError.setVisible(true);
      loginError.setText("Password does not match.");
      try {
        container.getChildren().add(1, loginError);
      } catch (Exception e) {
        // System.out.println("Error: " + e.getMessage());
      }
      return;
    }

    loginError.setVisible(true);

    try {
      container.getChildren().add(1, loginError);
    } catch (Exception e) {
      // System.out.println("Error: " + e.getMessage());
    }

    String username = usernameField.getText();
    String password = passwordField.getText();
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String middleName = middleNameField.getText().isEmpty() ? "" : middleNameField.getText();
    float weight = weightField.getText().isEmpty() ? 0.0f : Float.parseFloat(weightField.getText());
    float height = heightField.getText().isEmpty() ? 0.0f : Float.parseFloat(heightField.getText());
    LocalDate birthdate = null;

    birthdate = birthdateField.getValue();
    String gender = genderField.getText();

    // TODO: THIS ONE IS FOR REGISTRATION AS STAFF OR AS A MEMBER ONLY
    int role = 1; // 0 FOR STAFF, 1 FOR MEMBER

    User user = new User();

    if (username != "" && password != "" && firstName != "" && lastName != "" && birthdate != null) {

      try {
        int created = user.createUser(username, password, firstName, lastName, middleName, weight, height, birthdate,
            gender, role);
        if (created > 0) {
          loginError.getStyleClass().add("success");
          loginError.setText("Sign Up Success.");
          loggedIn = true;
        } else {
          String message = "Something went wrong.";
          loginError.setText(message);
        }
      } catch (Exception e) {
        String message = e.getMessage();
        loginError.setText("Error Occured on database");
        AppAlert.showAlert(AlertType.ERROR, "MySQL Error", "Error occured", message);
        throw new RuntimeException(e);
      }

    } else {
      // get fields that is empty

      ArrayList<String> emptyFields = new ArrayList<String>();
      if (username.isEmpty()) {
        emptyFields.add("Username");
      }
      if (password.isEmpty()) {
        emptyFields.add("Password");
      }
      if (firstName.isEmpty()) {
        emptyFields.add("First Name");
      }
      if (lastName.isEmpty()) {
        emptyFields.add("Last Name");
      }

      if (birthdate == null) {
        emptyFields.add("Birthdate");
        return;
      }

      String message = String.join(", ", emptyFields);
      loginError.setText(message);
      System.out.println("ERRRROR");
    }

    if (loggedIn) {
      try {
      } catch (Exception e) {
      }
    }

  }

  @FXML
  void changeScreenToLogin(ActionEvent event) throws IOException {
    FXMLLoader loginPage = new FXMLLoader(getClass().getResource("../screens/LoginScreen.fxml"));
    loginPage.setRoot(new BorderPane());
    Parent root = loginPage.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    container.getChildren().remove(1);

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

  }

  public void initData() {

  }

}
