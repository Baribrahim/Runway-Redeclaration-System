package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseModel {

  private Connection connection = null;

  public DatabaseModel() {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite::resource:RunwayRedeclaration.sqlite");
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

  public String getAirport(String airportID) throws SQLException {
    String query = "SELECT airportID FROM Airport WHERE airportID = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, airportID);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getString("airportID");
      }
    }
    return null; // Return null if no airport with the specified ID is found
  }

  public ArrayList<String> getPhysicalRunways(String airportName) throws SQLException {
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

  public float getObstacleHeight(String obstacleName) throws SQLException {
    String query = "SELECT Height FROM Obstacle WHERE ObstacleID = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, obstacleName);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getFloat("Height");
        } else {
          return -1; // Indicator that the obstacle was not found
        }
      }
    }
  }

  public float getObstacleWidth(String obstacleName) throws SQLException {
    String query = "SELECT Width FROM Obstacle WHERE ObstacleID = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, obstacleName);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getFloat("Width");
        } else {
          return -1; // Indicator that the obstacle was not found
        }
      }
    }
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

  public boolean CorrectInfo (String userID, String password) throws SQLException {
    ResultSet resultSet = query("SELECT * FROM LoginInfo WHERE UserID = '" + userID + "' AND Password = '" +password+ "'" );
    ArrayList<String> permissions = new ArrayList<>();
    while (resultSet.next()) {
      permissions.add(resultSet.getString("Permission"));
    }
    if(permissions.size() != 0 ) {
      return true;
    }else {return false;}
  }

  public Connection getConnection() {
    return connection;
  }

  public void deleteAirport(String airportId) throws SQLException {
    String sqlDeleteRunways = "DELETE FROM Runway WHERE Airport = ?";
    String sqlDeleteAirport = "DELETE FROM Airport WHERE airportID = ?";

    try {
      connection.setAutoCommit(false);

      // Delete associated runways
      try (PreparedStatement pstmtRunways = connection.prepareStatement(sqlDeleteRunways)) {
        pstmtRunways.setString(1, airportId);
        pstmtRunways.executeUpdate();
      }

      // Delete the airport
      try (PreparedStatement pstmtAirport = connection.prepareStatement(sqlDeleteAirport)) {
        pstmtAirport.setString(1, airportId);
        pstmtAirport.executeUpdate();
      }

      connection.commit();  // Commit both transactions
    } catch (SQLException e) {
      if (connection != null) {
        connection.rollback();  // Roll back if there was an error
      }
      throw e;
    }
  }

  public ArrayList<Runway> getAllRunways() throws SQLException {
    ArrayList<Runway> runways = new ArrayList<>();
    String query = "SELECT r.RunwayID, a.airportID, r.leftTORA, r.leftTODA, r.leftASDA, r.leftLDA, r.rightTODA, r.rightTORA, r.rightASDA, r.rightLDA " +
        "FROM Runway r JOIN Airport a ON r.Airport = a.airportID";
    try (PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        runways.add(new Runway(
            resultSet.getString("RunwayID"),
            resultSet.getString("airportID"),
            resultSet.getDouble("leftTORA"),
            resultSet.getDouble("leftTODA"),
            resultSet.getDouble("leftASDA"),
            resultSet.getDouble("leftLDA"),
            resultSet.getDouble("rightTODA"),
            resultSet.getDouble("rightTORA"),
            resultSet.getDouble("rightASDA"),
            resultSet.getDouble("rightLDA")
        ));
      }
    }
    return runways;
  }

  public void deleteRunway(String runwayID) throws SQLException {
    String sql = "DELETE FROM Runway WHERE RunwayID = ?";
    try (
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, runwayID);
      pstmt.executeUpdate();
    }
  }

  public void updateRunway(Runway runway) throws SQLException {
    String sql = "UPDATE Runway SET "
        + "RunwayID = ?, "
        + "Airport = ?, "
        + "leftTORA = ?, leftTODA = ?, leftASDA = ?, leftLDA = ?, "
        + "rightTODA = ?, rightTORA = ?, rightASDA = ?, rightLDA = ? "
        + "WHERE RunwayID = ?";

    try (
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, runway.getRunwayID());
      pstmt.setString(2, runway.getAirportName());
      pstmt.setString(3, String.valueOf(runway.getLeftTORA()));
      pstmt.setString(4, String.valueOf(runway.getLeftTODA()));
      pstmt.setString(5, String.valueOf(runway.getLeftASDA()));
      pstmt.setString(6, String.valueOf(runway.getLeftLDA()));
      pstmt.setString(7, String.valueOf(runway.getRightTODA()));
      pstmt.setString(8, String.valueOf(runway.getRightTORA()));
      pstmt.setString(9, String.valueOf(runway.getRightASDA()));
      pstmt.setString(10, String.valueOf(runway.getRightLDA()));
      pstmt.setString(11, runway.getRunwayID());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Updating runway failed, no rows affected.");
      }
    }
  }

  public ObservableList<User> getLoginInfo() throws SQLException {
    ObservableList<User> loginInfoList = FXCollections.observableArrayList();
    String sql = "SELECT UserID, Permission FROM LoginInfo";

    try (
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        String userID = rs.getString("UserID");
        String permission = rs.getString("Permission");
        User info = new User(userID, permission);
        loginInfoList.add(info);
      }
    } catch (SQLException e) {
      throw new SQLException("Error fetching users' data", e);
    }
    return loginInfoList;
  }

  public void addUser(String userID, String password, String permission) throws SQLException {
    String sql = "INSERT INTO LoginInfo (UserID, Password, Permission) VALUES (?, ?, ?)";

    try (
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, userID);
      pstmt.setString(2, password);
      pstmt.setString(3, permission);

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Inserting user failed, no rows affected.");
      }
    } catch (SQLException e) {
      // Optionally, log this exception or handle it further
      throw new SQLException("Error adding new user to the database", e);
    }
  }

  public void deleteUser(String userID) throws SQLException {
    String sql = "DELETE FROM LoginInfo WHERE UserID = ?";

    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, userID);
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Deleting user failed, no rows affected.");
      }
    } catch (SQLException e) {
      throw new SQLException("Error deleting user from the database", e);
    }
  }

  public void updateUser(User user) throws SQLException {
    String sql = "UPDATE LoginInfo SET Password = ?, Permission = ? WHERE UserID = ?";

    try (
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, user.getPassword());
      pstmt.setString(2, user.getPermission());
      pstmt.setString(3, user.getUserID());
      pstmt.executeUpdate();
    }
  }

}
