package Controller;

import Model.*;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;



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
  public Tab topViewTab;
  @FXML
  private Tab sideViewTab;
  @FXML
  private Tab simultaneousViewTab;
//  @FXML
//  private TableView<Parameter> leftTableView;
//  @FXML
//  private TableView<Parameter> rightTableView;
  @FXML
  private TableView<RunwayParameter> leftTableView;
  @FXML
  private TableView<RunwayParameter> rightTableView;

  @FXML
  private TableColumn<RunwayParameter, String> parColumn1;
  @FXML
  private TableColumn<RunwayParameter, String> originalCol1;
  @FXML
  private TableColumn<RunwayParameter, String> revisedCol1;
  @FXML
  private TableColumn<RunwayParameter, String> parColumn2;
  @FXML
  private TableColumn<RunwayParameter, String> originalCol2;
  @FXML
  private TableColumn<RunwayParameter, String> revisedCol2;


  @FXML
  private TableView<Parameter> notificationsTable;
  @FXML
  private TextField leftOriginalTORATextField;
  @FXML
  private TextField leftOriginalTODATextField;
  @FXML
  private TextField leftOriginalASDATextField;
  @FXML
  private TextField leftOriginalLDATextField;

  @FXML
  private TextField leftRevisedTORATextField;
  @FXML
  private TextField leftRevisedTODATextField;
  @FXML
  private TextField leftRevisedASDATextField;
  @FXML
  private TextField leftRevisedLDATextField;

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

  ObservableList<RunwayParameter> leftRunwayParameters = FXCollections.observableArrayList();
  ObservableList<RunwayParameter> rightRunwayParameters = FXCollections.observableArrayList();

  public static ObjectProperty<PhysicalRunway> physRunwayItem = new SimpleObjectProperty<>();
  public static ObjectProperty<Airport> airportItem = new SimpleObjectProperty();
  public static ObjectProperty<Obstacle> obstacleProperty = new SimpleObjectProperty<>();


  public static ObservableList<Obstacle> obstacles = FXCollections.observableArrayList();
  public static ObservableList<String> airportNames = FXCollections.observableArrayList();

  public ObservableList<Obstacle> getObstacles(){return obstacles;}
  public static PhysicalRunway getPhysRunwaySelected() {return physRunwayItem.get();}
  //public static boolean needRedeclare(){return needRedeclare;}
  public static Obstacle getObstacleSelected() {return obstacleProperty.get();}

  public static boolean beforeCalculation = false;

  private static final Logger logger = LogManager.getLogger(MainPageController.class);

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
    beforeCalculation = true;

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

          topDownViewController.displayObstacle(obstacle);
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


//  @FXML
//  public void setStripEnd(ActionEvent event){
//    //resetInactivityTimer();
//    try{
//      double stripEnd = Double.parseDouble(stripEndTextField.getText().trim());
//      if(stripEnd < 0 || stripEnd > 100){throw new NumberFormatException();}
//      PhysicalRunway.setStripEnd(stripEnd);
//      //addNotificationLabel("Status: Strip End Value Changed to " + stripEnd);
//    } catch (NumberFormatException e){
//      //new Error().showError(stripEndTextField, "Invalid input for strip end \nHint: please input a numerical value within this range 0-100", "60");
//      //addNotificationLabel("Error: Invalid input for strip end! Hint: please input a numerical value within this range 0-100");
//    }
//  }
//
//  @FXML
//  public void setBlastProtection(ActionEvent event){
//    //resetInactivityTimer();
//    try{
//      double blastProtection = Double.parseDouble(blastProtectionField.getText().trim());
//      if(blastProtection < 300 || blastProtection > 500){
//        throw new NumberFormatException();
//      }
//      PhysicalRunway.setBlastProtection(blastProtection);
//      //addNotificationLabel("Status: Blast Protection Value Changed to " + blastProtection);
//    } catch (NumberFormatException e){
//      //new Error().showError(blastProtectionField, "Invalid input for blast protection\nHint: please input a numerical value within this range: 300-500 (for safety purpose)", "300");
//      //addNotificationLabel("Error: Invalid input for blast protection! Hint: please input a numerical value within this range: 300-500 (for safety purpose)");
//    }
//  }
//
//  @FXML
//  public void setRESA(ActionEvent event){
//    //resetInactivityTimer();
//    try{
//      double resa = Double.parseDouble(resaTextField.getText().trim());
//      if(resa < 240 || resa > 500){
//        throw new NumberFormatException();
//      }
//      PhysicalRunway.setResa(resa);
//      //addNotificationLabel("Status: RESA Value Changed to " + resa);
//    } catch (NumberFormatException e){
//      //new Error().showError(resaTextField, "Invalid input for RESA\nHint: please input a numerical value within this range 240-500 (for safety purpose)", "240");
//      //addNotificationLabel("Error: Invalid input for RESA! Hint: please input a numerical value within this range 240-500 (for safety purpose)");
//    }
//  }

