package Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class SimultaneousViewController implements Initializable {

  public AnchorPane topDownRunwayPane;
  public Rectangle minCGArea1;
  public Rectangle rightClearway1;
  public Rectangle leftClearway1;
  public Rectangle rightStopway1;
  public Rectangle leftStopway1;
  public Rectangle runway1;
  public Line centreLine;
  public Label TDleftDesignator;
  public Label TDrightDesignator;
  public Line leftDisplacedThreshold1;
  public Line rightDisplacedThreshold1;

  public Line gradedAreaLine;
  public Label asdaLabel1;
  public Label todaLabel1;
  public Label toraLabel1;
  public Label ldaLabel1;
  public Line toraExtraLineLength1;
  public Line toraExtraLineEnd;
  public Label toraExtraLineLabel1;
  public Line toraEnd1;
  public Line ldaStart1;
  public Line asdaStart1;
  public Line ldaEnd1;
  public Line asdaEnd1;
  public Line toraStart1;
  public Line todaStart1;
  public Line todaEnd1;
  public Line toraLength1;
  public Line ldaLength1;
  public Line asdaLength1;
  public Line todaLength1;
  public Line ldaExtraLineLength1;
  public Line ldaExtraLineEnd;
  public Label ldaExtraLineLabel1;
  public Line leftThreshold1;
  public Line rightThreshold1;
  public Rectangle obstacleBlock;
  public Polygon toraArrow1;
  public Polygon ldaArrow1;
  public Polygon asdaArrow1;
  public Polygon todaArrow1;
  public Polygon toraExtraArrow1;
  public Polygon ldaExtraArrow1;
  public AnchorPane sideOnPane1;
  public Rectangle runway;
  public Label SOrightDesignator;
  public Label SOleftDesignator;
  public Rectangle rightClearway;
  public Rectangle leftClearway;
  public Line rightDisplacedThreshold;
  public Line leftDisplacedThreshold;
  public Rectangle rightStopway;
  public Rectangle leftStopway;
  public Line leftThreshold;
  public Line toraEnd;
  public Line rightThreshold;
  public Line ldaStart;
  public Line asdaStart;
  public Line ldaEnd;
  public Line asdaEnd;
  public Line toraStart;
  public Line todaStart;
  public Line todaEnd;
  public Line toraLength;
  public Line ldaLength;
  public Line asdaLength;
  public Line todaLength;
  public Label ldaLabel;
  public Label toraLabel;
  public Label asdaLabel;
  public Label todaLabel;
  public Polygon tocsSlope;
  public Polygon alsSlope;
  public Polygon todaArrow;
  public Polygon asdaArrow;
  public Polygon ldaArrow;
  public Polygon toraArrow;
  public Label ldaExtraLabel;
  public Line ldaExtraLength;
  public Line ldaExtraStart;
  public Line toraExtraLength;
  public Line toraExtraStart;
  public Label toraExtraLabel;
  public Polygon ldaExtraArrow;
  public Polygon toraExtraArrow;
  public Rectangle scaleLength;
  public Rectangle scaleStart;
  public Rectangle scaleEnd;
  public Label scaleLabel;
  public Label scale0;
  public Label scale500;
  public Label scale1000;
  public Label scale1500;
  public Label scaleUnit;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void updateView(String runwayName, ArrayList<Float> parameters) throws SQLException {
    TDleftDesignator.setText(runwayName.split("/")[0]);
    TDrightDesignator.setText(runwayName.split("/")[1]);
    SOleftDesignator.setText(runwayName.split("/")[0]);
    SOrightDesignator.setText(runwayName.split("/")[1]);
    ldaLabel.setText("LDA = " + parameters.get(3) + "m");
    todaLabel.setText("TODA = " + parameters.get(1) + "m");
    asdaLabel.setText("ASDA = " + parameters.get(2) + "m");
    toraLabel.setText("TORA = " + parameters.get(0) + "m");
    toraLabel1.setText("TORA = " + parameters.get(5) + "m");
    asdaLabel1.setText("ASDA = " + parameters.get(6) + "m");
    todaLabel1.setText("TODA = " + parameters.get(4) + "m");
    ldaLabel1.setText("LDA = " + parameters.get(7) + "m");
  }
}
