package Controller;

import Model.*;
import Model.DatabaseModel;
import java.io.File;
import java.lang.reflect.Parameter;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
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
  private TableView<Model.Parameter> rightTableView;
  @FXML
  private TableColumn<Model.Parameter, String> parColumnL;
  @FXML
  private TableColumn<Model.Parameter, String> parColumnR;
  @FXML
  private TableColumn<Model.Parameter, Double> originalColL;
  @FXML
  private TableColumn<Model.Parameter, Double> originalColR;
  @FXML
  private TableColumn<Parameter, Double> revisedColL;
  @FXML
  private TableColumn<Parameter, Double> revisedColR;
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

  @FXML
  private MenuItem darkModeButton;

  private ToggleGroup toggleGroup = new ToggleGroup();

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
  public static String getdirFromCentre() {return dirFromCentre.get();}
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
    leftSide.setToggleGroup(toggleGroup);
    rightSide.setToggleGroup(toggleGroup);

    darkModeButton.setOnAction(e -> toggleDarkMode());

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

  @FXML
  public void setLeftRightDirection(ActionEvent event) throws SQLException {
    String selectedObstacleId = obstacleMenu.getValue();
    double distanceFromThreshold = Double.parseDouble(distanceFromThresholdInput.getText());
    double distanceFromCentre = Double.parseDouble(distanceFromCentreLineInput.getText());

    double height = database.getObstacleHeight(selectedObstacleId);
    double width = database.getObstacleWidth(selectedObstacleId);

    Obstacle obstacle = new Obstacle(selectedObstacleId, height, width, distanceFromCentre, distanceFromThreshold);
    obstacleProperty.set(obstacle);
    if(leftSide.isSelected()){
      getObstacleSelected().setDirFromCentre("L");
      dirFromCentre.set("L");
      someMethodThatGetsObstacle();
    } else if (rightSide.isSelected()){
      getObstacleSelected().setDirFromCentre("R");
      dirFromCentre.set("R");
      someMethodThatGetsObstacle();
    }
  }

  public boolean isLeftSideSelected() {
    return leftSide.isSelected();
  }

  public boolean isRightSideSelected() {
    return rightSide.isSelected();
  }


  public void someMethodThatGetsObstacle() {
    Platform.runLater(() -> {
      try {
        String selectedObstacleId = obstacleMenu.getValue();
        System.out.println("Current selected obstacle is: " + selectedObstacleId.toString());
        if (selectedObstacleId != null && !selectedObstacleId.isEmpty()) {
         double distanceFromThreshold = Double.parseDouble(distanceFromThresholdInput.getText());
         double distanceFromCentre = Double.parseDouble(distanceFromCentreLineInput.getText());

         double height = database.getObstacleHeight(selectedObstacleId);
         double width = database.getObstacleWidth(selectedObstacleId);

         Obstacle obstacle = new Obstacle(selectedObstacleId, height, width, distanceFromCentre, distanceFromThreshold);
          obstacleProperty.set(obstacle);
         ArrayList<Float> parameters = database.getLogicalRunwayParameters(runwayMenu.getValue());
         LogicalRunway leftLogicalRunway = new LogicalRunway(runwayMenu.getValue().split("/")[0], parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
         LogicalRunway rightLogicalRunway = new LogicalRunway(runwayMenu.getValue().split("/")[1], parameters.get(5), parameters.get(4), parameters.get(6), parameters.get(7));
         ObservableList<LogicalRunway> logicalRunways = FXCollections.observableArrayList();
         logicalRunways.add(leftLogicalRunway);
         logicalRunways.add(rightLogicalRunway);
         PhysicalRunway physicalRunway = new PhysicalRunway(runwayMenu.getValue(), logicalRunways);
         physRunwayItem.set(physicalRunway);


         //sideOnViewController.displayObstacle(obstacle);
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
      double distanceFromThreshold = Double.parseDouble(distanceFromThresholdInput.getText());
      double distanceFromCentre = Double.parseDouble(distanceFromCentreLineInput.getText());
      float height = database.getObstacleHeight(selectedObstacleId);
      float width = database.getObstacleWidth(selectedObstacleId);

      if (height == -1 || width == -1) {
        System.out.println("Obstacle information not completed");
        return;
      }
      Obstacle obstacle = new Obstacle(selectedObstacleId, height, width, distanceFromCentre, distanceFromThreshold);
      obstacleProperty.set(obstacle);

      ArrayList<Float> runwayParameters = database.getLogicalRunwayParameters(runwayMenu.getValue());

      LogicalRunway runwayL = new LogicalRunway(runwayMenu.getValue().split("/")[0], runwayParameters.get(0), runwayParameters.get(1), runwayParameters.get(2), runwayParameters.get(3));

      LogicalRunway runwayR = new LogicalRunway(runwayMenu.getValue().split("/")[1], runwayParameters.get(5), runwayParameters.get(4), runwayParameters.get(6), runwayParameters.get(7));

      double newToraL = ParameterCalculator.calculateTORA(obstacle, runwayL);
      double newLdaL = ParameterCalculator.calculateLDA(obstacle, runwayL);
      double newAsdaL = ParameterCalculator.calculateASDA(obstacle, runwayL);
      double newTodaL = ParameterCalculator.calculateTODA(obstacle, runwayL);

      double oppDistFromThreshold = ParameterCalculator.getOppositeDistFromThreshold(obstacle, getPhysRunwaySelected());
      Obstacle obstacleOpp = new Obstacle(selectedObstacleId, height, width, distanceFromCentre, oppDistFromThreshold);

      double newToraR = ParameterCalculator.calculateTORA(obstacleOpp, runwayR);
      double newLdaR = ParameterCalculator.calculateLDA(obstacleOpp, runwayR);
      double newAsdaR = ParameterCalculator.calculateASDA(obstacleOpp, runwayR);
      double newTodaR = ParameterCalculator.calculateTODA(obstacleOpp, runwayR);

      runwayL.setNewTora(newToraL);
      runwayL.setNewToda(newTodaL);
      runwayL.setNewAsda(newAsdaL);
      runwayL.setNewLda(newLdaL);
      runwayR.setNewTora(newToraR);
      runwayR.setNewToda(newTodaR);
      runwayR.setNewAsda(newAsdaR);
      runwayR.setNewLda(newLdaR);

      ObservableList<LogicalRunway> logicalRunways = FXCollections.observableArrayList();
      logicalRunways.add(runwayL);
      logicalRunways.add(runwayR);
      PhysicalRunway physicalRunway = new PhysicalRunway(runwayMenu.getValue(), logicalRunways);
      physRunwayItem.set(physicalRunway);

      updateUI(runwayL.getTora(), newToraL, runwayL.getToda(), newTodaL, runwayL.getAsda(), newAsdaL, runwayL.getLda(), newLdaL,
          runwayR.getTora(), newToraR, runwayR.getToda(), newTodaR, runwayR.getAsda(), newAsdaR, runwayR.getLda(), newLdaR);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void updateUI(double originalToraL, double revisedToraL,
      double originalTodaL, double revisedTodaL,
      double originalAsdaL, double revisedAsdaL,
      double originalLdaL, double revisedLdaL,
      double originalToraR, double revisedToraR,
      double originalTodaR, double revisedTodaR,
      double originalAsdaR, double revisedAsdaR,
      double originalLdaR, double revisedLdaR) throws SQLException {

    ObservableList<Model.Parameter> leftData = FXCollections.observableArrayList();
    ObservableList<Model.Parameter> rightData = FXCollections.observableArrayList();

    parColumnL.setCellValueFactory(new PropertyValueFactory<>("name"));
    originalColL.setCellValueFactory(new PropertyValueFactory<>("originalValue"));
    revisedColL.setCellValueFactory(new PropertyValueFactory<>("newValue"));

    parColumnR.setCellValueFactory(new PropertyValueFactory<>("name"));
    originalColR.setCellValueFactory(new PropertyValueFactory<>("originalValue"));
    revisedColR.setCellValueFactory(new PropertyValueFactory<>("newValue"));

    leftData.add(new Model.Parameter("TORA (m)", String.valueOf(originalToraL), String.valueOf(revisedToraL)));
    leftData.add(new Model.Parameter("TODA (m)", String.valueOf(originalTodaL), String.valueOf(revisedTodaL)));
    leftData.add(new Model.Parameter("ASDA (m)", String.valueOf(originalAsdaL), String.valueOf(revisedAsdaL)));
    leftData.add(new Model.Parameter("LDA (m)", String.valueOf(originalLdaL), String.valueOf(revisedLdaL)));
    leftTableView.setItems(leftData);

    rightData.add(new Model.Parameter("TORA (m)", String.valueOf(originalToraR), String.valueOf(revisedToraR)));
    rightData.add(new Model.Parameter("TODA (m)", String.valueOf(originalTodaR), String.valueOf(revisedTodaR)));
    rightData.add(new Model.Parameter("ASDA (m)", String.valueOf(originalAsdaR), String.valueOf(revisedAsdaR)));
    rightData.add(new Model.Parameter("LDA (m)", String.valueOf(originalLdaR), String.valueOf(revisedLdaR)));
    rightTableView.setItems(rightData);

    ArrayList<Float> reDeclaredDistances = new ArrayList<>();
    reDeclaredDistances.add((float) revisedToraL);
    reDeclaredDistances.add((float) revisedTodaL);
    reDeclaredDistances.add((float) revisedAsdaL);
    reDeclaredDistances.add((float) revisedLdaL);
    reDeclaredDistances.add((float) revisedToraR);
    reDeclaredDistances.add((float) revisedTodaR);
    reDeclaredDistances.add((float) revisedAsdaR);
    reDeclaredDistances.add((float) revisedLdaR);
    topDownViewController.updateView(runwayMenu.getValue(), reDeclaredDistances);
    topDownViewController.relocateObstacle();
    calculationBreakdown.setDisable(false);
    sideOnViewController.updateView(runwayMenu.getValue(), reDeclaredDistances);
    simultaneousViewController.updateView(runwayMenu.getValue(), reDeclaredDistances);
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
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleRotateButton() {
    topDownViewController.rotateRunway();
  }

  @FXML
  private void handleCalculationBreakdown() throws IOException {
    try {
      Stage stage= new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/CalculationBreakdown.fxml"));
      Parent root = loader.load();

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("Calculation Breakdown");
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private Scene getScene() {
    // This method needs to retrieve the current scene. Depending on your UI structure,
    // you may need to adjust how you retrieve the scene. This is a generic example.
    return darkModeButton.getParentPopup().getOwnerWindow().getScene();
  }

  private void toggleDarkMode() {
    Scene scene = getScene(); // Ensure this method correctly retrieves your scene
    String darkModeStylesheetUrl = getClass().getResource("/CSS/darkMode.css").toExternalForm();
    if (scene.getStylesheets().contains(darkModeStylesheetUrl)) {
      scene.getStylesheets().remove(darkModeStylesheetUrl);
      darkModeButton.setText("Dark Mode");
    } else {
      scene.getStylesheets().add(darkModeStylesheetUrl);
      darkModeButton.setText("Light Mode");
    }
  }

  @FXML
  private void handleToggleDarkMode() {
    toggleDarkMode();
  }

  @FXML
  private void handleManageAirportsButton() {
    try {
      // Load the obstacle definition FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/manageAirports.fxml"));
      Parent root = loader.load();

      AirportManagerController airportManagerController = loader.getController();
      airportManagerController.setDatabaseModel(database);

      // Create a new stage (window) for the obstacle definition
      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("Manage Airports");
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleManageUsersButton() {
    try {
      // Load the obstacle definition FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/manageUsers.fxml"));
      Parent root = loader.load();

      ManagerUsersController managerUsersController = loader.getController();
      managerUsersController.setDatabaseModel(database);

      // Create a new stage (window) for the obstacle definition
      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("Manage Users");
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

