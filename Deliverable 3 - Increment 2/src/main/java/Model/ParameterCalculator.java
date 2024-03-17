//package Model;
//
//public class ParameterCalculator {
//  //Constructor
//  public ParameterCalculator() {}
//
//  public static final String talo = "Take-Off Away Landing Over";
//  public static final String ttlt = "Take-Off Towards Landing Towards";
//
//  public static double calculateTORA(Obstacle obstacle,LogicalRunway runway){
//    String flightMethod = getFlightMethod(obstacle,runway);
//    double originalTora = runway.getTora();
//    double blastProtection = PhysicalRunway.blastProtection;
//    double newTora;
//    double distanceFromThreshold = obstacle.getDistanceFromThreshold();
//    double displacedThreshold = runway.getDisplacedThreshold();
//    double displacedLandingThreshold = getDisplacedLandingThreshold(obstacle.getAlsTocs(),ttlt);
//
//    //Calculate TORA
//    if(flightMethod.equals(talo)){
//      newTora = originalTora - blastProtection - distanceFromThreshold - displacedThreshold;
//    }else {
//      newTora = distanceFromThreshold + displacedThreshold - displacedLandingThreshold;
//    }
//    runway.setNewTora(newTora);
//    return newTora;
//  }
//
//  public static double calculateLDA(Obstacle obstacle,LogicalRunway runway){
//    String flightMethod = getFlightMethod(obstacle,runway);
//    double originalLda = runway.getLda();
//    double distanceFromThreshold = obstacle.getDistanceFromThreshold();
//    double newLda;
//    double resa = PhysicalRunway.resa;
//    double stripEnd = PhysicalRunway.stripEnd;
//    double displacedLandingThreshold = getDisplacedLandingThreshold(obstacle.getAlsTocs(),talo);
//
//    //Calculate LDA
//    if (flightMethod.equals(talo)){
//      newLda = originalLda - distanceFromThreshold - displacedLandingThreshold;
//    }else {
//      newLda = distanceFromThreshold - stripEnd - resa;
//    }
//    runway.setNewLda(newLda);
//    return newLda;
//  }
//
//  public static double calculateASDA(Obstacle obstacle, LogicalRunway runway){
//    String flightMethod = getFlightMethod(obstacle,runway);
//    double newAsda;
//    double newTora = runway.getNewTora();
//    double stopway = runway.getStopway();
//
//    //Calculate ASDA
//    if (flightMethod.equals(talo)){
//      newAsda =  newTora + stopway;
//    }else {
//      newAsda = newTora;
//    }
//    runway.setNewAsda(newAsda);
//    return newAsda;
//  }
//
//  public static double calculateTODA(Obstacle obstacle, LogicalRunway runway){
//    String flightMethod = getFlightMethod(obstacle,runway);
//    double newToda;
//    double newTora = runway.getNewTora();
//    double clearway = runway.getClearway();
//
//    // Calculate TODA
//    if (flightMethod.equals(talo)){
//      newToda =  newTora + clearway;
//    }else {
//      newToda = newTora;
//    }
//    runway.setNewToda(newToda);
//    return newToda;
//  }
//
//  // For both flight method TTLT(Takeoff Towards Landing Towards) and TALO(Takeoff Away Landing Over),
//  // If alsTocs(height of obstacle * 50) is less than RESA, displaced threshold will be (resa + strip) end otherwise it will be (alsTocs + strip end).
//  // Only if the flight method is TALO(Takeoff Away Landing Over),
//  // When either (resa + strip) or (alsTocs + strip end) is less than blast protection value, we need to add blast protection value into Displaced Landing Threshold.
//  public static double getDisplacedLandingThreshold(double alsTocs, String flightMethod){
//    double displacedLandingThreshold;
//    double blastProtection = PhysicalRunway.blastProtection;
//    double resa = PhysicalRunway.resa;
//    double stripEnd = PhysicalRunway.stripEnd;
//
//    if (alsTocs < resa){
//      displacedLandingThreshold = resa + stripEnd;
//    }else {
//      displacedLandingThreshold = alsTocs + stripEnd;
//    }
//    if (displacedLandingThreshold <= blastProtection && flightMethod.equals(talo)){
//      displacedLandingThreshold = blastProtection;
//    }
//    return displacedLandingThreshold;
//  }
//
//  //Check if the obstacle is within the strip end, or minimum clear graded area
//  //the declaration needs to be redeclare if true.
//  public static boolean needRedeclare(Obstacle obstacle, LogicalRunway logicalRunway){
//    double stripEnd = PhysicalRunway.stripEnd;
//    double minCGArea = PhysicalRunway.minCGArea;
//    boolean withinStripEnd = obstacle.getDistanceFromThreshold() <= logicalRunway.getTora() + stripEnd - logicalRunway.getDisplacedThreshold() && obstacle.getDistanceFromThreshold()  >= -stripEnd - logicalRunway.getDisplacedThreshold();
//    boolean withinCentreline = obstacle.getDistanceFromCentre() <= minCGArea && obstacle.getDistanceFromCentre() >= -minCGArea;
//    return withinStripEnd && withinCentreline;
//  }
//
//  public static String getFlightMethod(Obstacle obstacle, LogicalRunway logicalRunway){
//    String flightMethod;
//    if (obstacle.getDistanceFromThreshold() < logicalRunway.getTora()/2){
//      flightMethod = talo;
//    }else {
//      flightMethod = ttlt;
//    }
//    return flightMethod;
//  }
//}
package Model;

