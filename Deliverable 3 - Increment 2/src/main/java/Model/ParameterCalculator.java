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
    String flightMethod = getFlightMethod(obstacle,runway);
    double newAsda;
    double newTora = runway.getNewTora();
    double stopway = runway.getStopway();

    //Calculate ASDA
    if (flightMethod.equals(TAKE_OFF_AWAY_LANDING_OVER)){
      newAsda =  newTora + stopway;
    }else {
      newAsda = newTora;
    }
    runway.setNewAsda(newAsda);
    return newAsda;

  }

  // Method to calculate TODA based on obstacle position
  public static double calculateTODA(Obstacle obstacle, LogicalRunway runway) {
    String flightMethod = getFlightMethod(obstacle,runway);
    double newToda;
    double newTora = runway.getNewTora();
    double clearway = runway.getClearway();

    // Calculate TODA
    if (flightMethod.equals(TAKE_OFF_AWAY_LANDING_OVER)){
      newToda =  newTora + clearway;
    }else {
      newToda = newTora;
    }
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
    if (displacedLandingThreshold <= blastProtection && flightMethod.equals(TAKE_OFF_AWAY_LANDING_OVER)){
      displacedLandingThreshold = blastProtection;
    }
    return displacedLandingThreshold;
  }

  public static boolean needRedeclare(Obstacle obstacle, LogicalRunway logicalRunway){
    double stripEnd = PhysicalRunway.stripEnd;
    double minCGArea = PhysicalRunway.minCGArea;
    boolean withinStripEnd = obstacle.getDistanceFromThreshold() <= logicalRunway.getTora() + stripEnd - logicalRunway.getDisplacedThreshold() && obstacle.getDistanceFromThreshold()  >= -stripEnd - logicalRunway.getDisplacedThreshold();
    boolean withinCentreline = obstacle.getDistanceFromCentre() <= minCGArea && obstacle.getDistanceFromCentre() >= -minCGArea;
    return withinStripEnd && withinCentreline;
  }

  // Helper method to determine flight method based on obstacle position
  public static String getFlightMethod(Obstacle obstacle, LogicalRunway runway) {
    String flightMethod;
    if (obstacle.getDistanceFromThreshold() < runway.getTora()/2){
      flightMethod = TAKE_OFF_AWAY_LANDING_OVER;
    }else {
      flightMethod = TAKE_OFF_TOWARDS_LANDING_TOWARDS;
    }
    return flightMethod;
  }

  public static int toraBreakdownChoice(Obstacle obstacle){
    int choice;
    double resa = PhysicalRunway.resa;
    if (resa > obstacle.getAlsTocs()){
      choice = 1;
    }else {
      choice = 2;
    }
    return choice;
  }

  public static int ldaBreakdownChoice(Obstacle obstacle){
    int choice;
    double resa = PhysicalRunway.resa;
    double alsTocs = obstacle.getAlsTocs();
    double stripEnd = PhysicalRunway.stripEnd;
    double blastProtection = PhysicalRunway.blastProtection;
    if (resa > alsTocs){
      choice = 1;
    } else if (blastProtection >= alsTocs + stripEnd) {
      choice = 2;
    } else {
      choice = 3;
    }
    return choice;
  }

  public static double getOppositeDistFromThreshold(Obstacle obstacle,PhysicalRunway runway){
    return runway.getLogicalRunways().get(0).getLda() - obstacle.getDistanceFromThreshold() - runway.getLogicalRunways().get(1).getDisplacedThreshold();
  }

  public static String toraBreakdown(Obstacle obstacle, LogicalRunway runway) {
    if (getFlightMethod(obstacle, runway).equals("Take-Off Away Landing Over")) {
      return "TORA = Old TORA - Blast Protection\n         - Distance from Threshold - Displaced Threshold\n         = " + runway.getTora() + " - " + PhysicalRunway.getBlastProtection() + " - " + obstacle.getDistanceFromThreshold() + " - " + runway.getDisplacedThreshold() + "\n         = " + runway.getNewTora() + "\n\n";
    } else {
      int ldaOrToraChoice = toraBreakdownChoice(obstacle);
      if (ldaOrToraChoice == 1) {
        return "TORA = Distance from threshold + Displaced Threshold\n         - RESA - Strip End\n         = " + obstacle.getDistanceFromThreshold() + " + " + runway.getDisplacedThreshold() + " - " + PhysicalRunway.getResa() + " - " + " - " + PhysicalRunway.getStripEnd() + "\n         = " + runway.getNewTora() + "\n";
      } else {
        double slopeRatio = Obstacle.slopeRatio;
        //other variables
        String slopeCalculation = obstacle.getHeight() + "*" + slopeRatio;
        return "TORA =  Distance from threshold + Displaced Threshold\n         - Slope Calculation - Strip End\n         = " + obstacle.getDistanceFromThreshold() + " + " + runway.getDisplacedThreshold() + " - " + slopeCalculation + " - " + PhysicalRunway.getStripEnd() + "\n         = " + runway.getNewTora() + "\n\n";
      }
    }
  }

  public static String todaBreakdown(Obstacle obstacle, LogicalRunway runway) {
    if (getFlightMethod(obstacle, runway).equals("Take-Off Away Landing Over")) {
      return "TODA = New TORA + CLEARWAY\n         = " + runway.getNewTora() + " + " + runway.getClearway() + "\n         = " + runway.getNewToda() + "\n\n";
    } else {
      return "TODA = New TORA\n         = " + runway.getNewToda() + "\n\n";
    }
  }

  public static String asdaBreakdown(Obstacle obstacle, LogicalRunway runway) {
    if (getFlightMethod(obstacle, runway).equals("Take-Off Away Landing Over")) {
      return "ASDA = New TORA + STOPWAY\n         = " + runway.getNewTora() + " + " + runway.getStopway() + "\n         = " + runway.getNewAsda() + "\n\n";
    } else{
      return "ASDA = New TORA\n         = " + runway.getNewAsda() + "\n\n";
    }
  }

  public static String ldaBreakdown(Obstacle obstacle, LogicalRunway runway) {
    double slopeRatio = Obstacle.slopeRatio;
    //other variables
    String slopeCalculation = obstacle.getHeight() + "*" + slopeRatio;
    if (getFlightMethod(obstacle, runway).equals("Take-Off Away Landing Over")) {
      int ldaOrToraChoice = ldaBreakdownChoice(obstacle);
      if (ldaOrToraChoice == 1){
        return "LDA  = Old LDA - Distance from threshold\n        - Strip End - RESA\n        = " + runway.getLda() + " - " + obstacle.getDistanceFromThreshold() + " - " + PhysicalRunway.getStripEnd() + " - " + PhysicalRunway.getResa() + "\n        = " + runway.getNewLda() + "\n\n";
      }
      else if (ldaOrToraChoice == 2){

        return "LDA  = Old LDA - Distance from threshold\n        - Blast Protection\n        = " + runway.getLda() + " - " + obstacle.getDistanceFromThreshold() + " - " + PhysicalRunway.getBlastProtection() +"\n        = " + runway.getNewLda() + "\n\n";
      }
      else {

        return "LDA  = Old LDA - Distance from threshold\n        - Strip End - Slope Calculation\n        = " + runway.getLda() + " - " + obstacle.getDistanceFromThreshold() + " - " + PhysicalRunway.getStripEnd() + " - " + slopeCalculation + "\n        = " + runway.getNewLda() + "\n\n";
      }

    } else{
      return "LDA   = Distance from Threshold - Strip End - RESA\n         = " + obstacle.getDistanceFromThreshold() + " - " + PhysicalRunway.getStripEnd() + " - " + PhysicalRunway.getResa() + "\n         = " + runway.getNewLda() + "\n\n";
    }
  }

}
