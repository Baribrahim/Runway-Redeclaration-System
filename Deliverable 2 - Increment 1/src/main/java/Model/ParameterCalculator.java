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
