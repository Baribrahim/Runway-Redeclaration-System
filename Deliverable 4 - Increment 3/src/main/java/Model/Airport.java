package Model;

import java.util.ArrayList;

public class Airport {

  private ArrayList<PhysicalRunway> physicalRunways;
  private String name;
  private String manager;
  private String airportID;
  private String city;
  private Double latitude;
  private Double longitude;

  //Constructor
  public Airport(String name) {
    this.name = name;
  }

  public Airport(){};

  public Airport(String airportID, String aiportName, String manager, String city, Double latitude, Double longitude) {
    this.airportID = airportID;
    this.name = aiportName;
    this.physicalRunways = new ArrayList<>();
    this.manager = manager;
    this.city = city;
    this.latitude = latitude;
    this.longitude = longitude;
  }


  @Override
  public String toString() {
    return "Airport{" +
        "name='" + name + '\'' +
        "\n physicalRunways=" + physicalRunways +
        '}';
  }

  //Getters and Setters for the properties of Airport
  public String getAirportID() {
    return airportID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<PhysicalRunway> getPhysicalRunways() {
    return physicalRunways;
  }

  public String getManager() {
    return manager;
  }

  public void setManager(String name){
    this.manager = name;
  }

  public void addRunway(PhysicalRunway runway) {
    physicalRunways.add(runway);
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

}
