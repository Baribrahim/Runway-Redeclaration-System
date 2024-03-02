package org.example;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;

public class MainPageController implements Initializable {

  //fxml elements
  @FXML
  private Button importDataButton;

  @FXML
  private Button exportDataButton;

  @FXML
  private MenuButton airportMenu;
  @FXML
  private MenuButton runwayMenu;
  @FXML
  private MenuButton obstacleMenu;
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

  private TopDownViewController topDownViewController;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/TopDownView.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    topDownViewController = loader.getController();
    topViewTab.setContent(root);
  }
}

