package Controller;

import Model.LogicalRunway;
import Model.PhysicalRunway;
import Model.RunwayParameterSpan;
import Model.DatabaseModel;
import Model.Obstacle;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class SideOnViewController implements Initializable {

  @FXML
  private Pane dragPane;
  //The Physical Runway
  @FXML
  private AnchorPane sideOnViewPane;
  @FXML
  private Rectangle physicalRunway;
  @FXML
  private Label leftDesignator;
  @FXML
  private Label rightDesignator;
  @FXML
  private Label leftDesignator1;
  @FXML
  private Label rightDesignator1;
  @FXML
  private Line leftThreshold;
  @FXML
  private Line rightThreshold;
  @FXML
  private Line leftDisplacedThreshold;
  @FXML
  private Line rightDisplacedThreshold;

  //TORA
  @FXML
  private Line toraStart;
  @FXML
  private Line toraEnd;
  @FXML
  private Line toraLength;
  @FXML
  private Label toraLabel;
  @FXML
  private Polygon toraArrow;
  @FXML
  private Line rToraStart;
  @FXML
  private Line rToraEnd;
  @FXML
  private Line rToraLength;
  @FXML
  private Label rToraLabel;
  @FXML
  private Polygon rToraArrow;

  //LDA
  @FXML
  private Line ldaStart;
  @FXML
  private Line ldaEnd;
  @FXML
  private Line ldaLength;
  @FXML
  private Label ldaLabel;
  @FXML
  private Polygon ldaArrow;
  @FXML
  private Line rLdaStart;
  @FXML
  private Line rLdaEnd;
  @FXML
  private Line rLdaLength;
  @FXML
  private Label rLdaLabel;
  @FXML
  private Polygon rLdaArrow;

  //ASDA
  @FXML
  private Line asdaStart;
  @FXML
  private Line asdaEnd;
  @FXML
  private Line asdaLength;
  @FXML
  private Label asdaLabel;
  @FXML
  private Polygon asdaArrow;
  @FXML
  private Line rAsdaStart;
  @FXML
  private Line rAsdaEnd;
  @FXML
  private Line rAsdaLength;
  @FXML
  private Label rAsdaLabel;
  @FXML
  private Polygon rAsdaArrow;

  //TODA
  @FXML
  private Line todaStart;
  @FXML
  private Line todaEnd;
  @FXML
  private Line todaLength;
  @FXML
  private Label todaLabel;
  @FXML
  private Polygon todaArrow;
  @FXML
  private Line rTodaStart;
  @FXML
  private Line rTodaEnd;
  @FXML
  private Line rTodaLength;
  @FXML
  private Label rTodaLabel;
  @FXML
  private Polygon rTodaArrow;

  //EXTRAS
  @FXML
  private Line toraExtraLength;
  @FXML
  private Line toraExtraLength1;
  @FXML
  private Line ldaExtraLength;
  @FXML
  private Line ldaExtraLength1;
  @FXML
  private Label toraExtraLabel;
  @FXML
  private Label toraExtraLabel1;
  @FXML
  private Label ldaExtraLabel;
  @FXML
  private Label ldaExtraLabel1;
  @FXML
  private Line toraExtraStart;
  @FXML
  private Line ldaExtraStart;
  @FXML
  private Line ldaExtraEnd;
  @FXML
  private Line toraExtraEnd;
  @FXML
  private Polygon toraExtraArrow;
  @FXML
  private Polygon toraExtraArrow1;
  @FXML
  private Polygon ldaExtraArrow;
  @FXML
  private Polygon ldaExtraArrow1;

  //Stopway and Clearway
  @FXML
  private Rectangle leftStopway;
  @FXML
  private Rectangle rightStopway;
  @FXML
  private Rectangle leftClearway;
  @FXML
  private Rectangle rihgtClearway;

  //Obstacles
  @FXML
  private Polygon tocsSlope;
  @FXML
  private Polygon alsSlope;
  @FXML
  private Rectangle obstacleRectangle;

  //Scales
  @FXML
  private Label scaleLabel;
  @FXML
  private Rectangle scaleStart;
  @FXML
  private Rectangle scaleEnd;
  @FXML
  private Rectangle scaleLength;
  @FXML
  private Label scale0;
  @FXML
  private Label scale500;
  @FXML
  private Label scale1000;
  @FXML
  private Label scale1500;
  @FXML
  private Label scaleUnit;

  //Colours
  @FXML
  private Rectangle stopwayColour;
  @FXML
  private Rectangle clearwayColour;
  @FXML
  private Rectangle obstacleColour;
  @FXML
  private Rectangle displacedThresholdColour;
  @FXML
  private Rectangle minCGArea;

  private final double visualRunwayLength = 600.0; // 这应该与physicalRunway.getWidth()相匹配

  // 实际跑道的长度（米）
  private final double actualRunwayLength = 3000.0; // 这是一个例子，你需要用你的实际跑道长度替换它

  // 计算缩放因子
  private final double scaleFactor = actualRunwayLength / visualRunwayLength;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }
  public void displayObstacle(Obstacle obstacle) {
    //double actualRunwayLength = database.getActualRunwayLength(selectedRunwayId);
    //double visualRunwayLength = 600.0;

    //double unitDistance = actualRunwayLength / visualRunwayLength;
    // User-provided distance values
    double distanceFromThreshold = obstacle.getDistanceFromThreshold();
    double distanceFromCentre = obstacle.getDistanceFromCentre();

    // Calculate the obstacle's x and y coordinates in the view
    double obstacleX = calculateXPosition(distanceFromThreshold);
    //double obstacleX = obstacle.getDistanceFromThreshold() / unitDistance;

    double obstacleY = calculateYPosition(distanceFromCentre, obstacle.getWidth());
    // Determine the runway's layoutY and height
    double runwayLayoutY = 208.0; // This value is obtained from FXML
    double runwayHeight = 15.0;   // Likewise, obtained from FXML

    // Set the visual height of the obstacle
    double obstacleVisualHeight = 10.0; // This value can be adjusted according to your view needs

    // Calculate the obstacle's Y coordinate to align its bottom edge with the top of the runway
    //double obstacleY = runwayLayoutY + runwayHeight - obstacleVisualHeight;

    // Set or update the position and size of the obstacle rectangle
    obstacleRectangle.setX(obstacleX);
    obstacleRectangle.setY(obstacleY);
    obstacleRectangle.setWidth(obstacle.getWidth());
    obstacleRectangle.setHeight(obstacleVisualHeight); // Set a fixed height

    // Ensure the obstacle is visible
    obstacleRectangle.setVisible(true);
  }

  // Below are the example methods for calculating X and Y coordinates; you need to adjust them according to the actual view size and scale
  private double calculateXPosition(double distanceFromThreshold) {
    // Get the x-coordinate of the runway threshold defined in FXML
    final double thresholdX = leftThreshold.getLayoutX();
    // Assuming each pixel represents a specific actual distance, directly convert it to an x-coordinate in the view
    final double unitDistance = 50; // Actual distance each unit represents (e.g., 1 pixel represents 1 meter)
    return thresholdX + (distanceFromThreshold / unitDistance);
  }

  private double calculateYPosition(double distanceFromCentre, double obstacleWidth) {
    // Assume the Y-coordinate of the runway centerline is 350
    final double centreY = 350;
    // Calculate the Y coordinate, since it's a top view, the width does not affect the Y coordinate, can be disregarded
    return centreY - (obstacleWidth / 2);
  }

  public void updateView(String runwayName, ArrayList<Float> parameters) throws SQLException {
    leftDesignator.setText(runwayName.split("/")[0]);
    rightDesignator.setText(runwayName.split("/")[1]);
    ldaLabel.setText("LDA = " + parameters.get(3) + "m");
    todaLabel.setText("TODA = " + parameters.get(1) + "m");
    asdaLabel.setText("ASDA = " + parameters.get(2) + "m");
    toraLabel.setText("TORA = " + parameters.get(0) + "m");
    rToraLabel.setText("TORA = " + parameters.get(5) + "m");
    rAsdaLabel.setText("ASDA = " + parameters.get(6) + "m");
    rTodaLabel.setText("TODA = " + parameters.get(4) + "m");
    rLdaLabel.setText("LDA = " + parameters.get(7) + "m");
  }
}
