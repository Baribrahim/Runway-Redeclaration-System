package Controller;

import Model.Obstacle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Model.DatabaseModel;
import java.sql.SQLException;

public class ObstacleDefinitionController {

private static ObstacleDefinitionController SINGLE_INSTANCE = null;

    // Injected Parameters for Obstacle Definition Window
    @FXML
    private TextField obstacleName;
    @FXML
    private TextField obstacleHeight;
    @FXML
    private Button obstacleDoneButton;

    private ObservableList<Obstacle> obstacles;


    private ObstacleDefinitionController()
    {
        obstacles = FXCollections.observableArrayList();
        populateObstacleList();
    }

public static ObstacleDefinitionController getInstance()
{
    if (SINGLE_INSTANCE == null) {
        synchronized (ObstacleDefinitionController.class)
        {
            SINGLE_INSTANCE = new ObstacleDefinitionController();
        }
    }

    return SINGLE_INSTANCE;
}

    /**
     * Fills in the obstacle list with a few predefined obstacles and their heights
     */
    private void populateObstacleList()
    {
        obstacles.add(new Obstacle("Cargo Containers",2,1));
        obstacles.add(new Obstacle("Fallen Trees",3,1));
        obstacles.add(new Obstacle("Runway Light Fixtures",1,1));
        obstacles.add(new Obstacle("Ground Service Equipment ",4,1));
    }

    /**
     * Reads the Textfields in ObstacleDefinitionView.fxml,
     * creates a new Obstacle and adds it to a list
     * of Obstacles stored in this Controller
     */
    private DatabaseModel databaseModel = new DatabaseModel();

    @FXML
    private void defineObstacle() {
        try {
            String newObstacleName = obstacleName.getText().trim();
            String obstacleHeightText = obstacleHeight.getText().trim();

            // Check if the name and height fields are empty
            if (newObstacleName.isEmpty() || obstacleHeightText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input field empty");
                alert.setContentText("Please fill in all input fields");
                alert.showAndWait();
                return; // Exit early, do not continue executing
            }

            int newObstacleHeight = Integer.parseInt(obstacleHeightText);

            // Validate the height value
            if (newObstacleHeight < 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please put a number greater than zero for Height");
                alert.showAndWait();
                return; // Exit early
            } else if (newObstacleHeight > 100) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please put a number less than one hundred for Height");
                alert.showAndWait();
                return; // Exit early
            }

            // Create and add a new obstacle
            Obstacle newObstacle = new Obstacle(newObstacleName, newObstacleHeight, 1); // Assume width is 1
            obstacles.add(newObstacle);

            // Check for duplicate obstacles
            for (Obstacle obs : obstacles) {
                if (obs.getName().equalsIgnoreCase(newObstacleName) && obs.getHeight() == newObstacleHeight) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Duplicate alert: Obstacle has not been added");
                    alert.showAndWait();
                    obstacles.remove(newObstacle); // Remove the just added duplicate obstacle
                    return; // Exit early
                }
            }

            // Insert the new obstacle into the database
            databaseModel.insertObstacle(newObstacleName, newObstacleHeight, 1); // Assume width is 1

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please input a valid number for height");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Database error: Unable to insert the obstacle");
            alert.showAndWait();
        }
    }


    public ObservableList<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Used for TestFX testing
     */
    public void cleanUp(){obstacles.clear(); populateObstacleList();}
}
