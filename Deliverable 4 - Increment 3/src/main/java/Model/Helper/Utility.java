package Model.Helper;

import Model.Airport;
import Model.LogicalRunway;
import Model.PhysicalRunway;
import View.AirportManager;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Utility {

    public static int getScaleRange(double input){
        if(input <= 500){
            return 90;
        } else if(input <= 1000){
            return 240;
        } else if(input <= 1500){
            return 390;
        } else if (input <= 2000){
            return 600;
        } else if (input <= 2500){
            return 750;
        } else if (input <= 3000){
            return 960;
        } else if (input <= 3500){
            return 1200;
        } else{
            return 1500;
        }
    }

    // The portion of the code for generating report begins here
    public static void exportAirport(Stage stage, ObservableList<Airport> airports) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Airport Details");

        // Set initial directory for the file chooser
        File userDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(userDirectory);

        // Show the file chooser and get the selected file directory
        File selectedDirectory = fileChooser.showSaveDialog(AirportManager.getStage());

        if (selectedDirectory != null) {

            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedDirectory));
            writer.write(Utility.airportInfo(airports));
            writer.close();
        }
    }

    public static String airportInfo(ObservableList<Airport> airports){
        StringBuilder message = new StringBuilder();
        for(Airport airport: airports){
            message.append("Airport name: ").append(airport.getName()).append(" (").append(airport.getAirportID()).append(")");
            message.append("\nAirport manager: ").append(airport.getManager());
            message.append("\n\nPhysical runways:");
            ArrayList<PhysicalRunway> runways = airport.getPhysicalRunways();
            for(PhysicalRunway runway: runways){
                message.append("\n").append(runway.getPhysicalRunwayName());
                ObservableList<LogicalRunway> logRunways = runway.getLogicalRunways();
                for(LogicalRunway logRunway: logRunways){
                    message.append("\n  ").append(logRunway.getDesignator());
                    message.append("\n      ").append("TORA: ").append(logRunway.getTora());
                    message.append("\n      ").append("ASDA: ").append(logRunway.getAsda());
                    message.append("\n      ").append("TODA: ").append(logRunway.getToda());
                    message.append("\n      ").append("LDA: ").append(logRunway.getLda());
                }
            }
            message.append("\n\n\n");
        }

        return message.toString();
    }

    public static void exportImage(Node contentNode){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Download Images");

        // Set initial directory for the file chooser
        File userDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(userDirectory);

        // Set extension filters based on the type of file to be saved
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Png Files (*.png)", "*.png");
        FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPEG Files (*.jpeg)", "+.jpeg");
        fileChooser.getExtensionFilters().addAll(pngFilter, jpegFilter);

        // Show the file chooser and get the selected file directory
        File selectedDirectory = fileChooser.showSaveDialog(AirportManager.getStage());

        if (selectedDirectory != null) {
            try {
                SnapshotParameters params = new SnapshotParameters();
                params.setTransform(Transform.scale(2, 2)); // Set the device pixel ratio to 2x
                params.setFill(Color.TRANSPARENT);

                double width = contentNode.getBoundsInLocal().getWidth();
                double height = contentNode.getBoundsInLocal().getHeight();

                WritableImage snapshot = new WritableImage((int) (width * 2), (int) (height * 2));
                snapshot = contentNode.snapshot(params, snapshot);

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
                ImageIO.write(bufferedImage, "png", selectedDirectory);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }








}
