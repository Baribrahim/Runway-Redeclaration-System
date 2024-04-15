package Controller;

import Model.Airport;
import Model.DatabaseModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AirportManagerController implements Initializable {

  private DatabaseModel database;

  @FXML
  private TableView<Airport> airportsTable;
  @FXML
  private TableColumn<Airport, String> airports;

  @FXML
  private Button deleteAirportButton;

  @FXML
  private Button manageRunwaysButton;

  @FXML
  private Button goBackButton;



  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    airports.setCellValueFactory(new PropertyValueFactory<>("name"));
  }

  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }

  @FXML
  private void loadAirportNames() throws SQLException {
    ArrayList<String> names = database.getAirports(); // This method should return ArrayList<String> from your database
    ObservableList<Airport> airportNames = FXCollections.observableArrayList();
    for (String name : names) {
      airportNames.add(new Airport(name));
    }
    airportsTable.setItems(airportNames);
  }

  @FXML
  private void handleBackButtonClick() throws IOException {
    // Close the current stage (NewAirportController)
    Stage stage = (Stage) goBackButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void handleDeleteButton() {
    Airport selectedAirport = airportsTable.getSelectionModel().getSelectedItem();
    if (selectedAirport == null) {
      showAlert("No Selection", "Please select an Airport to delete.");
      return;
    }

    try {
      database.deleteAirport(selectedAirport.getName());  // Assuming Airport has an getId() method
      airportsTable.getItems().remove(selectedAirport);  // Remove from TableView
      showAlert("Deleted", "Airport and associated runways have been deleted.");
    } catch (SQLException e) {
      showAlert("Error", "Error occurred while deleting the airport: " + e.getMessage());
    }
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  private void handleManageRunwaysButton() {
    try {
      // Load the obstacle definition FXML file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/manageRunways.fxml"));
      Parent root = loader.load();

      ManageRunwaysController manageRunwaysController = loader.getController();
      manageRunwaysController.setDatabaseModel(database);

      // Create a new stage (window) for the obstacle definition
      // Create a new stage
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
      stage.setScene(scene);
      stage.setTitle("Manage Runways");
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
