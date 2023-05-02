/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package gymmembershipsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author John Albert Flores
 */
public class GymMembershipSystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Login Page
        FXMLLoader loginPage = new FXMLLoader(getClass().getResource("./screens/LoginScreen.fxml"));
        loginPage.setRoot(new BorderPane());
        Parent loginRoot = loginPage.load();
        Scene loginScene = new Scene(loginRoot);

        stage.setTitle("Login - Todo Application");
        stage.setScene(loginScene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
