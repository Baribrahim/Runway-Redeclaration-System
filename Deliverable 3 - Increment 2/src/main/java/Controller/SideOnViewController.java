package Controller;

import Model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
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
import Model.Helper.Utility;

public class SideOnViewController implements Initializable {

  @FXML
  private Pane dragPane;
  //physical runway
  @FXML
  private AnchorPane sideOnPane;
  @FXML
  private Rectangle phyRunway;
  @FXML
  private Label designatorL;
  @FXML
  private Label designatorR;
  @FXML
  private Label designatorL1;
  @FXML
  private Label designatorR1;
  @FXML
  private Line thresholdL;
  @FXML
  private Line thresholdR;
  @FXML
  private Line displacedThresholdL;
  @FXML
  private Line displacedThresholdR;
  //tora
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

  //lda
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

  //asda
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

  //toda
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

  //others
  @FXML
  private Line toraOtherLength;
  @FXML
  private Line toraOtherLength1;
  @FXML
  private Line ldaOtherLength;
  @FXML
  private Line ldaOtherLength1;
  @FXML
  private Label toraOtherLabel;
  @FXML
  private Label toraOtherLabel1;
  @FXML
  private Label ldaOtherLabel;
  @FXML
  private Label ldaOtherLabel1;
  @FXML
  private Line toraOtherStart;
  @FXML
  private Line ldaOtherStart;
  @FXML
  private Line ldaOtherEnd;
  @FXML
  private Line toraOtherEnd;
  @FXML
  private Polygon toraOtherArrow;
  @FXML
  private Polygon toraOtherArrow1;
  @FXML
  private Polygon ldaOtherArrow;
  @FXML
  private Polygon ldaOtherArrow1;

  //clearway and stopway
  @FXML
  private Rectangle clearwayL;
  @FXML
  private Rectangle clearwayR;
  @FXML
  private Rectangle stopwayL;
  @FXML
  private Rectangle stopwayR;

  //obstacles
  @FXML
  private Polygon tocsSlope;
  @FXML
  private Polygon alsSlope;

  //scales
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

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    MainPageController.obstacleProperty.addListener(((ObservableValue<? extends Obstacle> observable, Obstacle oldValue, Obstacle newValue) -> {
      if(newValue != null){
        if(oldValue != null){
          oldValue.setDistFThreshold(0);
          oldValue.setDistFCent(0);
        }
        newValue.setDistFThreshold(MainPageController.disFromThreshold.get());
        setUpAlsTocs(newValue,MainPageController.getPhysRunwaySelected().getLogicalRunways().get(0));
      }
    }));

