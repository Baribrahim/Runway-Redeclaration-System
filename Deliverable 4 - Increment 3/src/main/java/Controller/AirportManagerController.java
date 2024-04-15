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
import javafx.fxml.Initializable;
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
}