@FXML
private void calculateRunwayDistances() {
//  try {
//    String selectedObstacleId = obstacleMenu.getValue();
//    float height = database.getObstacleHeight(selectedObstacleId);
//    float width = database.getObstacleWidth(selectedObstacleId);
//
//    if (height == -1 || width == -1) {
//      System.out.println("Obstacle information not completed");
//      return;
//    }
//
//    Obstacle obstacle = new Obstacle(selectedObstacleId, height, width);
//
//    ArrayList<Float> runwayParameters = database.getLogicalRunwayParameters(runwayMenu.getValue());
//
//
//    LogicalRunway runway = new LogicalRunway(runwayMenu.getValue(), runwayParameters.get(0), runwayParameters.get(1), runwayParameters.get(2), runwayParameters.get(3));
//
//    double newTora = ParameterCalculator.calculateTORA(obstacle, runway);
//    double newLda = ParameterCalculator.calculateLDA(obstacle, runway);
//    double newAsda = ParameterCalculator.calculateASDA(obstacle, runway);
//    double newToda = ParameterCalculator.calculateTODA(obstacle, runway);
//
//    updateUI(runway.getTora(), newTora, runway.getToda(), newToda, runway.getAsda(), newAsda, runway.getLda(), newLda);
//
//  } catch (SQLException e) {
//    e.printStackTrace();
//  }
  updateUI();
}

  private void editColumn(TableColumn<RunwayParameter, String> tableColumn) {
    tableColumn.setResizable(false);
    tableColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          this.setStyle("-fx-background-color: rgb(244,244,244); -fx-alignment: CENTER;-fx-font-family: Verdana; -fx-padding: 7");
          // Set the text of the cell to the item
          setText(item);
        } else {
          setText(null);
        }
      }
    });
  }

  private void updateUI() {
    Platform.runLater(() -> {
      ObservableList<RunwayParameter> leftData = FXCollections.observableArrayList();
      LogicalRunway logRunway1 = getPhysRunwaySelected().getLogicalRunways().get(0);
      ObservableList<RunwayParameter> rightData = FXCollections.observableArrayList();
      LogicalRunway logRunway2 = getPhysRunwaySelected().getLogicalRunways().get(1);

      parColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
      parColumn1.setText(logRunway1.getDesignator());
      originalCol1.setCellValueFactory(new PropertyValueFactory<>("originalValue"));
      revisedCol1.setCellValueFactory(new PropertyValueFactory<>("newValue"));
      editColumn(parColumn1);
      editColumn(originalCol1);
      editColumn(revisedCol1);

      parColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
      parColumn2.setText(logRunway2.getDesignator());
      originalCol2.setCellValueFactory(new PropertyValueFactory<>("originalValue"));
      revisedCol2.setCellValueFactory(new PropertyValueFactory<>("newValue"));
      editColumn(parColumn2);
      editColumn(originalCol2);
      editColumn(revisedCol2);


//      leftData.add(new RunwayParameter("TORA (m)", String.valueOf(logRunway1.getTora()), "-"));
//      leftData.add(new RunwayParameter("TODA (m)", String.valueOf(logRunway1.getToda()), "-"));
//      leftData.add(new RunwayParameter("ASDA (m)", String.valueOf(logRunway1.getAsda()), "-"));
//      leftData.add(new RunwayParameter("LDA (m)", String.valueOf(logRunway1.getLda()), "-"));
//
//      rightData.add(new RunwayParameter("TORA (m)", String.valueOf(logRunway2.getTora()), "-"));
//      rightData.add(new RunwayParameter("TODA (m)", String.valueOf(logRunway2.getToda()), "-"));
//      rightData.add(new RunwayParameter("ASDA (m)", String.valueOf(logRunway2.getAsda()), "-"));
//      rightData.add(new RunwayParameter("LDA (m)", String.valueOf(logRunway2.getLda()), "-"));

      // 更新左边的表格数据，使用传入的原始值和修订后的值
      leftData.add(new RunwayParameter("TORA (m)", String.valueOf(logRunway1.getTora()), String.valueOf(logRunway1.getNewTora())));
      leftData.add(new RunwayParameter("TODA (m)", String.valueOf(logRunway1.getToda()), String.valueOf(logRunway1.getNewToda())));
      leftData.add(new RunwayParameter("ASDA (m)", String.valueOf(logRunway1.getAsda()), String.valueOf(logRunway1.getNewAsda())));
      leftData.add(new RunwayParameter("LDA (m)", String.valueOf(logRunway1.getLda()), String.valueOf(logRunway1.getNewLda())));

      // 更新右边的表格数据，这里假设右边表格显示另一逻辑跑道的数据，如果逻辑不同，请做相应调整
      rightData.add(new RunwayParameter("TORA (m)", String.valueOf(logRunway2.getTora()), String.valueOf(logRunway2.getNewTora())));
      rightData.add(new RunwayParameter("TODA (m)", String.valueOf(logRunway2.getToda()), String.valueOf(logRunway2.getNewToda())));
      rightData.add(new RunwayParameter("ASDA (m)", String.valueOf(logRunway2.getAsda()), String.valueOf(logRunway2.getNewAsda())));
      rightData.add(new RunwayParameter("LDA (m)", String.valueOf(logRunway2.getLda()), String.valueOf(logRunway2.getNewLda())));

      leftTableView.setItems(leftData);
      rightTableView.setItems(rightData);


    });
  }

  private void updateUI(double originalTora, double revisedTora,
                      double originalToda, double revisedToda,
                      double originalAsda, double revisedAsda,
                      double originalLda, double revisedLda) {
  Platform.runLater(() -> {
    leftOriginalTORATextField.setText(String.format("%.2f", originalTora));
    leftRevisedTORATextField.setText(String.format("%.2f", revisedTora));
    leftOriginalTODATextField.setText(String.format("%.2f", originalToda));
    leftRevisedTODATextField.setText(String.format("%.2f", revisedToda));
    leftOriginalASDATextField.setText(String.format("%.2f", originalAsda));
    leftRevisedASDATextField.setText(String.format("%.2f", revisedAsda));
    leftOriginalLDATextField.setText(String.format("%.2f", originalLda));
    leftRevisedLDATextField.setText(String.format("%.2f", revisedLda));

    rightOriginalTORATextField.setText(leftOriginalTORATextField.getText());
    rightRevisedTORATextField.setText(leftRevisedTORATextField.getText());
    rightOriginalTODATextField.setText(leftOriginalTODATextField.getText());
    rightRevisedTODATextField.setText(leftRevisedTODATextField.getText());
    rightOriginalASDATextField.setText(leftOriginalASDATextField.getText());
    rightRevisedASDATextField.setText(leftRevisedASDATextField.getText());
    rightOriginalLDATextField.setText(leftOriginalLDATextField.getText());
    rightRevisedLDATextField.setText(leftRevisedLDATextField.getText());
  });
}


