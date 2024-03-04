package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseModel {

  private Connection connection = null;

  public DatabaseModel() {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:../RunwayRedeclaration.sqlite");
      connection.setAutoCommit(false);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public ResultSet query(String command) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(command);
    return statement.executeQuery();
  }

  public ArrayList<String> getAirports() throws SQLException {
    ResultSet resultSet = query("SELECT airportID FROM Airport");
    ArrayList<String> airports = new ArrayList<>();
    while (resultSet.next()) {
      airports.add(resultSet.getString("airportID"));
    }
    return airports;
  }

  public ArrayList<String> getPhysicalRunways(String airportName) throws SQLException {
    //PreparedStatement statement = connection.prepareStatement("SELECT RunwayID FROM Runway WHERE Airport (?)");
    ResultSet resultSet = query("SELECT RunwayID FROM Runway WHERE Airport = '" + airportName + "'");
    ArrayList<String> runways = new ArrayList<>();
    while (resultSet.next()) {
      runways.add(resultSet.getString("RunwayID"));
    }
    return runways;
  }

  public ArrayList<String> getObstacles() throws SQLException {
    ResultSet resultSet = query("SELECT ObstacleID FROM Obstacle");
    ArrayList<String> obstacles = new ArrayList<>();
    while (resultSet.next()) {
      obstacles.add(resultSet.getString("ObstacleID"));
    }
    return obstacles;
  }

  public ArrayList<Float> getLogicalRunwayParameters(String runway) throws SQLException {
    ResultSet resultSet = query("SELECT leftTORA, leftTODA, leftASDA, leftLDA, rightTODA, rightTORA, rightASDA, rightLDA FROM Runway WHERE RunwayID = '" + runway + "'");
    ArrayList<Float> parameters = new ArrayList<>();
    while (resultSet.next()) {
      parameters.add(resultSet.getFloat("leftTORA"));
      parameters.add(resultSet.getFloat("leftTODA"));
      parameters.add(resultSet.getFloat("leftASDA"));
      parameters.add(resultSet.getFloat("leftLDA"));
      parameters.add(resultSet.getFloat("rightTODA"));
      parameters.add(resultSet.getFloat("rightTORA"));
      parameters.add(resultSet.getFloat("rightASDA"));
      parameters.add(resultSet.getFloat("rightLDA"));
    }
    return parameters;
  }


  public Boolean insertAirport(String airportName) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("INSERT INTO Airport (airportID) VALUES (?)");
    statement.setString(1, airportName);
    statement.executeUpdate();
    statement.close();
    connection.commit();
    return true;
  }

  public Boolean insertRunway(String runwayName, String airportName, Float leftTORA, Float leftTODA, Float leftASDA, Float leftLDA, Float rightTODA, Float rightTORA, Float rightASDA, Float rightLDA) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("INSERT INTO Runway (RunwayID, Airport, leftTORA, leftTODA, leftASDA, leftLDA, rightTODA, rightTORA, rightASDA, rightLDA) VALUES (?,?,?,?,?,?,?,?,?,?)");
    statement.setString(1, runwayName);
    statement.setString(2, airportName);
    statement.setString(3, String.valueOf(leftTORA));
    statement.setString(4, String.valueOf(leftTODA));
    statement.setString(5, String.valueOf(leftASDA));
    statement.setString(6, String.valueOf(leftLDA));
    statement.setString(7, String.valueOf(rightTODA));
    statement.setString(8, String.valueOf(rightTORA));
    statement.setString(9, String.valueOf(rightASDA));
    statement.setString(10, String.valueOf(rightLDA));
    statement.executeUpdate();
    statement.close();
    connection.commit();
    return true;
  }

  public Boolean insertObstacle(String obstacleName, float height, float width) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("INSERT INTO Obstacle (ObstacleID, Height, Width) VALUES (?,?,?)");
    statement.setString(1, obstacleName);
    statement.setString(2, String.valueOf(height));
    statement.setString(3, String.valueOf(width));
    statement.executeUpdate();
    statement.close();
    connection.commit();
    return true;
  }

  public void closeConnection() throws SQLException {
    connection.close();
  }

}
