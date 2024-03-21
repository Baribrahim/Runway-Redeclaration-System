package Controller;

import Model.LogicalRunway;
import Model.Obstacle;
import Model.ParameterCalculator;
import Model.PhysicalRunway;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class CalculationBreakdownController implements Initializable {

  @FXML
  private Label resa;
  @FXML
  private Label blastProtection;
  @FXML
  private Label stripEnd;
  @FXML
  private Label designatorL;
  @FXML
  private Label designatorR;
  @FXML
  private Tab TORATabL;
  @FXML
  private Tab TODATabL;
  @FXML
  private Tab ASDATabL;
  @FXML
  private Tab LDATabL;
  @FXML
  private Tab TORATabR;
  @FXML
  private Tab TODATabR;
  @FXML
  private Tab ASDATabR;
  @FXML
  private Tab LDATabR;
  @FXML
  private Label TORABreakdownL;
  @FXML
  private Label TODABreakdownL;
  @FXML
  private Label ASDABreakdownL;
  @FXML
  private Label LDABreakdownL;
  @FXML
  private Label TORABreakdownR;
  @FXML
  private Label TODABreakdownR;
  @FXML
  private Label ASDABreakdownR;
  @FXML
  private Label LDABreakdownR;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    PhysicalRunway runway = MainPageController.getPhysRunwaySelected();
    Obstacle obstacle = MainPageController.getObstacleSelected();

    designatorL.setText(runway.getLogicalRunways().get(0).getDesignator());
    designatorR.setText(runway.getLogicalRunways().get(1).getDesignator());

    resa.setText("=  "+PhysicalRunway.getResa()+"  m");
    stripEnd.setText("=  "+ PhysicalRunway.getStripEnd()+"  m");
    blastProtection.setText("=  "+PhysicalRunway.getBlastProtection()+"  m");

    LogicalRunway logRunway1 = runway.getLogicalRunways().get(0);
    LogicalRunway logRunway2 = runway.getLogicalRunways().get(1);
    TORABreakdownL.setText(ParameterCalculator.toraBreakdown(obstacle, logRunway1));
    TODABreakdownL.setText(ParameterCalculator.todaBreakdown(obstacle, logRunway1));
    LDABreakdownL.setText(ParameterCalculator.ldaBreakdown(obstacle, logRunway1));
    ASDABreakdownL.setText(ParameterCalculator.asdaBreakdown(obstacle, logRunway1));

    Obstacle obstacle1 = MainPageController.getObstacleSelected();
    obstacle1.setDistFThreshold(obstacle.getDistanceFromThreshold());
    obstacle1.setDistFThreshold(ParameterCalculator.getOppositeDistFromThreshold(obstacle,runway));
    TORABreakdownR.setText(ParameterCalculator.toraBreakdown(obstacle1, logRunway2));
    TODABreakdownR.setText(ParameterCalculator.todaBreakdown(obstacle1, logRunway2));
    LDABreakdownR.setText(ParameterCalculator.ldaBreakdown(obstacle1, logRunway2));
    ASDABreakdownR.setText(ParameterCalculator.asdaBreakdown(obstacle1, logRunway2));

  }

}
