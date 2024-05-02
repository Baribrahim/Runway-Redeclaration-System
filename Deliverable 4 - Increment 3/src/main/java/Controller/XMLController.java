package Controller;

import Model.DatabaseModel;
import javafx.scene.control.Alert.AlertType;

import javax.xml.transform.OutputKeys;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.*;

import Controller.Helper.Notification;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XMLController {
  private static final Logger logger = LogManager.getLogger("XML");
  private DatabaseModel database;
  public void setDatabaseModel(DatabaseModel databaseModel) {
    this.database = databaseModel;
  }

  public void importAirports(File xmlFile) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      NodeList airportList = doc.getElementsByTagName("airport");
      for (int i = 0; i < airportList.getLength(); i++) {
        Node airportNode = airportList.item(i);
        if (airportNode.getNodeType() == Node.ELEMENT_NODE) {
          Element airportElement = (Element) airportNode;
          String airportName = airportElement.getElementsByTagName("name").item(0).getTextContent();
          database.insertAirport(airportName);

          NodeList runwayList = airportElement.getElementsByTagName("runways");
          for (int j = 0; j < runwayList.getLength(); j++) {
            Node runwayNode = runwayList.item(j);
            if (runwayNode.getNodeType() == Node.ELEMENT_NODE) {
              Element runwayElement = (Element) runwayNode;
              String runwayName = runwayElement.getElementsByTagName("name").item(0).getTextContent();
              Float leftTORA = Float.valueOf(
                  runwayElement.getElementsByTagName("leftTORA").item(0).getTextContent());
              Float leftTODA = Float.valueOf(
                  runwayElement.getElementsByTagName("leftTODA").item(0).getTextContent());
              Float leftASDA = Float.valueOf(
                  runwayElement.getElementsByTagName("leftASDA").item(0).getTextContent());
              Float leftLDA = Float.valueOf(
                  runwayElement.getElementsByTagName("leftLDA").item(0).getTextContent());
              Float righTORA = Float.valueOf(
                  runwayElement.getElementsByTagName("rightTORA").item(0).getTextContent());
              Float rightTODA = Float.valueOf(
                  runwayElement.getElementsByTagName("rightTODA").item(0).getTextContent());
              Float rightASDA = Float.valueOf(
                  runwayElement.getElementsByTagName("rightASDA").item(0).getTextContent());
              Float rightLDA = Float.valueOf(
                  runwayElement.getElementsByTagName("rightLDA").item(0).getTextContent());
              database.insertRunway(runwayName, airportName, leftTORA, leftTODA, leftASDA, leftLDA, rightTODA, righTORA, rightASDA, rightLDA);
            }
          }
        }
      }
      new Notification(AlertType.INFORMATION, "Import", "Airports imported successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      new Notification(AlertType.ERROR, "Error", "Airports not imported!");
    }
  }

  public void exportAirports(File xmlFile) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      Element rootElement = doc.createElement("airports");
      doc.appendChild(rootElement);

      ArrayList<String> airports = database.getAirports();
      for (String airportID : airports) {
        Element airportElement = doc.createElement("airport");
        rootElement.appendChild(airportElement);

        Element nameElement = doc.createElement("name");
        nameElement.appendChild(doc.createTextNode(database.getAirport(airportID)));
        airportElement.appendChild(nameElement);

        ArrayList<String> runways = database.getPhysicalRunways(airportID);
        Element runwaysElement = doc.createElement("runways");
        airportElement.appendChild(runwaysElement);

        for (String runwayID : runways) {
          Element runwayElement = doc.createElement("runway");
          runwaysElement.appendChild(runwayElement);

          Element runwayNameElement = doc.createElement("name");
          runwayNameElement.appendChild(doc.createTextNode(runwayID));
          runwayElement.appendChild(runwayNameElement);

          // Fetch runway parameters
          ArrayList<Float> parameters = database.getLogicalRunwayParameters(runwayID);
          String[] parameterNames = {"leftTORA", "leftTODA", "leftASDA", "leftLDA", "rightTORA",
              "rightTODA", "rightASDA", "rightLDA"};

          for (int i = 0; i < parameters.size(); i++) {
            Element parameterElement = doc.createElement(parameterNames[i]);
            // Convert float to string for XML text node
            parameterElement.appendChild(doc.createTextNode(Float.toString(parameters.get(i))));
            runwayElement.appendChild(parameterElement);
          }
        }
      }

      // Write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Nicely formats the output
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);
      new Notification(AlertType.CONFIRMATION, "Export", "Airports exported successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      new Notification(AlertType.ERROR, "Error", "Airports not exported!");
    }
  }

  public void importObstacles(File xmlFile) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      NodeList obstacleList = doc.getElementsByTagName("obstacle");
      for (int i = 0; i < obstacleList.getLength(); i++) {
        Node obstacleNode = obstacleList.item(i);
        if (obstacleNode.getNodeType() == Node.ELEMENT_NODE) {
          Element obstacleElement = (Element) obstacleNode;
          String obstacleName = obstacleElement.getElementsByTagName("name").item(0).getTextContent();
          Float obstacleHeight = Float.valueOf(
              obstacleElement.getElementsByTagName("height").item(0).getTextContent());
          Float obstacleWidht = Float.valueOf(
              obstacleElement.getElementsByTagName("width").item(0).getTextContent());
          database.insertObstacle(obstacleName, obstacleHeight, obstacleWidht);
        }
      }
      new Notification(AlertType.INFORMATION,"Import", "Obstacles imported successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      new Notification(AlertType.ERROR,"Error", "Obstacles not imported!");
    }
  }

  public void exportObstacles(File xmlFile) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      Element rootElement = doc.createElement("obstacles");
      doc.appendChild(rootElement);

      ArrayList<String> obstacles = database.getObstacles();
      for (String obstacleID : obstacles) {
        Element obstacleElement = doc.createElement("obstacle");
        rootElement.appendChild(obstacleElement);

        Element nameElement = doc.createElement("name");
        nameElement.appendChild(doc.createTextNode(obstacleID)); // Assuming obstacleID is the name; adjust if not
        obstacleElement.appendChild(nameElement);

        // Height Element
        Element heightElement = doc.createElement("height");
        heightElement.appendChild(doc.createTextNode(Float.toString(database.getObstacleHeight(obstacleID))));
        obstacleElement.appendChild(heightElement);

        // Width Element
        Element widthElement = doc.createElement("width");
        widthElement.appendChild(doc.createTextNode(Float.toString(database.getObstacleWidth(obstacleID))));
        obstacleElement.appendChild(widthElement);
      }

      // Write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Nicely formats the output
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);
      new Notification(AlertType.CONFIRMATION, "Export", "Obstacles exported successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      new Notification(AlertType.ERROR,"Error", "Obstacles not exported!");
    }
  }
  }
