package Controller;

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
import org.example.SimultaneousView;

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
  private Tab obstacleDefinitionViewTab;
  @FXML
  private TableView<Parameter> leftTableView;
  @FXML
  private TableView<Parameter> rightTableView;
  @FXML
  private TableView<Parameter> notificationsTable;

  @FXML
  private Button calculationBreakdown;

  private TopDownViewController topDownViewController;
  private SideOnViewController sideOnViewController;
  private SimultaneousView simultaneousViewController;

  private ObstacleDefinitionController obstacleDefinitionController;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/TopDownView.fxml"));
    Parent root1 = null;
    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/SideView.fxml"));
    Parent root2 = null;
    FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/SimultaneousView.fxml"));
    Parent root3 = null;
    FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/ObstacleDefinitionView.fxml"));
    Parent root4 = null;

    try {
      root1 = loader1.load();
      root2 = loader2.load();
      root3 = loader3.load();
      root4 = loader4.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    topDownViewController = loader1.getController();
    sideOnViewController = loader2.getController();
    simultaneousViewController = loader3.getController();
    topViewTab.setContent(root1);
    sideViewTab.setContent(root2);
    simultaneousViewTab.setContent(root3);
    obstacleDefinitionController = loader4.getController();
    obstacleDefinitionViewTab.setContent(root4);
  }
}

