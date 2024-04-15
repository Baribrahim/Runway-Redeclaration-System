package Controller;

import Model.Airport;
import Model.DatabaseModel;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManagerUsersController implements Initializable {

  @FXML
  private Button goBackButton;

  @FXML
  private TableView<User> usersTable;
  @FXML
  private TableColumn<User, String> user, role;
  private DatabaseModel database;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    user.setCellValueFactory(new PropertyValueFactory<>("userID"));
    role.setCellValueFactory(new PropertyValueFactory<>("permission"));
  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }


  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) goBackButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void loadUsers() throws SQLException {
    usersTable.setItems(database.getLoginInfo());
  }

  @FXML
  private void handleAddUserButton() {
    try {
      // Load the obstacle definition FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUser.fxml"));
      Parent root = loader.load();

      AddUserController addUserController = loader.getController();
      addUserController.setDatabaseModel(database);

      // Create a new stage (window) for the obstacle definition
      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("Add User");
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @FXML
  private void handleDeleteUserButton() {
    User selectedUser = usersTable.getSelectionModel().getSelectedItem();
    if (selectedUser == null) {
      showAlert("No selection", "Please select a user to delete.");
      return;
    }

    try {
      database.deleteUser(selectedUser.getUserID());
      usersTable.getItems().remove(selectedUser); // Remove from ObservableList, updating the TableView
      showAlert("Success", "The user was successfully deleted.");
    } catch (SQLException e) {
      showAlert("Error", "Failed to delete the user: " + e.getMessage());
    }
  }

  @FXML
  private void handleUpdateUserButton() throws SQLException {
    User selectedUser = usersTable.getSelectionModel().getSelectedItem();
    if (selectedUser == null) {
      showAlert("No selection", "Please select a user to update.");
      return;
    }
    try {
      // Load the obstacle definition FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUser.fxml"));
      Parent root = loader.load();

      UpdateUserController UpdateUserController = loader.getController();
      UpdateUserController.setDatabaseModel(database);
      UpdateUserController.initData(selectedUser);

      // Create a new stage (window) for the obstacle definition
      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("Update User");
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
    usersTable.setItems(database.getLoginInfo());
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
