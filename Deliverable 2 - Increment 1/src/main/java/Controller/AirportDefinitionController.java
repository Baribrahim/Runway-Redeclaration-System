package Controller;

import Model.DatabaseModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AirportDefinitionController implements Initializable {

  @FXML
  private Button newAirportSubmitButton;

  @FXML
  private TextField newAirportName;

  @FXML
  private Button goBackButton;

  private DatabaseModel database;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }

  @FXML
  private void handleSubmitButtonClick() throws SQLException {
    String airportName = newAirportName.getText().trim();
    if (!airportName.isEmpty()) {
      database.insertAirport(airportName);
      ((Stage) newAirportSubmitButton.getScene().getWindow()).close();
    } else {
      // Handle empty input error
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Empty Input");
      alert.setContentText("Please enter a name for the airport.");
      alert.showAndWait();
    }
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) goBackButton.getScene().getWindow();
    stage.close();
  }
}
