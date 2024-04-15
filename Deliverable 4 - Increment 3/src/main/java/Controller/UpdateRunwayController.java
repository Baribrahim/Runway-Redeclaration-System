package Controller;

import Model.DatabaseModel;
import Model.Runway;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateRunwayController implements Initializable {

  @FXML
  private TextField leftTODAInput;
  @FXML
  private TextField leftTORAInput;
  @FXML
  private TextField leftASDAInput;
  @FXML
  private TextField leftLDAInput;
  @FXML
  private TextField rightTODAInput;
  @FXML
  private TextField rightTORAInput;
  @FXML
  private TextField rightASDAInput;
  @FXML
  private TextField rightLDAInput;
  @FXML
  private TextField runwayNameInput;
  @FXML
  private TextField airportNameInput;
  @FXML
  private Button UpdateButton;

  @FXML
  private Button backButton;

  private Runway currentRunway;
  private DatabaseModel database;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }

  // Call this method from the main controller to pass the selected runway
  public void initRunwayData(Runway runway, DatabaseModel dbModel) {
    currentRunway = runway;
    database = dbModel;

    runwayNameInput.setText(runway.getRunwayID());
    airportNameInput.setText(runway.getAirportName());
    leftTORAInput.setText(String.valueOf(runway.getLeftTORA()));
    leftTODAInput.setText(String.valueOf(runway.getLeftTODA()));
    leftASDAInput.setText(String.valueOf(runway.getLeftASDA()));
    leftLDAInput.setText(String.valueOf(runway.getLeftLDA()));
    rightTORAInput.setText(String.valueOf(runway.getRightTORA()));
    rightTODAInput.setText(String.valueOf(runway.getRightTODA()));
    rightASDAInput.setText(String.valueOf(runway.getRightASDA()));
    rightLDAInput.setText(String.valueOf(runway.getRightLDA()));
    // Continue setting the rest of the fields
  }

  @FXML
  private void handleUpdateButton() {
    try {
      // Create an updated runway object or directly update the currentRunway object
      currentRunway.setRunwayID(runwayNameInput.getText());
      currentRunway.setAirportName(airportNameInput.getText());
      currentRunway.setLeftTORA(Double.parseDouble(leftTORAInput.getText()));
      currentRunway.setLeftTODA(Double.parseDouble(leftTODAInput.getText()));
      currentRunway.setLeftASDA(Double.parseDouble(leftASDAInput.getText()));
      currentRunway.setLeftLDA(Double.parseDouble(leftLDAInput.getText()));
      currentRunway.setRightTORA(Double.parseDouble(rightTORAInput.getText()));
      currentRunway.setRightTODA(Double.parseDouble(rightTODAInput.getText()));
      currentRunway.setRightASDA(Double.parseDouble(rightASDAInput.getText()));
      currentRunway.setRightLDA(Double.parseDouble(rightASDAInput.getText()));
      // Continue updating fields...

      database.updateRunway(currentRunway);

      // Close the update window
      Stage stage = (Stage) airportNameInput.getScene().getWindow();
      stage.close();
    } catch (Exception e) {
      showAlert("Error", "Failed to update the runway: " + e.getMessage());
      System.out.println("Failed to update runway with ID: " + currentRunway.getRunwayID());
      e.printStackTrace();
    }
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) backButton.getScene().getWindow();
    stage.close();
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
