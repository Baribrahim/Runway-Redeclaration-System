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
      // Optionally, you can close the stage here if needed
      ((Stage) newAirportSubmitButton.getScene().getWindow()).close();
    } else {
      // Handle empty input error
      System.out.println("Enter a name");
    }
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) goBackButton.getScene().getWindow();
    stage.close();
  }
}
