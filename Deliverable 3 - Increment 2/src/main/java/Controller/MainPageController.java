package Controller;

import Model.DatabaseModel;
import Model.Obstacle;
import Model.LogicalRunway;
import Model.ParameterCalculator;
import Model.RunwayParameterSpan;
import Model.DatabaseModel;
import java.io.File;
import javafx.scene.control.MenuItem;
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

//
//  @FXML
//  private void calculateRunwayDistances() {
//    try {
//      String selectedObstacleId = obstacleMenu.getValue(); // 用户选择的障碍物ID
//      float height = database.getObstacleHeight(selectedObstacleId);
//      float width = database.getObstacleWidth(selectedObstacleId);
//
//      if (height == -1 || width == -1) {
//        // 障碍物高度或宽度未找到，处理错误情况
//        System.out.println("障碍物数据不完整");
//        return;
//      }
//
//      // 构建障碍物实例
//      Obstacle obstacle = new Obstacle(selectedObstacleId, height, width);
//
//      // 假设您已经有方式获取选定跑道的参数，我们这里只是示意
//      String selectedRunway = runwayMenu.getValue();
//      ArrayList<Float> runwayParameters = database.getLogicalRunwayParameters(selectedRunway);
//      LogicalRunway runway = new LogicalRunway(selectedRunway, runwayParameters.get(0), runwayParameters.get(1), runwayParameters.get(2), runwayParameters.get(3));
//
//      // 使用障碍物和跑道信息计算跑道参数
//      double newTora = ParameterCalculator.calculateTORA(obstacle, runway);
//      double newLda = ParameterCalculator.calculateLDA(obstacle, runway);
//      double newAsda = ParameterCalculator.calculateASDA(obstacle, runway);
//      double newToda = ParameterCalculator.calculateTODA(obstacle, runway);
//
//      // 更新UI或其他逻辑来显示计算结果
//      //updateUI(newTora, newLda, newAsda, newToda);
//      updateUI(runway.getTora(), newTora, runway.getToda(), newToda, runway.getAsda(), newAsda, runway.getLda(), newLda);
//
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }
@FXML
private void calculateRunwayDistances() {
  try {
    // 获取障碍物和跑道的数据
    String selectedObstacleId = obstacleMenu.getValue(); // 用户选择的障碍物ID
    float height = database.getObstacleHeight(selectedObstacleId);
    float width = database.getObstacleWidth(selectedObstacleId);

    if (height == -1 || width == -1) {
      // 障碍物高度或宽度未找到，处理错误情况
      System.out.println("障碍物数据不完整");
      return;
    }

    Obstacle obstacle = new Obstacle(selectedObstacleId, height, width);

    ArrayList<Float> runwayParameters = database.getLogicalRunwayParameters(runwayMenu.getValue());

    // 构建LogicalRunway对象
    LogicalRunway runway = new LogicalRunway(runwayMenu.getValue(), runwayParameters.get(0), runwayParameters.get(1), runwayParameters.get(2), runwayParameters.get(3));

    // 使用ParameterCalculator计算修正后的参数
//    runway.setNewTora(ParameterCalculator.calculateTORA(obstacle, runway));
//    runway.setNewLda(ParameterCalculator.calculateLDA(obstacle, runway));
//    runway.setNewAsda(ParameterCalculator.calculateASDA(obstacle, runway));
//    runway.setNewToda(ParameterCalculator.calculateTODA(obstacle, runway));
    double newTora = ParameterCalculator.calculateTORA(obstacle, runway);
    double newLda = ParameterCalculator.calculateLDA(obstacle, runway);
    double newAsda = ParameterCalculator.calculateASDA(obstacle, runway);
    double newToda = ParameterCalculator.calculateTODA(obstacle, runway);


    // 使用刚刚计算的参数更新UI
//    updateUI(runway.getTora(), runway.getNewTora(),
//            runway.getToda(), runway.getNewToda(),
//            runway.getAsda(), runway.getNewAsda(),
//            runway.getLda(), runway.getNewLda());
    updateUI(runway.getTora(), newTora, runway.getToda(), newToda, runway.getAsda(), newAsda, runway.getLda(), newLda);

  } catch (SQLException e) {
    e.printStackTrace();
  }
}
//  public void updateUI(double newTora, double newLda, double newAsda, double newToda) {
//    // 假设您有一个名为RunwayData的类，它有四个属性：tora, toda, asda, lda
//    // 创建两个实例分别表示左侧和右侧跑道的结果
//    RunwayData leftRunwayData = new RunwayData(newTora, newLda, newAsda, newToda);
//    RunwayData rightRunwayData = new RunwayData(newTora, newLda, newAsda, newToda); // 根据需要调整值
//
//    // 清除现有数据
//    leftTableView.getItems().clear();
//    rightTableView.getItems().clear();
//
//    // 添加新数据
//    leftTableView.getItems().add(leftRunwayData);
//    rightTableView.getItems().add(rightRunwayData);
//  }
//private void updateUI(double newTora, double newLda, double newAsda, double newToda) {
//  // 更新每个TextField以显示新计算的跑道参数
//  Platform.runLater(() -> {
//    newTORATextField.setText(String.format("%.2f", newTora));
//    newTODATextField.setText(String.format("%.2f", newToda));
//    newASDATextField.setText(String.format("%.2f", newAsda));
//    newLDATextField.setText(String.format("%.2f", newLda));
//  });
//}
// 该方法假定leftTableView和rightTableView的数据类型已经是LogicalRunway
//private void updateUI(double newTora, double newLda, double newAsda, double newToda) {
//  // 创建两个新的LogicalRunway对象，一个用于左侧跑道，一个用于右侧跑道
//  LogicalRunway newLeftRunwayData = new LogicalRunway("L", newTora, newToda, newAsda, newLda);
//  LogicalRunway newRightRunwayData = new LogicalRunway("R", newTora, newToda, newAsda, newLda);
//
//  // 更新左侧跑道的TableView
//  leftTableView.getItems().clear(); // 如果您想保留现有的数据，请不要清除它们
//  leftTableView.getItems().add(newLeftRunwayData);
//
//  // 更新右侧跑道的TableView
//  rightTableView.getItems().clear(); // 如果您想保留现有的数据，请不要清除它们
//  rightTableView.getItems().add(newRightRunwayData);
//}
//private void updateUI(LogicalRunway leftRunway, LogicalRunway rightRunway) {
//  Platform.runLater(() -> {
//    // 更新左侧跑道原始参数
//    leftOriginalTORATextField.setText(String.format("%.2f", leftRunway.getTora()));
//    leftOriginalTODATextField.setText(String.format("%.2f", leftRunway.getToda()));
//    leftOriginalASDATextField.setText(String.format("%.2f", leftRunway.getAsda()));
//    leftOriginalLDATextField.setText(String.format("%.2f", leftRunway.getLda()));
//
//    // 更新左侧跑道修正后的参数
//    leftRevisedTORATextField.setText(String.format("%.2f", leftRunway.getNewTora()));
//    leftRevisedTODATextField.setText(String.format("%.2f", leftRunway.getNewToda()));
//    leftRevisedASDATextField.setText(String.format("%.2f", leftRunway.getNewAsda()));
//    leftRevisedLDATextField.setText(String.format("%.2f", leftRunway.getNewLda()));
//
//    // 更新右侧跑道原始参数
//    rightOriginalTORATextField.setText(String.format("%.2f", rightRunway.getTora()));
//    rightOriginalTODATextField.setText(String.format("%.2f", rightRunway.getToda()));
//    rightOriginalASDATextField.setText(String.format("%.2f", rightRunway.getAsda()));
//    rightOriginalLDATextField.setText(String.format("%.2f", rightRunway.getLda()));
//
//    // 更新右侧跑道修正后的参数
//    rightRevisedTORATextField.setText(String.format("%.2f", rightRunway.getNewTora()));
//    rightRevisedTODATextField.setText(String.format("%.2f", rightRunway.getNewToda()));
//    rightRevisedASDATextField.setText(String.format("%.2f", rightRunway.getNewAsda()));
//    rightRevisedLDATextField.setText(String.format("%.2f", rightRunway.getNewLda()));
//  });
//}
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

    // 如果右跑道参数与左跑道参数相同，可以复制左侧参数到右侧
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

