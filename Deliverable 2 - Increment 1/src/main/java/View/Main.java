package View;

import Controller.XMLController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  public static final XMLController XML_CONTROLLER = new XMLController();

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/StartPage.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);
    // Add the CSS stylesheet
    scene.getStylesheets().add(getClass().getResource("/CSS/MainPageStylesheet.css").toExternalForm());
    primaryStage.setTitle("Start");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}