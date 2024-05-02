package Controller.Helper;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;


public class Notification {
    public Notification(Alert.AlertType alertType, String title, String message) {
      showAlert(alertType, title, message);
    }
    
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        // Create a fade transition for the alert
        FadeTransition fadeInTransition = new FadeTransition(javafx.util.Duration.millis(500), alert.getDialogPane());
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
    
        FadeTransition fadeOutTransition = new FadeTransition(javafx.util.Duration.millis(500), alert.getDialogPane());
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
    
        // Show the alert and start the fade in transition
        alert.show();
        fadeInTransition.play();
    
        javafx.util.Duration duration;
        if (alertType == Alert.AlertType.ERROR) {
          duration = javafx.util.Duration.millis(2000);
        } else {
          duration = javafx.util.Duration.millis(1000);
        }
    
        // Set the alert to automatically close after x seconds
        PauseTransition delay = new PauseTransition(duration);
        delay.setOnFinished(event -> {
          fadeOutTransition.play();
          fadeOutTransition.setOnFinished(closeEvent -> alert.close());
        });
        delay.play();
      }
}
