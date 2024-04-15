package Controller;

import Model.DatabaseModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserController implements Initializable {

  private DatabaseModel database;

  @FXML
  private TextField userid;
  @FXML
  private TextField userPass;
  @FXML
  private TextField userRole;

  @FXML
  private Button goBackButton;

  @FXML
  private Button submitButton;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) goBackButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void handleSubmitButton() {
    String userID = userid.getText();
    String password = userPass.getText();
    String permission = userRole.getText();

    try {
      database.addUser(userID, password, permission);
      showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully");
      ((Stage) submitButton.getScene().getWindow()).close();
    } catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user: " + e.getMessage());
    }
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