    MainPageController.disFromThreshold.addListener((observable, oldValue, newValue) -> {
      setUpAlsTocs(MainPageController.getObstacleSelected(),MainPageController.getPhysRunwaySelected().getLogicalRunways().get(0));
    });

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
//    obstacleRectangle.setX(obstacleX);
//    obstacleRectangle.setY(obstacleY);
//    obstacleRectangle.setWidth(obstacle.getWidth());
//    obstacleRectangle.setHeight(obstacleVisualHeight); // Set a fixed height
//
//    // Ensure the obstacle is visible
//    obstacleRectangle.setVisible(true);
  }

  // Below are the example methods for calculating X and Y coordinates; you need to adjust them according to the actual view size and scale
  private double calculateXPosition(double distanceFromThreshold) {
    // Get the x-coordinate of the runway threshold defined in FXML
    final double thresholdX = thresholdL.getLayoutX();
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


  private double[] oldAndNewValue(String type, LogicalRunway logicalRunway){
    double originalValue = 0;
    double newValue = 0;
    switch (type) {
      case "TORA" -> {
        originalValue = logicalRunway.getTora();
        newValue = logicalRunway.getNewTora();
      }
      case "LDA" -> {
        originalValue = logicalRunway.getLda();
        newValue = logicalRunway.getNewLda();
      }
      case "ASDA" -> {
        originalValue = logicalRunway.getAsda();
        newValue = logicalRunway.getNewAsda();
      }
      case "TODA" -> {
        originalValue = logicalRunway.getToda();
        newValue = logicalRunway.getNewToda();
      }
    }
    return new double[]{originalValue,newValue};
  }

  protected void setUpScale(LogicalRunway logRunway){
    double tora = logRunway.getTora();
    int scaleRange = Utility.getScaleRange(tora);
    //setting up scale proportion
    scaleLength.setLayoutX(scaleLength.getLayoutX());
    scaleLength.setWidth(scaleRange*toraLength.getEndX()/tora);
    scaleStart.setLayoutX(scaleLength.getLayoutX());
    double length = scaleLength.getWidth();
    scaleStart.setWidth(length/3);
    scaleEnd.setLayoutX(scaleLength.getLayoutX()+length*2/3);
    scaleEnd.setWidth(length/3);

    //setting up scale labels
    scale500.setText(""+scaleRange/3);
    scale1000.setText(""+scaleRange*2/3);
    scale1500.setText(""+scaleRange);
    scale0.setLayoutX(scaleStart.getLayoutX()-scale0.getWidth());
    scale500.setLayoutX(scaleStart.getLayoutX() + length/3 - scale500.getWidth());
    scale1000.setLayoutX(scaleEnd.getLayoutX() -scale1000.getWidth());
    scale1500.setLayoutX(scaleEnd.getLayoutX() + scaleEnd.getWidth()-scale1500.getWidth());
    scaleUnit.setLayoutX(scaleEnd.getLayoutX() + scaleEnd.getWidth() + 10);
    scaleLabel.setLayoutX(scaleLength.getLayoutX() + (length - scaleLabel.getWidth())/2);
  }

  private void setUpPhyRunway(PhysicalRunway physicalRunway, LogicalRunway selectedLogRunway){
    LogicalRunway lLogicalRunway = physicalRunway.getLogicalRunways().get(0);
    LogicalRunway rLogicalRunway = physicalRunway.getLogicalRunways().get(1);

    //Set Up Threshold
    thresholdL.setLayoutX(phyRunway.getLayoutX());
    thresholdR.setLayoutX(phyRunway.getLayoutX()+phyRunway.getWidth());

    //Set Up Designator
    String lDesignator = lLogicalRunway.getDesignator();
    String rDesignator = rLogicalRunway.getDesignator();
    //Set Up DisplacedThreshold
    double lDisplacedThreshold = lLogicalRunway.getDisplacedThreshold();
    double rDisplacedThreshold = rLogicalRunway.getDisplacedThreshold();
    double lDisplacedThresholdX;
    double rDisplacedThresholdX;

    //change in designator and displacedThreshold as logical runway changes
    designatorL.setText(lDesignator.substring(0, lDesignator.length()-1));
    designatorR.setText(rDesignator.substring(0, rDesignator.length()-1));
    designatorL1.setText(lDesignator.substring(lDesignator.length()-1));
    designatorR1.setText(rDesignator.substring(rDesignator.length()-1));
    lDisplacedThresholdX = thresholdL.getLayoutX() + getNumberOfPx(lDisplacedThreshold,lLogicalRunway);
    rDisplacedThresholdX = thresholdR.getLayoutX() - getNumberOfPx(rDisplacedThreshold,rLogicalRunway);

    displacedThresholdL.setLayoutX(lDisplacedThresholdX);
    displacedThresholdR.setLayoutX(rDisplacedThresholdX);
  }

  protected void setUpStopwayAndClearway(PhysicalRunway physicalRunway, LogicalRunway selectedLogRunway){
    LogicalRunway lLogicalRunway = physicalRunway.getLogicalRunways().get(0);
    LogicalRunway rLogicalRunway = physicalRunway.getLogicalRunways().get(1);
    setStopClearway(lLogicalRunway,"Right");
    setStopClearway(rLogicalRunway,"Left");
  }

  private void setStopClearway(LogicalRunway logicalRunway,String leftOrRightWay){
    //Set Up  Variable
    double stopwayLength = logicalRunway.getStopway();
    double stopwayWidthPx = getNumberOfPx(stopwayLength,logicalRunway);
    double clearwayLength = logicalRunway.getClearway();
    double clearwayWidthPx = getNumberOfPx(clearwayLength,logicalRunway);
    double oriStopwayX;
    double oriClearwayX;
    Rectangle stopway;
    Rectangle clearway;

    //Set Up Variable based on left or right
    if (leftOrRightWay.equals("Left")){
      oriStopwayX = thresholdL.getLayoutX() - stopwayWidthPx;
      oriClearwayX = thresholdL.getLayoutX() - clearwayWidthPx;
      stopway = stopwayL;
      clearway = clearwayL;
    } else {
      oriStopwayX = thresholdR.getLayoutX();
      oriClearwayX = thresholdR.getLayoutX();
      stopway = stopwayR;
      clearway = clearwayR;
    }

    // Set Values for both clearway and stopway
    setStopClearwayValue(stopway,stopwayLength,stopwayWidthPx,oriStopwayX);
    setStopClearwayValue(clearway,clearwayLength,clearwayWidthPx,oriClearwayX);
  }

  private void setStopClearwayValue(Rectangle way,double length, double widthPx, double oriWayX){
    if (length != 0 ){
      way.setWidth(widthPx);
    }else {
      way.setWidth(0);
    }
    way.setLayoutX(oriWayX);
  }

  private void setUpAlsTocs(Obstacle obstacle, LogicalRunway logicalRunway){
    double obsHeight = obstacle.getHeight();
    double slopeWidth = obstacle.getAlsTocs();
    double distanceFromThreshold = obstacle.getDistanceFromThreshold();

    //Get Number of Pixel
    double slopeWidthPx = getNumberOfPx(slopeWidth,logicalRunway);
    double obsHeightPx = getNumberOfPx(obsHeight,logicalRunway);
    double distanceFromThresholdPx = getNumberOfPx(distanceFromThreshold,logicalRunway);

    Polygon slope;
    boolean usingAls = ParameterCalculator.getFlightMethod(obstacle,logicalRunway).equals(ParameterCalculator.TAKE_OFF_AWAY_LANDING_OVER);
    if (usingAls){
      tocsSlope.setVisible(false);
      alsSlope.setVisible(true);
      slope = alsSlope;
      slope.setLayoutX(displacedThresholdL.getLayoutX()+distanceFromThresholdPx);
      ObservableList<Double> points = slope.getPoints();
      points.set(3,-obsHeightPx*10);
      //points.set(3,-20d);
      points.set(0, points.get(4)+slopeWidthPx);
    }else {
      tocsSlope.setVisible(true);
      alsSlope.setVisible(false);
      slope = tocsSlope;
      slope.setLayoutX(displacedThresholdL.getLayoutX()+distanceFromThresholdPx);
      ObservableList<Double> points = slope.getPoints();
      points.set(3,-obsHeightPx*10);
      //points.set(3,-20d);
      points.set(0, points.get(4)-slopeWidthPx);
    }
  }

  private double getMeterPerPx(LogicalRunway logicalRunway){
    return logicalRunway.getTora()/ phyRunway.getWidth();
  }

  private double getNumberOfPx(double length,LogicalRunway logicalRunway){
    return length/getMeterPerPx(logicalRunway);
  }

  private double getLabelLayout(Line start,Line length,Label label){
    return start.getLayoutX() + (length.getEndX()/2 - label.getWidth()/2);
  }

//  public AnchorPane getSideOnPane() {
//    return sideOnPane;
//  }

  public Pane getDragPane() {
    return dragPane;
  }

  public void updateView(String runwayName, ArrayList<Float> parameters) throws SQLException {
    designatorL.setText(runwayName.split("/")[0]);
    designatorR.setText(runwayName.split("/")[1]);
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
