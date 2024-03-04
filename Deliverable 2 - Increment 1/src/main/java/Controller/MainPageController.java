package Controller;

import Model.DatabaseModel;
import Model.PhysicalRunway;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class MainPageController implements Initializable {

  //fxml elements
  @FXML
  private Button importDataButton;

  @FXML
  private Button exportDataButton;

  @FXML
  private ComboBox<String> airportMenu;
  @FXML
  public ComboBox<String> runwayMenu;
  @FXML
  private ComboBox<String> obstacleMenu;
  @FXML
  private Button addNewAirportButton;
  @FXML
  private Button addNewRunwayButton;
  @FXML
  private Button addNewObstacleButton;
  @FXML
  public Tab topViewTab;
  @FXML
  private Tab sideViewTab;
  @FXML
  private Tab simultaneousViewTab;
  @FXML
  private TableView<Parameter> leftTableView;
  @FXML
  private TableView<Parameter> rightTableView;
  @FXML
  private TableView<Parameter> notificationsTable;

  @FXML
  private Button calculationBreakdown;

  @FXML
  private TextField distanceFromThresholdInput;
  @FXML
  private TextField distanceFromCentreLineInput;

  @FXML
  private RadioButton leftSide;
  @FXML
  private RadioButton rightSide;
  @FXML
  private Button calculateButton;

  private TopDownViewController topDownViewController;
  private SideOnViewController sideOnViewController;
  private SimultaneousViewController simultaneousViewController;
  private DatabaseModel database = new DatabaseModel();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/TopDownView.fxml"));
    Parent root1 = null;
    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/SideView.fxml"));
    Parent root2 = null;
    FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/SimultaneousView.fxml"));
    Parent root3 = null;
    try {
      root1 = loader1.load();
      root2 = loader2.load();
      root3 = loader3.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    topDownViewController = loader1.getController();
    sideOnViewController = loader2.getController();
    simultaneousViewController = loader3.getController();
    topViewTab.setContent(root1);
    sideViewTab.setContent(root2);
    simultaneousViewTab.setContent(root3);

    airportMenu.setVisible(true);
    airportMenu.setDisable(false);

    runwayMenu.setDisable(true);
    obstacleMenu.setDisable(true);


    try {
      obstacleMenu.getItems().addAll(database.getObstacles());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      airportMenu.getItems().addAll(database.getAirports());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    applyNumericInputFilter(distanceFromThresholdInput);
    applyNumericInputFilter(distanceFromCentreLineInput);

    distanceFromThresholdInput.textProperty().addListener((observable,oldValue,newVlaue) -> {
      int distance = Integer.parseInt(distanceFromThresholdInput.getText());
      if (distance < 1) {
        //error handling
        System.out.println("Error");
      }
    });
  }

  @FXML
  public void showPhysicalRunways() throws SQLException {
    runwayMenu.getItems().clear();
    runwayMenu.setVisible(true);
    runwayMenu.setDisable(false);
    if (!runwayMenu.getItems().containsAll(database.getPhysicalRunways(airportMenu.getValue()))) {
      runwayMenu.getItems().addAll(database.getPhysicalRunways(airportMenu.getValue()));
    }
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

  @FXML
  public void runwayMenuChanged(ActionEvent event) throws SQLException {
    obstacleMenu.setDisable(false);
    String runway = runwayMenu.getValue();
    ArrayList<Float> parameters = database.getLogicalRunwayParameters(runway);
    topDownViewController.updateView(runway, parameters);
    sideOnViewController.updateView(runway, parameters);
    simultaneousViewController.updateView(runway, parameters);
  }

  @FXML
  private void handleAddAirportButtonClick() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/airportDefinition.fxml"));
      Parent root = loader.load();

      // Get the controller for the new page
      AirportDefinitionController newAirportController = loader.getController();
      newAirportController.setDatabaseModel(database);

      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("New Airport");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleAddRunwayButtonClick() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/runwayDefinition.fxml"));
      Parent root = loader.load();

      // Get the controller for the new page
      RunwayDefinition runwayDefinition = loader.getController();
      runwayDefinition.setDatabaseModel(database);

      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("New Runway");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

