package Controller;

import Model.DatabaseModel;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateUserController implements Initializable {

  private DatabaseModel database;

  @FXML
  private TextField userid;
  @FXML
  private TextField userPass;
  @FXML
  private TextField userRole;

  @FXML
  private Button goBackButton;

  @FXML
  private Button submitButton;

  private User currentUser;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  // Method to initialize the form with user data
  public void initData(User user) {
    currentUser = user;
    userid.setText(user.getUserID());
    userPass.setText(user.getPassword()); // Assuming password is retrievable, consider security implications
    userRole.setText(user.getPermission());
  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }

  @FXML
  private void handleSubmitButton() {
    String userID = userid.getText();
    String password = userPass.getText();
    String permission = userRole.getText();

    try {
      database.updateUser(new User(userID, password, permission));
      Stage stage = (Stage) userid.getScene().getWindow();
      stage.close(); // Close the update form window after update
    } catch (SQLException e) {
      e.printStackTrace(); // Proper error handling should be implemented
    }
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) goBackButton.getScene().getWindow();
    stage.close();
  }
}
