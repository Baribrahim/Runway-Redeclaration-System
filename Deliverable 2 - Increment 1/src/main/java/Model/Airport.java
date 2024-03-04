package Model;

import javafx.collections.ObservableList;

public class Airport {
  private String airportName;
  private String manager;
  private String airportID;
  private ObservableList<PhysicalRunway> physicalRunways;

  //Constructor
  public Airport(){};
  public Airport(String airportID, String aiportName, ObservableList<PhysicalRunway> physicalRunways, String manager) {
    this.airportID = airportID;
    this.airportName = aiportName;
    this.physicalRunways = physicalRunways;
    this.manager = manager;
  }


  @Override
  public String toString() {
    return "Airport{" +
        "name='" + airportName + '\'' +
        "\n physicalRunways=" + physicalRunways +
        '}';
  }

  //Getters and Setters for the properties of Airport
  public String getAirportID() {
    return airportID;
  }

  public String getAirportName() {
    return airportName;
  }

  public void setAirportName(String airportName) {
    this.airportName = airportName;
  }

  public ObservableList<PhysicalRunway> getPhysicalRunways() {
    return physicalRunways;
  }

  public String getManager() {
    return manager;
  }

  public void setManager(String name){
    this.manager = name;
  }

}
