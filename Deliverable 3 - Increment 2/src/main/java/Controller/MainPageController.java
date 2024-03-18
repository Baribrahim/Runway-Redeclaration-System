package Controller;

import Model.*;
import Model.DatabaseModel;
import java.io.File;
import java.lang.reflect.Parameter;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.ObservableList;
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
import javafx.application.Platform;

public class MainPageController implements Initializable {

  //fxml elements
  @FXML
  private Button importAirportButton;

  @FXML
  private Button exportAirportButton;

  @FXML
  private Button importObstacleButton;

  @FXML
  private Button exportObstacleButton;

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
  private Button updateView;
  @FXML
  public Tab topViewTab;
  @FXML
  private Tab sideViewTab;
  @FXML
  private Tab simultaneousViewTab;
  @FXML
  private TableView<Model.Parameter> leftTableView;

  @FXML
  private TableColumn<Model.Parameter, String> parColumnL;

  @FXML
  private TableColumn<Model.Parameter, Double> originalColL;

  @FXML
  private TableColumn<Parameter, Double> revisedColL;
  @FXML
  private TableView<Parameter> rightTableView;
  @FXML
  private TableView<Parameter> notificationsTable;

  @FXML
  private TextField rightOriginalTORATextField;
  @FXML
  private TextField rightOriginalTODATextField;
  @FXML
  private TextField rightOriginalASDATextField;
  @FXML
  private TextField rightOriginalLDATextField;

  @FXML
  private TextField rightRevisedTORATextField;
  @FXML
  private TextField rightRevisedTODATextField;
  @FXML
  private TextField rightRevisedASDATextField;
  @FXML
  private TextField rightRevisedLDATextField;

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

  @FXML
  private Button refreshButton;

  @FXML
  private MenuItem userGuidePage;

  private XMLController xmlController = new XMLController();

  private TopDownViewController topDownViewController;
  private SideOnViewController sideOnViewController;
  private SimultaneousViewController simultaneousViewController;
  private DatabaseModel database = new DatabaseModel();

  private Boolean isLightMode;

  private static final Logger logger = LogManager.getLogger(MainPageController.class);


  public ObservableList<Obstacle> getObstacles(){return obstacles;}
  public static ObservableList<Airport> getAirports(){return airports;}
  public static PhysicalRunway getPhysRunwaySelected() {return physRunwayItem.get();}
  //  public static boolean needRedeclare(){return needRedeclare;}
  public static Obstacle getObstacleSelected() {return obstacleProperty.get();}
  public static Airport getAirportSelected() {return airportItem.get();}

  //  public MenuButton getAirportMenu() {return this.airportMenu;}
  public TopDownViewController getTopDownViewController() { return topDownViewController;}
  public SideOnViewController getSideOnViewController() { return sideOnViewController;}
  public static boolean beforeCalculation = false;

  public static ObjectProperty<PhysicalRunway> physRunwayItem = new SimpleObjectProperty<>();
  public static ObjectProperty<Airport> airportItem = new SimpleObjectProperty();
  public static ObjectProperty<Obstacle> obstacleProperty = new SimpleObjectProperty<>();
  public static DoubleProperty disFromThreshold = new SimpleDoubleProperty();
  public static DoubleProperty disFromCentre = new SimpleDoubleProperty();
  public static StringProperty dirFromCentre = new SimpleStringProperty();
  public static DoubleProperty valueChanged = new SimpleDoubleProperty();
  public static DoubleProperty obstacleHeight = new SimpleDoubleProperty();
  public static DoubleProperty obstacleWidth = new SimpleDoubleProperty();
  public static IntegerProperty themeProperty = new SimpleIntegerProperty();

  public static HashMap<String, Airport> airportMap = new HashMap<>();
  public static ObservableList<Airport> airports = FXCollections.observableArrayList();
  //    public static MapProperty<String, Airport> airports = new SimpleMapProperty<>(FXCollections.observableMap(map));
  public static ObservableList<Obstacle> obstacles = FXCollections.observableArrayList();
  public static ObservableList<String> airportNames = FXCollections.observableArrayList();

  private static boolean needRedeclare = true;

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

    isLightMode = true;

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

