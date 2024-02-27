package org.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller extends Application {

  public void start() {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader startWindow = new FXMLLoader(getClass().getResource("/StartWindow.fxml"));
    Parent root = startWindow.load();
    stage.setTitle("Burndown Chart");
    Scene startScene = new Scene(root, 1000, 1000);
    stage.setScene(startScene);
    stage.show();
  }
}
