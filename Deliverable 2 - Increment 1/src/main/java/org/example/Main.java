package org.example;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);
    primaryStage.setTitle("Main");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}