  @FXML
  public void refreshAirports() throws SQLException {
    ArrayList<String> allAirports = database.getAirports();
    ObservableList<String> airportList = airportMenu.getItems();

    for (String airport : allAirports) {
      if (!airportList.contains(airport)) {
        airportList.add(airport);
      }
    }
  }
  @FXML
  private void handleAddObstacleButtonClick() {
    try {
      // Load the obstacle definition FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ObstacleDefinition.fxml"));
      Parent root = loader.load();

      // Get the controller for the new page and set the database model
      ObstacleDefinitionController obstacleDefinitionController = loader.getController();
      obstacleDefinitionController.setDatabaseModel(database);

      // Create a new stage (window) for the obstacle definition
      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("New Obstacle");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @FXML
  public void refreshObstacles() throws SQLException {
    ArrayList<String> allObstacles = database.getObstacles();
    ObservableList<String> obstacleList = obstacleMenu.getItems();

    for (String obstacle : allObstacles) {
      if (!obstacleList.contains(obstacle)) {
        obstacleList.add(obstacle);
      }
    }
  }

  public void someMethodThatGetsObstacle() {
    Platform.runLater(() -> {
      try {
        String selectedObstacleId = obstacleMenu.getValue();
        if (selectedObstacleId != null && !selectedObstacleId.isEmpty()) {
         double distanceFromThreshold = Double.parseDouble(distanceFromThresholdInput.getText());
          double distanceFromCentre = Double.parseDouble(distanceFromCentreLineInput.getText());

          double height = database.getObstacleHeight(selectedObstacleId);
          double width = database.getObstacleWidth(selectedObstacleId);

          Obstacle obstacle = new Obstacle(selectedObstacleId, height, width, distanceFromCentre, distanceFromThreshold);

          topDownViewController.relocateObstacle();
          sideOnViewController.displayObstacle(obstacle);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }


  @FXML
  private void onObstacleExportClick() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Obstacles");
    // Suggest a file name for the user
    fileChooser.setInitialFileName("obstacles_export.xml");
    File file = fileChooser.showSaveDialog(exportObstacleButton.getScene().getWindow());
    if (file != null) {
      xmlController.setDatabaseModel(this.database);
      xmlController.exportObstacles(file);
    }
  }


@FXML
private void calculateRunwayDistances() {
  try {
    String selectedObstacleId = obstacleMenu.getValue();
    float height = database.getObstacleHeight(selectedObstacleId);
    float width = database.getObstacleWidth(selectedObstacleId);

    if (height == -1 || width == -1) {
      System.out.println("Obstacle information not completed");
      return;
    }

    Obstacle obstacle = new Obstacle(selectedObstacleId, height, width);

    ArrayList<Float> runwayParameters = database.getLogicalRunwayParameters(runwayMenu.getValue());

    LogicalRunway runway = new LogicalRunway(runwayMenu.getValue(), runwayParameters.get(0), runwayParameters.get(1), runwayParameters.get(2), runwayParameters.get(3));

      double newTora = ParameterCalculator.calculateTORA(obstacle, runway);
    double newLda = ParameterCalculator.calculateLDA(obstacle, runway);
    double newAsda = ParameterCalculator.calculateASDA(obstacle, runway);
    double newToda = ParameterCalculator.calculateTODA(obstacle, runway);

    updateUI(runway.getTora(), newTora, runway.getToda(), newToda, runway.getAsda(), newAsda, runway.getLda(), newLda);

  } catch (SQLException e) {
    e.printStackTrace();
  }
}

private void updateUI(double originalTora, double revisedTora,
                      double originalToda, double revisedToda,
                      double originalAsda, double revisedAsda,
                      double originalLda, double revisedLda) {

  ObservableList<Model.Parameter> leftData = FXCollections.observableArrayList();

  parColumnL.setCellValueFactory(new PropertyValueFactory<>("name"));
  originalColL.setCellValueFactory(new PropertyValueFactory<>("originalValue"));
  revisedColL.setCellValueFactory(new PropertyValueFactory<>("newValue"));

  leftData.add(new Model.Parameter("TORA (m)", String.valueOf(originalTora), String.valueOf(revisedTora)));
  leftData.add(new Model.Parameter("TODA (m)", String.valueOf(originalToda), String.valueOf(revisedToda)));
  leftData.add(new Model.Parameter("ASDA (m)", String.valueOf(originalAsda), String.valueOf(revisedAsda)));
  leftData.add(new Model.Parameter("LDA (m)", String.valueOf(originalLda), String.valueOf(revisedLda)));
  leftTableView.setItems(leftData);
}

  @FXML
  private void onAirportExportClick() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Airports");
    // Suggest a file name for the user
    fileChooser.setInitialFileName("airports_export.xml");
    File file = fileChooser.showSaveDialog(exportAirportButton.getScene().getWindow());
    if (file != null) {
      xmlController.setDatabaseModel(this.database);
      xmlController.exportAirports(file);
    }
  }

  @FXML
  private void onObstacleImportClick() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import Obstacles");
    File file = fileChooser.showOpenDialog(importObstacleButton.getScene().getWindow());
    if (file != null) {
      xmlController.setDatabaseModel(this.database);
      xmlController.importObstacles(file);
    }
  }

  @FXML
  private void onAirportImportClick() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import Airports");
    File file = fileChooser.showOpenDialog(importAirportButton.getScene().getWindow());
    if (file != null) {
      xmlController.setDatabaseModel(this.database);
      xmlController.importAirports(file);
    }
  }

  @FXML
  private void handleUserGuidePageButton() {
    try {
      // Load the obstacle definition FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/userGuidePage.fxml"));
      Parent root = loader.load();

      // Create a new stage (window) for the obstacle definition
      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("User Guide");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleRotateButton() {
    topDownViewController.rotateRunway();
  }

}

