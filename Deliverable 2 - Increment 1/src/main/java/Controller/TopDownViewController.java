package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class TopDownViewController implements Initializable {
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

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }
}
