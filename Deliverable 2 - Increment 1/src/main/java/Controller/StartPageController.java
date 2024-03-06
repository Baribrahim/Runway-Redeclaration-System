package Controller;

import Model.DatabaseModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class StartPageController {
    public DatabaseModel databaseModel = new DatabaseModel();
    @FXML
    private TextField userIDField;
    @FXML
    private PasswordField passwordField;



    public void Login(javafx.event.ActionEvent actionEvent) {
        try {
            if (databaseModel.CorrectInfo(userIDField.getText(), passwordField.getText())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Main");
                stage.show();
            } else if (userIDField.getText() == "" || passwordField.getText() == ""  ) {
                    // Handle empty input error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empty Input");
                    alert.setContentText("Please enter a UserId and Password.");
                    alert.showAndWait();
                } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect Input");
                alert.setContentText("Incorrect UserID or Password.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
