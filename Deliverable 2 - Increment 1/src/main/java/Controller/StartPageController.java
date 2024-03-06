package Controller;

import Model.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    private Label fail;
    @FXML
    private TextField userIDField;
    @FXML
    private PasswordField passwordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(loginModel.Connected()){
            fail.setText("Connected");
        }
        else{
            fail.setText("Not Connected");
        }
    }
    public void Login (ActionEvent event) {
        try {
            if (loginModel.CorrectInfo(userIDField.getText(), passwordField.getText())) {
                fail.setText("Login Successful");
            } else {
                fail.setText("UserId or Password Incorrect");
            }
        } catch (SQLException e) {
            fail.setText("UserId or Password Incorrect");
            e.printStackTrace();
        }
    }

    public void Login(javafx.event.ActionEvent actionEvent) {
        try {
            if (loginModel.CorrectInfo(userIDField.getText(), passwordField.getText())) {
                fail.setText("Login Successful");
            } else {
                fail.setText("UserId or Password Incorrect");
            }
        } catch (SQLException e) {
            fail.setText("UserId or Password Incorrect");
            e.printStackTrace();
        }
    }
}
