package Controller;

import Model.DatabaseModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import Controller.Helper.Notification;

public class StartPageController {
    public DatabaseModel databaseModel = new DatabaseModel();
    @FXML
    private TextField userIDField;
    @FXML
    private PasswordField passwordField;



    public void Login(javafx.event.ActionEvent actionEvent) throws SQLException {
        String userRole = databaseModel.getUserRole(userIDField.getText(), passwordField.getText());
        try {
            if (databaseModel.CorrectInfo(userIDField.getText(), passwordField.getText())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                Parent root = loader.load();
                MainPageController mainPageController = loader.getController();
                mainPageController.setUserRole(userRole);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Main");
                stage.setResizable(false);
                stage.show();

                // Close connection to database to prevent locked database
                databaseModel.getConnection().close();

                // Close the login window
                Node source = (Node) actionEvent.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.close();
            } else if (userIDField.getText() == "" || passwordField.getText() == ""  ) {
                    // Handle empty input error
                    new Notification(AlertType.ERROR, "Error", "Empty Input\nPlease enter a UserId and Password.");
                } else {
                new Notification(AlertType.ERROR, "Error", "Incorrect UserID or Password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
