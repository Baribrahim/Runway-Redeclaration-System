package Controller;

import Model.DatabaseModel;
import Model.Runway;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Controller.Helper.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ManageRunwaysController implements Initializable {

  @FXML
  private Button goBackButton;

  @FXML
  private Button deleteRunwayButton;

  @FXML
  private Button UpdateRunwaysButton;

  @FXML
  private TableView<Runway> runwaysTable;
  @FXML
  private TableColumn<Runway, String> colRunwayID, colAirportName, colLeftTORA, colLeftTODA, colLeftASDA, colLeftLDA,
      colRightTORA, colRightTODA, colRightASDA, colRightLDA;

  private DatabaseModel database;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    colRunwayID.setCellValueFactory(new PropertyValueFactory<>("runwayID"));
    colAirportName.setCellValueFactory(new PropertyValueFactory<>("airportName"));
    colLeftTORA.setCellValueFactory(new PropertyValueFactory<>("leftTORA"));
    colLeftTODA.setCellValueFactory(new PropertyValueFactory<>("leftTODA"));
    colLeftASDA.setCellValueFactory(new PropertyValueFactory<>("leftASDA"));
    colLeftLDA.setCellValueFactory(new PropertyValueFactory<>("leftLDA"));
    colRightTORA.setCellValueFactory(new PropertyValueFactory<>("rightTORA"));
    colRightTODA.setCellValueFactory(new PropertyValueFactory<>("rightTODA"));
    colRightASDA.setCellValueFactory(new PropertyValueFactory<>("rightASDA"));
    colRightLDA.setCellValueFactory(new PropertyValueFactory<>("rightLDA"));
  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }

  @FXML
  private void loadRunways() {
    try {
      ObservableList<Runway> runways = FXCollections.observableArrayList(database.getAllRunways());
      runwaysTable.setItems(runways);
    } catch (SQLException e) {
      e.printStackTrace();  // Handle exceptions appropriately, perhaps showing an error message to the user
    }
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) goBackButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void handleDeleteRunwayButton() {
    Runway selectedRunway = runwaysTable.getSelectionModel().getSelectedItem();
    if (selectedRunway == null) {
      new Notification(AlertType.INFORMATION, "No selection", "Please select a runway to delete.");
      return;
    }

    try {
      database.deleteRunway(selectedRunway.getRunwayID());
      runwaysTable.getItems().remove(selectedRunway);
      new Notification(AlertType.CONFIRMATION, "Success", "Runway deleted successfully");
    } catch (SQLException e) {
      new Notification(AlertType.ERROR, "Error", "Failed to delete runway: " + e.getMessage());
      System.out.println("Failed to delete runway with ID: " + selectedRunway.getRunwayID());
      e.printStackTrace();
    }
  }

  @FXML
  private void handleOpenUpdateWindow() throws Exception {
    Runway selectedRunway = runwaysTable.getSelectionModel().getSelectedItem();
    if (selectedRunway == null) {
      new Notification(AlertType.INFORMATION, "No selection", "Please select a runway to update.");
      return;
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRunway.fxml"));
    Parent root = loader.load();
    UpdateRunwayController updateController = loader.getController();
    updateController.initRunwayData(selectedRunway, database);  // Assuming databaseModel is available here

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.showAndWait();

    // Optionally refresh the table view if updates are committed
    runwaysTable.refresh();
  }
}