public class ParameterCalculator {
  // Constants
  public static final String TAKE_OFF_AWAY_LANDING_OVER = "Take-Off Away Landing Over";
  public static final String TAKE_OFF_TOWARDS_LANDING_TOWARDS = "Take-Off Towards Landing Towards";

  // Constructor
  public ParameterCalculator() {}

  // Method to calculate TORA based on obstacle position
  public static double calculateTORA(Obstacle obstacle, LogicalRunway runway) {
    String flightMethod = getFlightMethod(obstacle, runway);
    double originalTora = runway.getTora();
    double distanceFromThreshold = obstacle.getDistanceFromThreshold();
    double blastProtection = PhysicalRunway.blastProtection;
    double displacedThreshold = runway.getDisplacedThreshold();
    double displacedLandingThreshold = getDisplacedLandingThreshold(obstacle.getAlsTocs(), TAKE_OFF_TOWARDS_LANDING_TOWARDS);

    double newTora;
    if (flightMethod.equals(TAKE_OFF_AWAY_LANDING_OVER)) {
      newTora = originalTora - blastProtection - distanceFromThreshold - displacedThreshold;
    } else {
      newTora = distanceFromThreshold + displacedThreshold - displacedLandingThreshold;
    }

    runway.setNewTora(newTora);
    return newTora;
  }

  // Method to calculate LDA based on obstacle position
  public static double calculateLDA(Obstacle obstacle, LogicalRunway runway) {
    String flightMethod = getFlightMethod(obstacle, runway);
    double originalLda = runway.getLda();
    double distanceFromThreshold = obstacle.getDistanceFromThreshold();
    double resa = PhysicalRunway.resa;
    double stripEnd = PhysicalRunway.stripEnd;
    double displacedLandingThreshold = getDisplacedLandingThreshold(obstacle.getAlsTocs(), TAKE_OFF_AWAY_LANDING_OVER);

    double newLda;
    if (flightMethod.equals(TAKE_OFF_AWAY_LANDING_OVER)) {
      newLda = originalLda - distanceFromThreshold - displacedLandingThreshold;
    } else {
      newLda = distanceFromThreshold - stripEnd - resa;
    }

    runway.setNewLda(newLda);
    return newLda;
  }

  // Method to calculate ASDA based on obstacle position
  public static double calculateASDA(Obstacle obstacle, LogicalRunway runway) {
    double newTora = calculateTORA(obstacle, runway);
    double stopway = runway.getStopway();
    double newAsda = newTora + stopway;

    runway.setNewAsda(newAsda);
    return newAsda;
  }

  // Method to calculate TODA based on obstacle position
  public static double calculateTODA(Obstacle obstacle, LogicalRunway runway) {
    double newTora = calculateTORA(obstacle, runway);
    double clearway = runway.getClearway();
    double newToda = newTora + clearway;

    runway.setNewToda(newToda);
    return newToda;
  }

  // Helper method to get displaced landing threshold
  public static double getDisplacedLandingThreshold(double alsTocs, String flightMethod) {
    double displacedLandingThreshold;
    double blastProtection = PhysicalRunway.blastProtection;
    double resa = PhysicalRunway.resa;
    double stripEnd = PhysicalRunway.stripEnd;

    if (alsTocs < resa) {
      displacedLandingThreshold = resa + stripEnd;
    } else {
      displacedLandingThreshold = alsTocs + stripEnd;
    }

    if (flightMethod.equals(TAKE_OFF_AWAY_LANDING_OVER) && displacedLandingThreshold < blastProtection) {
      displacedLandingThreshold += blastProtection;
    }

    return displacedLandingThreshold;
  }

  // Helper method to determine flight method based on obstacle position
  public static String getFlightMethod(Obstacle obstacle, LogicalRunway runway) {
    return (obstacle.getDistanceFromThreshold() < runway.getTora() / 2) ?
            TAKE_OFF_AWAY_LANDING_OVER : TAKE_OFF_TOWARDS_LANDING_TOWARDS;
  }
}
