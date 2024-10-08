package View;

import Controller.AirportManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AirportManager extends Application {
  private static Scene classScene;
  private static Stage classStage;
  private static String username;

  //default constructor
  public AirportManager(){}

  public AirportManager(String username) {
    AirportManager.username = username;
  }

  public static String getUsername() {
    return username;
  }

  public static Stage getStage(){
    return classStage;
  }

  public static Scene getClassScene(){
    return classScene;
  }

  @Override
  public void start(Stage stage) throws Exception {
    classStage = stage;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AirportManager.fxml"));
    Parent root = loader.load();
    loader.setController(new AirportManagerController());

    classScene = new Scene(root);
    stage.setTitle("SEG Runway Project");
    stage.setScene(classScene);
    stage.setResizable(false);
    stage.show();
  }
}
