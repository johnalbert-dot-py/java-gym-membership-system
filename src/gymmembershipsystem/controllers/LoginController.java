package gymmembershipsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import gymmembershipsystem.singleton.UserSession;
import gymmembershipsystem.types.UserType;
import gymmembershipsystem.utilities.AppAlert;
import gymmembershipsystem.data.User;

// import todoapplication.controllers.SignUpController;

public class LoginController implements Initializable {

  private Stage stage;

  @FXML
  private VBox container;

  @FXML
  private Button loginBtn;

  @FXML
  private Button signUpButton;

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label loginError;

  @FXML
  void login(ActionEvent event) {

    String username = usernameField.getText();
    String password = passwordField.getText();

    User user = new User();
    try {
      UserType userValidate = user.validateUserCredentials(username, password);
      // show id
      UserSession.setUserId(userValidate.id);
      UserSession.setRole(userValidate.role);
      if (userValidate.role != 1) {
        this.changeScreenToStaff(event);
      } else {
        this.changeScreenToHome(event);
      }
    } catch (SQLException e) {
      AppAlert.showAlert(AlertType.ERROR, "MySQL Error", "Error occured", e.getMessage());
    } catch (Exception e) {

      loginError.setVisible(true);
      loginError.setText(e.getMessage());
      try {
        container.getChildren().add(1, loginError);
      } catch (Exception x) {
        // System.out.println("Error: " + e.getMessage());
      }

      AppAlert.showAlert(AlertType.ERROR, "MySQL Error", "Error occured", e.getMessage());
    }

    // try to convert to int

  }

  @FXML
  private void changeScreenToSignUp(ActionEvent event) throws IOException {
    FXMLLoader signUpPage = new FXMLLoader(getClass().getResource("../screens/SignUpScreen.fxml"));
    signUpPage.setRoot(new BorderPane());
    Parent root = signUpPage.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void changeScreenToHome(ActionEvent event) throws IOException {
    FXMLLoader homeScreen = new FXMLLoader(getClass().getResource("../screens/HomeScreen.fxml"));
    homeScreen.setRoot(new BorderPane());
    Parent root = homeScreen.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void changeScreenToStaff(ActionEvent event) throws IOException {
    FXMLLoader staffScreen = new FXMLLoader(getClass().getResource("../screens/StaffScreen.fxml"));
    staffScreen.setRoot(new BorderPane());
    Parent root = staffScreen.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    // remove the label
    container.getChildren().remove(1);
    // loginError.setVisible(false);
  }
}
