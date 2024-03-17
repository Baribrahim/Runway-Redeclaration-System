package Model;

import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

//This class is model for the lines and arrows that represents the span of each parameter
//in the visualisations
public class RunwayParameterSpan {
  private Line start;
  private Line length;
  private Line end;
  private Label label;
  private Polygon arrowHead;

  //Constructor
  public RunwayParameterSpan(Line start, Line length, Line end,Label label,Polygon polygon){
    this.start = start;
    this.length = length;
    this.end = end;
    this.label = label;
    this.arrowHead = polygon;
  }

  //Getters and Setters
  public Label getLabel() {
    return label;
  }

  public void setLabel(Label label) {
    this.label = label;
  }

  public Line getStart() {
    return start;
  }

  public void setStart(Line start) {
    this.start = start;
  }

  public Line getLength() {
    return length;
  }

  public void setLength(Line length) {
    this.length = length;
  }

  public Line getEnd() {
    return end;
  }

  public Polygon getArrowHead() {
    return arrowHead;
  }


}
