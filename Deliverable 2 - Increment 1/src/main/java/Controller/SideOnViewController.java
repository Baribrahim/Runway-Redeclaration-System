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

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }
}
