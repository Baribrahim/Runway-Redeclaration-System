package Model;
import java.sql.*;
import java.util.ArrayList;

public class LoginModel {
    Connection connection;
    public LoginModel() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:../RunwayRedeclaration.sqlite");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean Connected(){
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
    public boolean CorrectInfo(String userID, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String login = "SELECT * FROM LoginInfo WHERE UserID = ? AND Password = ?" ;
        try {
            preparedStatement = connection.prepareStatement(login);
            preparedStatement.setString(1,userID);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            else { return  false;}
        } catch (Exception e){
            return false;
        }


    } */
    public ResultSet query(String command) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(command);
        return statement.executeQuery();
    }
    public boolean CorrectInfo (String userID, String password) throws SQLException {
        ResultSet resultSet = query("SELECT * FROM LoginInfo WHERE UserID = " + userID + " AND Password = " +password+ "" );
        ArrayList<String> runways = new ArrayList<>();
        while (resultSet.next()) {
            runways.add(resultSet.getString("Permission"));
        }
        if(runways != null ) {
            return true;
        }else {return false;}
    }
}