//private void updateUI(double originalTora, double revisedTora,
//                      double originalToda, double revisedToda,
//                      double originalAsda, double revisedAsda,
//                      double originalLda, double revisedLda) {
//  Platform.runLater(() -> {
//    leftOriginalTORATextField.setText(String.format("%.2f", originalTora));
//    leftRevisedTORATextField.setText(String.format("%.2f", revisedTora));
//    leftOriginalTODATextField.setText(String.format("%.2f", originalToda));
//    leftRevisedTODATextField.setText(String.format("%.2f", revisedToda));
//    leftOriginalASDATextField.setText(String.format("%.2f", originalAsda));
//    leftRevisedASDATextField.setText(String.format("%.2f", revisedAsda));
//    leftOriginalLDATextField.setText(String.format("%.2f", originalLda));
//    leftRevisedLDATextField.setText(String.format("%.2f", revisedLda));
//
//    rightOriginalTORATextField.setText(leftOriginalTORATextField.getText());
//    rightRevisedTORATextField.setText(leftRevisedTORATextField.getText());
//    rightOriginalTODATextField.setText(leftOriginalTODATextField.getText());
//    rightRevisedTODATextField.setText(leftRevisedTODATextField.getText());
//    rightOriginalASDATextField.setText(leftOriginalASDATextField.getText());
//    rightRevisedASDATextField.setText(leftRevisedASDATextField.getText());
//    rightOriginalLDATextField.setText(leftOriginalLDATextField.getText());
//    rightRevisedLDATextField.setText(leftRevisedLDATextField.getText());
//  });
//}



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

}

