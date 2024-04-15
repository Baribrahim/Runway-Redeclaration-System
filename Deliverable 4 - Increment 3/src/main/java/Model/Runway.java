package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Runway {
  private String runwayID;
  private String airportName;
  private double leftTORA;
  private double leftTODA;
  private double leftASDA;
  private double leftLDA;
  private double rightTORA;
  private double rightTODA;
  private double rightASDA;
  private double rightLDA;

  public Runway(String runwayID, String airportName, double leftTORA, double leftTODA, double leftASDA, double leftLDA,
      double rightTORA, double rightTODA, double rightASDA, double rightLDA) {
    this.runwayID = runwayID;
    this.airportName = airportName;
    this.leftTORA = leftTORA;
    this.leftTODA = leftTODA;
    this.leftASDA = leftASDA;
    this.leftLDA = leftLDA;
    this.rightTORA = rightTORA;
    this.rightTODA = rightTODA;
    this.rightASDA = rightASDA;
    this.rightLDA = rightLDA;
  }

  // RunwayID getters and setters
  public String getRunwayID() {
    return runwayID;
  }

  public void setRunwayID(String runwayID) {
    this.runwayID = runwayID;
  }

  // AirportName getters and setters
  public String getAirportName() {
    return airportName;
  }

  public void setAirportName(String airportName) {
    this.airportName = airportName;
  }

  // LeftTORA getters and setters
  public double getLeftTORA() {
    return leftTORA;
  }

  public void setLeftTORA(double leftTORA) {
    this.leftTORA = leftTORA;
  }

  // LeftTODA getters and setters
  public double getLeftTODA() {
    return leftTODA;
  }

  public void setLeftTODA(double leftTODA) {
    this.leftTODA = leftTODA;
  }

  // LeftASDA getters and setters
  public double getLeftASDA() {
    return leftASDA;
  }

  public void setLeftASDA(double leftASDA) {
    this.leftASDA = leftASDA;
  }

  // LeftLDA getters and setters
  public double getLeftLDA() {
    return leftLDA;
  }

  public void setLeftLDA(double leftLDA) {
    this.leftLDA = leftLDA;
  }

  // RightTORA getters and setters
  public double getRightTORA() {
    return rightTORA;
  }

  public void setRightTORA(double rightTORA) {
    this.rightTORA = rightTORA;
  }

  // RightTODA getters and setters
  public double getRightTODA() {
    return rightTODA;
  }

  public void setRightTODA(double rightTODA) {
    this.rightTODA = rightTODA;
  }

  // RightASDA getters and setters
  public double getRightASDA() {
    return rightASDA;
  }

  public void setRightASDA(double rightASDA) {
    this.rightASDA = rightASDA;
  }

  // RightLDA getters and setters
  public double getRightLDA() {
    return rightLDA;
  }

  public void setRightLDA(double rightLDA) {
    this.rightLDA = rightLDA;
  }
}
