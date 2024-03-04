package Controller;

import Model.DatabaseModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class RunwayDefinition implements Initializable {

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
  private Button submitButton;

  @FXML
  private Button backButton;

  @FXML
  private TextField runwayNameInput;

  @FXML
  private TextField airportNameInput;

  private DatabaseModel database;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    applyNumericInputFilter(leftTODAInput);
    applyNumericInputFilter(leftTORAInput);
    applyNumericInputFilter(leftASDAInput);
    applyNumericInputFilter(leftLDAInput);
    applyNumericInputFilter(rightTODAInput);
    applyNumericInputFilter(rightTORAInput);
    applyNumericInputFilter(rightASDAInput);
    applyNumericInputFilter(rightLDAInput);
  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }


  @FXML
  private void handleSubmitButtonClick() throws SQLException {
    String runwayName = runwayNameInput.getText().trim();
    String airportName = airportNameInput.getText().trim();

// Check if any of the input fields are empty
    if (runwayName.isEmpty() || airportName.isEmpty() ||
        leftTODAInput.getText().isEmpty() || leftTORAInput.getText().isEmpty() ||
        leftASDAInput.getText().isEmpty() || leftLDAInput.getText().isEmpty() ||
        rightTODAInput.getText().isEmpty() || rightTORAInput.getText().isEmpty() ||
        rightASDAInput.getText().isEmpty() || rightLDAInput.getText().isEmpty()) {

      // Display an error alert if any input field is empty
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Empty Input");
      alert.setContentText("Please do not leave any empty inputs!");
      alert.showAndWait();
    } else {
      // Convert input to appropriate data types
      Float leftTODA = Float.valueOf(leftTODAInput.getText().trim());
      Float leftTORA = Float.valueOf(leftTORAInput.getText().trim());
      Float leftASDA = Float.valueOf(leftASDAInput.getText().trim());
      Float leftLDA = Float.valueOf(leftLDAInput.getText().trim());
      Float rightTODA = Float.valueOf(rightTODAInput.getText().trim());
      Float rightTORA = Float.valueOf(rightTORAInput.getText().trim());
      Float rightASDA = Float.valueOf(rightASDAInput.getText().trim());
      Float rightLDA = Float.valueOf(rightLDAInput.getText().trim());

      // Insert data into the database
      database.insertRunway(runwayName, airportName, leftTORA, leftTODA, leftASDA, leftLDA, rightTODA, rightTORA, rightASDA, rightLDA);

      // Close the window
      ((Stage) submitButton.getScene().getWindow()).close();
    }
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) backButton.getScene().getWindow();
    stage.close();
  }

  private void applyNumericInputFilter(TextField textField) { // allows only numerical values to be inputted
    UnaryOperator<Change> filter = change -> {
      String text = change.getText();
      String fullText = change.getControlText();

      if (text.matches("[0-9]*") && (fullText.length() < 6 || change.isDeleted())) {
        return change;
      }

      return null;
    };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    textField.setTextFormatter(textFormatter);
  }
}
