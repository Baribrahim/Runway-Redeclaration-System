
package Controller;

import Model.DatabaseModel;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import Controller.Helper.Notification;

public class ObstacleDefinitionController implements Initializable {

    @FXML
    private TextField obstacleNameInput;

    @FXML
    private TextField obstacleHeightInput;

    @FXML
    private TextField obstacleWidthInput;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    private DatabaseModel database;

    private static ObstacleDefinitionController instance;

  public static ObstacleDefinitionController getInstance() {
    return instance;
  }

  public ObstacleDefinitionController() {
    instance = this;
  }

  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyNumericInputFilter(obstacleHeightInput);
        applyNumericInputFilter(obstacleWidthInput);
    }

    public void setDatabaseModel(DatabaseModel databaseModel) {
        this.database = databaseModel;
    }

    @FXML
    private void handleSubmitButtonClick() throws SQLException {
        String obstacleName = obstacleNameInput.getText().trim();
        String obstacleHeight = obstacleHeightInput.getText().trim();
        String obstacleWidth = obstacleWidthInput.getText().trim();

        // Check if any of the input fields are empty
        if (obstacleName.isEmpty() || obstacleHeight.isEmpty() || obstacleWidth.isEmpty()) {
            new Notification(AlertType.ERROR, "Error", "Please do not leave any empty inputs!");
        } else {
            // Convert height and width to float
            Float height = Float.valueOf(obstacleHeight);
            Float width = Float.valueOf(obstacleWidth);

            // Insert data into the database
            database.insertObstacle(obstacleName, height, width);

            // Close the window
            ((Stage) submitButton.getScene().getWindow()).close();
        }
      new Notification(AlertType.CONFIRMATION, "Success", "Obstacle added successfully!");
    }

    @FXML
    private void handleBackButtonClick() {
        // Close the current stage (ObstacleDefinitionController)
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private void applyNumericInputFilter(TextField textField) { // Allows only numerical values to be inputted
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*") || text.matches("[.]") && (text.length() < 6 || change.isDeleted())) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }
}
