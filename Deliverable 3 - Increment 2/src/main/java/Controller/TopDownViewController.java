package Controller;

import Model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class TopDownViewController implements Initializable {

  private DatabaseModel database = new DatabaseModel();
  @FXML
  private Pane dragPane;
  @FXML
  private AnchorPane topDownRunwayPane;
  @FXML
  private Rectangle minCGArea1;
  @FXML
  private Rectangle minCGArea2;
  @FXML
  private Polyline minCGArea3;
  @FXML
  private Polyline minCGArea4;
  @FXML
  private Polyline minCGArea5;
  @FXML
  private Polyline minCGArea6;
  @FXML
  private Rectangle rightClearway;
  @FXML
  private Rectangle leftClearway;
  @FXML
  private Rectangle rightStopway;
  @FXML
  private Rectangle leftStopway;
  @FXML
  private Rectangle runway;
  @FXML
  private Line centreLine;
  @FXML
  private Label leftDesignator;
  @FXML
  private Label rightDesignator;
  @FXML
  private Line leftDisplacedThreshold;
  @FXML
  private Line rightDisplacedThreshold;
  @FXML
  private Line gradedAreaLine;
  @FXML
  private Label minCGLabel;
  @FXML
  private Label maxCGLabel;
  @FXML
  private Label todaLabel1;
  @FXML
  private Label toraLabel1;
  @FXML
  private Label ldaLabel1;
  @FXML
  private Label asdaLabel1;
  @FXML
  private Line toraExtraLength1;
  @FXML
  private Line toraExtraStart1;
  @FXML
  private Label toraExtraLabel1;
  @FXML
  private Label todaLabel2;
  @FXML
  private Label toraLabel2;
  @FXML
  private Label ldaLabel2;
  @FXML
  private Label asdaLabel2;
  @FXML
  private Line toraExtraLength2;
  @FXML
  private Line toraExtraEnd2;
  @FXML
  private Label toraExtraLabel2;
  @FXML
  private Line toraStart1;
  @FXML
  private Line toraEnd1;
  @FXML
  private Line toraLength1;
  @FXML
  private Line asdaStart1;
  @FXML
  private Line asdaEnd1;
  @FXML
  private Line asdaLength1;
  @FXML
  private Line ldaStart1;
  @FXML
  private Line ldaEnd1;
  @FXML
  private Line ldaLength1;
  @FXML
  private Line todaStart1;
  @FXML
  private Line todaEnd1;
  @FXML
  private Line todaLength1;
  @FXML
  private Line toraStart2;
  @FXML
  private Line toraEnd2;
  @FXML
  private Line toraLength2;
  @FXML
  private Line asdaStart2;
  @FXML
  private Line asdaEnd2;
  @FXML
  private Line asdaLength2;
  @FXML
  private Line ldaStart2;
  @FXML
  private Line ldaEnd2;
  @FXML
  private Line ldaLength2;
  @FXML
  private Line todaStart2;
  @FXML
  private Line todaEnd2;
  @FXML
  private Line todaLength2;
  @FXML
  private Line ldaExtraLength1;
  @FXML
  private Line ldaExtraEnd;
  @FXML
  private Label ldaExtraLabel1;
  @FXML
  private Label ldaExtraLabel;
  @FXML
  private Line ldaExtraLength2;
  @FXML
  private Line ldaExtraStart;
  @FXML
  private Line leftThreshold;
  @FXML
  private Line rightThreshold;
  @FXML
  private Rectangle obstacleBlock;
  @FXML
  private Polygon todaArrow1;
  @FXML
  private Polygon asdaArrow1;
  @FXML
  private Polygon ldaArrow1;
  @FXML
  private Polygon ldaOtherArrow1;
  @FXML
  private Polygon ldaOtherArrow2;
  @FXML
  private Polygon toraArrow1;
  @FXML
  private Polygon toraOtherArrow1;
  @FXML
  private Polygon toraOtherArrow2;
  @FXML
  private Polygon todaArrow2;
  @FXML
  private Polygon asdaArrow2;
  @FXML
  private Polygon ldaArrow2;
  @FXML
  private Polygon toraArrow2;
  @FXML
  private Label leftDirection;
  @FXML
  private Label rightDirection;
  @FXML
  private Rectangle stopwayColour;
  @FXML
  private Rectangle clearwayColour;
  @FXML
  private Rectangle obstacleColour;
  @FXML
  private Rectangle displacedThresholdColour;
  @FXML
  private Rectangle gradedAreaColour;
  @FXML
  private Rectangle scaleLength;
  @FXML
  private Rectangle scaleStart;
  @FXML
  private Rectangle scaleEnd;
  @FXML
  private Label scaleLabel;
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
  @FXML
  private Rectangle minCGArea;

  private boolean isRotated = false;
  private int rotation = 0;
  private boolean labelFlipped = false;
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  protected double[] oldAndNewValue(String type, LogicalRunway logicalRunway){
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

  protected double getLabelLayout(Line start,Line length,Label label){
    return start.getLayoutX() + (length.getEndX()/2 - label.getWidth()/2);
  }

  protected void setUpLine(String type,String LeftorRight, PhysicalRunway physicalRunway,
                           RunwayParameterSpan arrow){
    LogicalRunway logicalRunway = null;
    double originalStartX;
    double originalEndX;
    double leftActualThresholdX = leftThreshold.getLayoutX();
    double rightActualThresholdX = rightThreshold.getLayoutX();
    double displacedThresholdLayoutX;
    Rectangle stopway;
    Rectangle clearway;
    //Arrow variables
    Line start = arrow.getStart();
    Line length = arrow.getLength();
    Line end = arrow.getEnd();
    Label label = arrow.getLabel();
    Polygon arrowHead = arrow.getArrowHead();

    if (LeftorRight.equals("Left")){
      logicalRunway = physicalRunway.getLogicalRunways().get(0);
      displacedThresholdLayoutX = leftDisplacedThreshold.getLayoutX();
      stopway = rightStopway;
      clearway = rightClearway;
      originalStartX = leftActualThresholdX;
      originalEndX = rightActualThresholdX;
    }else {
      logicalRunway = physicalRunway.getLogicalRunways().get(1);
      displacedThresholdLayoutX = rightDisplacedThreshold.getLayoutX();
      stopway = leftStopway;
      clearway = leftClearway;
      originalStartX = rightActualThresholdX;
      originalEndX = leftActualThresholdX;
    }

    //condition for defining start
    if (type.equals("LDA")){
      originalStartX = displacedThresholdLayoutX;
    }

    //condition for defining end
    if (LeftorRight.equals("Left")){
      if (type.equals("ASDA")){
        originalEndX = stopway.getLayoutX() + stopway.getWidth();
      }else if (type.equals("TODA")){
        originalEndX = clearway.getLayoutX() + clearway.getWidth();
      }
    }else {
      if (type.equals("ASDA")){
        originalEndX = stopway.getLayoutX();
      }else if (type.equals("TODA")){
        originalEndX = clearway.getLayoutX() ;
      }
    }

    double originalValue = oldAndNewValue(type,logicalRunway)[0];

    start.setLayoutX(originalStartX);
    end.setLayoutX(originalEndX);
    label.setText(" " + type + " = " + originalValue + "m " );
    if (LeftorRight.equals("Left")){
      length.setLayoutX(originalStartX);
    }else {
      length.setLayoutX(originalEndX);
    }

    double difference = Math.abs(originalEndX-originalStartX);

    length.setEndX(difference);
    double labelLayout = getLabelLayout(LeftorRight.equals("Left")? start: end,length,label);
    label.setLayoutX(labelLayout);
    arrowHead.setLayoutX(end.getLayoutX());
  }

  public void relocateObstacle(){
    obstacleBlock.setVisible(true);
    Obstacle obstacle = MainPageController.getObstacleSelected();
    LogicalRunway logRunway;
    double runwayStartX = runway.getLayoutX();
    double runwayLength = runway.getWidth();
    double centre = centreLine.getLayoutY();
    double disFromThreshold = obstacle.getDistanceFromThreshold();
    double tora;
    double stripEnd;
    double obsBlockWidth = obstacleBlock.getHeight();

    logRunway = MainPageController.getPhysRunwaySelected().getLogicalRunways().get(0);
    tora = logRunway.getTora();
    double displacedFromCentre = obstacle.getDirFromCentre().equals("L")? (-obstacle.getDistanceFromCentre()*(minCGArea1.getHeight()/2)/PhysicalRunway.minCGArea)
            -obsBlockWidth/2: (obstacle.getDistanceFromCentre()*(minCGArea.getHeight()/2)/PhysicalRunway.minCGArea)-obsBlockWidth/2;
    if(ParameterCalculator.needRedeclare(obstacle, logRunway)){
      if(ParameterCalculator.getFlightMethod(obstacle, logRunway).equals("Take-Off Away Landing Over")){
        obstacleBlock.relocate(runwayStartX+((disFromThreshold+logRunway.getDisplacedThreshold())*(runwayLength-logRunway.getClearway())/tora) -obsBlockWidth,
                centre+displacedFromCentre);
      } else{
        obstacleBlock.relocate(runwayStartX+((disFromThreshold+logRunway.getDisplacedThreshold())*(runwayLength-logRunway.getClearway())/tora),
                centre+(displacedFromCentre));
      }
    }else{
      obstacleBlock.setVisible(false);
    }
  }


  protected void setUpLogicalRunway(PhysicalRunway physicalRunway){
    RunwayParameterSpan ToraArrow = new RunwayParameterSpan(toraStart1,toraLength1,toraEnd1,toraLabel1,toraArrow1);
    RunwayParameterSpan LdaArrow = new RunwayParameterSpan(ldaStart1,ldaLength1,ldaEnd1,ldaLabel1,ldaArrow1);
    RunwayParameterSpan AsdaArrow = new RunwayParameterSpan(asdaStart1,asdaLength1,asdaEnd1,asdaLabel1,asdaArrow1);
    RunwayParameterSpan TodaArrow = new RunwayParameterSpan(todaStart1,todaLength1,todaEnd1,todaLabel1,todaArrow1);
    RunwayParameterSpan ToraArrow1 = new RunwayParameterSpan(toraEnd2,toraLength2,toraStart2,toraLabel2,toraArrow2);
    RunwayParameterSpan LdaArrow1 = new RunwayParameterSpan(ldaEnd2,ldaLength2,ldaStart2,ldaLabel2,ldaArrow2);
    RunwayParameterSpan AsdaArrow1 = new RunwayParameterSpan(asdaEnd2,asdaLength2,asdaStart2,asdaLabel2,asdaArrow2);
    RunwayParameterSpan TodaArrow1 = new RunwayParameterSpan(todaEnd2,todaLength2,todaStart2,todaLabel2,todaArrow2);
    //left logical runway
    setUpLine("TORA","Left",physicalRunway,ToraArrow);
    setUpLine("LDA","Left",physicalRunway,LdaArrow);
    setUpLine("ASDA","Left",physicalRunway,AsdaArrow);
    setUpLine("TODA","Left",physicalRunway,TodaArrow);
    //right logical runway
    setUpLine("TORA","Right",physicalRunway,ToraArrow1);
    setUpLine("LDA","Right",physicalRunway,LdaArrow1);
    setUpLine("ASDA","Right",physicalRunway,AsdaArrow1);
    setUpLine("TODA","Right",physicalRunway,TodaArrow1);
  }

  public void updateView(String runwayName, ArrayList<Float> parameters) throws SQLException {
    LogicalRunway leftLogicalRunway = new LogicalRunway(runwayName.split("/")[0], parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
    LogicalRunway rightLogicalRunway = new LogicalRunway(runwayName.split("/")[1], parameters.get(5), parameters.get(4), parameters.get(6), parameters.get(7));
    ObservableList<LogicalRunway> logicalRunways = FXCollections.observableArrayList();
    logicalRunways.add(leftLogicalRunway);
    logicalRunways.add(rightLogicalRunway);
    PhysicalRunway physicalRunway = new PhysicalRunway(runwayName, logicalRunways);
    leftDesignator.setText(runwayName.split("/")[0]);
    rightDesignator.setText(runwayName.split("/")[1]);
    ldaLabel1.setText("LDA = " + parameters.get(3) + "m");
    todaLabel1.setText("TODA = " + parameters.get(1) + "m");
    asdaLabel1.setText("ASDA = " + parameters.get(2) + "m");
    toraLabel1.setText("TORA = " + parameters.get(0) + "m");
    toraLabel2.setText("TORA = " + parameters.get(5) + "m");
    asdaLabel2.setText("ASDA = " + parameters.get(6) + "m");
    todaLabel2.setText("TODA = " + parameters.get(4) + "m");
    ldaLabel2.setText("LDA = " + parameters.get(7) + "m");
    leftThreshold.setLayoutX(runway.getLayoutX());
    rightThreshold.setLayoutX(runway.getLayoutX()+runway.getWidth());
    setUpLogicalRunway(physicalRunway);

    //Rotates the view back to normal when runway changes
    if (isRotated != false) {
      Rotate rotate = new Rotate();
      rotate.setPivotX(topDownRunwayPane.getWidth() / 2);
      rotate.setPivotY(topDownRunwayPane.getHeight() / 2);
      rotate.setAngle(rotation);
      topDownRunwayPane.getTransforms().add(rotate);
      isRotated = false;
      labelFlipped = false;
      topDownRunwayPane.setScaleX(1);
      topDownRunwayPane.setScaleY(1);
    }
  }

  @FXML
  public void rotateRunway() {
    Rotate rotate = new Rotate();
    try {
      if (Integer.parseInt(leftDesignator.getText().substring(0, 2)) == 9 || Integer.parseInt(leftDesignator.getText().substring(0, 2)) == 27 ) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Heads up");
        alert.setHeaderText("Already Rotated");
        alert.setContentText("This runway faces east and therefore is already rotated correctly.");
        alert.showAndWait();
      }else if(Integer.parseInt(leftDesignator.getText().substring(0, 2)) < 18) {
        rotation = Integer.parseInt(leftDesignator.getText().substring(0, 2)) * 10 - 90;

      }else {
        String temp = leftDesignator.getText();
        leftDesignator.setText(rightDesignator.getText());
        rightDesignator.setText(temp);
        rotation = Integer.parseInt(leftDesignator.getText().substring(0, 2)) * 10 - 90;
        labelFlipped = true;
      }
    } catch (NumberFormatException e){
      // Handle empty runway error
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("No Runway");
      alert.setContentText("Please select a runway.");
      alert.showAndWait();
    }
    rotate.setAngle(rotation);
    rotate.setPivotX(topDownRunwayPane.getWidth() / 2);
    rotate.setPivotY(topDownRunwayPane.getHeight() / 2);
    if (isRotated != true) {
      topDownRunwayPane.getTransforms().add(rotate);
      topDownRunwayPane.setScaleX(1 - (double) Math.abs(rotation) /300 );
      topDownRunwayPane.setScaleY(1 - (double) Math.abs(rotation) /300 );
      rotation = 360 - rotation  ;
      isRotated = true;
    } else {
      rotate.setAngle(360 - rotation);
      topDownRunwayPane.getTransforms().add(rotate);
      isRotated = false;
      topDownRunwayPane.setScaleX(1);
      topDownRunwayPane.setScaleY(1);
      if (labelFlipped == true){
        String temp = leftDesignator.getText();
        leftDesignator.setText(rightDesignator.getText());
        rightDesignator.setText(temp);
        labelFlipped = false;
      }
    }

  }
}
