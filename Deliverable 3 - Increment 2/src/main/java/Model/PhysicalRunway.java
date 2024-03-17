package Model;

import javafx.collections.ObservableList;

public class PhysicalRunway {
  public static double blastProtection = 300;
  public static double resa = 240;
  public static double stripEnd = 60;
  public static final double minCGArea = 75;
  public static final double maxCGArea = 150;
  private String physicalRunwayName;
  private ObservableList<LogicalRunway> logicalRunways;

  @Override
  public String toString() {
    return "PhysicalRunway{" +
        "name='" + physicalRunwayName + '\'' +
        "\nlogicalRunways=" + logicalRunways + '}';
  }

  //Constructors
  public PhysicalRunway(){};

  public PhysicalRunway(String physicalRunwayName, ObservableList<LogicalRunway> logicalRunways){
    this.physicalRunwayName = physicalRunwayName;
    this.logicalRunways = logicalRunways;
  }

  //Getters and Setters for the properties of Physical Runway
  public String getPhysicalRunwayName() {
    return physicalRunwayName;
  }

  public void setPhysicalRunwayName(String physicalRunwayName) {
    this.physicalRunwayName = physicalRunwayName;
  }

  public ObservableList<LogicalRunway> getLogicalRunways() {
    return logicalRunways;
  }

  public static double getBlastProtection() {
    return blastProtection;
  }

  public static void setBlastProtection(double blastProtection) {
    PhysicalRunway.blastProtection = blastProtection;
  }

  public static double getResa() {
    return resa;
  }

  public static void setResa(double resa) {
    PhysicalRunway.resa = resa;
  }

  public static double getStripEnd() {
    return stripEnd;
  }

  public static void setStripEnd(double stripEnd) {
    PhysicalRunway.stripEnd = stripEnd;
  }

}
