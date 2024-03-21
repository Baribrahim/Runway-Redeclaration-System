import Model.ParameterCalculator;
import Model.LogicalRunway;
import Model.Obstacle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterCalculatorTest {

  LogicalRunway logicalRunwayL = new LogicalRunway("09L",3902,3902,3902,3595);
  LogicalRunway logicalRunwayR = new LogicalRunway("27R",3884,3962,3884,3884);
  Obstacle obstacle1 = new Obstacle("obstacle1",12,0,0,-50);
  Obstacle obstacle2 = new Obstacle("obstacle2",12,0,0,3646);

  @Test
  @DisplayName("Test TORA calculation for Takeoff Away")
  void calcTORA_TA() {
    assertEquals(3345, ParameterCalculator.calculateTORA(obstacle1,logicalRunwayL));
  }

  @Test
  @DisplayName("Test TORA calculation for Takeoff Towards")
  void calcTORA_TT() {
    assertEquals(2986,ParameterCalculator.calculateTORA(obstacle2,logicalRunwayR));
  }

  @Test
  @DisplayName("Test TODA calculation for Takeoff Away Landing Over")
  void calcTODA_TALO() {
    ParameterCalculator.calculateTORA(obstacle1,logicalRunwayL);
    assertEquals(3345,ParameterCalculator.calculateTODA(obstacle1,logicalRunwayL));
  }

  @Test
  @DisplayName("Test TODA calculation for Takeoff Towards Landing Towards")
  void calcTODA_TTLT() {
    ParameterCalculator.calculateTORA(obstacle2,logicalRunwayR);
    assertEquals(2986,ParameterCalculator.calculateTODA(obstacle2,logicalRunwayR));
  }

  @Test
  @DisplayName("Test ASDA calculation for Takeoff Away Landing Over")
  void calcASDA_TALO() {
    ParameterCalculator.calculateTORA(obstacle1,logicalRunwayL);
    assertEquals(3345,ParameterCalculator.calculateASDA(obstacle1,logicalRunwayL));
  }

  @Test
  @DisplayName("Test ASDA calculation for Takeoff Towards Landing Towards")
  void calcASDA_TTLT() {
    ParameterCalculator.calculateTORA(obstacle2,logicalRunwayR);
    assertEquals(2986,ParameterCalculator.calculateASDA(obstacle2,logicalRunwayR));
  }

  @Test
  @DisplayName("Test LDA calculation for Landing Over")
  void calcLDA_LO() {
    assertEquals(2985,ParameterCalculator.calculateLDA(obstacle1,logicalRunwayL));
  }

  @Test
  @DisplayName("Test LDA calculation for Landing Towards")
  void calcLDA_LT() {
    assertEquals(2986,ParameterCalculator.calculateTORA(obstacle2,logicalRunwayR));
  }

  @Test
  @DisplayName("Test if runway needs declaration given obstacle and LogicalRunway")
  void needRedeclare(){
    Obstacle obstacleGreaterThanCentreline = new Obstacle("obstacle1",10,10,200,50);
    Obstacle obstacleLesserThanCentreline = new Obstacle("obstacle2",10,10,-200,50);
    Obstacle obstacleGreaterThanStripEnd = new Obstacle("obstacle3",10,10,50,99999);
    Obstacle obstacleLesserThanStripEnd = new Obstacle("obstacle4",10,10,50,-999);
    assertFalse(ParameterCalculator.needRedeclare(obstacleGreaterThanCentreline, logicalRunwayL));
    assertFalse(ParameterCalculator.needRedeclare(obstacleLesserThanCentreline, logicalRunwayL));
    assertFalse(ParameterCalculator.needRedeclare(obstacleGreaterThanStripEnd, logicalRunwayL));
    assertFalse(ParameterCalculator.needRedeclare(obstacleLesserThanStripEnd, logicalRunwayL));
    assertTrue(ParameterCalculator.needRedeclare(obstacle1, logicalRunwayL));
  }

  @Test
  @DisplayName("Test if correct choice of TORA breakdown is chosen")
  void toraBreakdownChoice(){
    assertEquals(2, ParameterCalculator.toraBreakdownChoice(obstacle2));
    Obstacle toraObs = new Obstacle("toraObs", 20, 0, 20, 3500);
    assertEquals(2, ParameterCalculator.toraBreakdownChoice(toraObs));
  }

  @Test
  @DisplayName("Test if correct choice of LDA breakdown is chosen")
  void ldaBreakdownChoice(){
    assertEquals(3, ParameterCalculator.ldaBreakdownChoice(obstacle1));
  }

  @Test
  @DisplayName("Test if the correct flight method is used for the correct runway direction")
  void getFlightMethod(){
    assertEquals(ParameterCalculator.TAKE_OFF_AWAY_LANDING_OVER,ParameterCalculator.getFlightMethod(obstacle1,logicalRunwayL));
    assertEquals(ParameterCalculator.TAKE_OFF_TOWARDS_LANDING_TOWARDS,ParameterCalculator.getFlightMethod(obstacle2,logicalRunwayR));
  }

  // alsTocs = 10*50 = 500 > 240 (resa)
  // alsTocs = 4*50 = 200 < 240 (resa)
  @Test
  @DisplayName("Test if the displaced landing threshold is correct dependent on whether alsTocs greater or lesser than resa")
  void getDisplacedLandingThreshold(){
    Obstacle alsTocsGreaterThanResa      = new Obstacle("alsTocsGreaterThanResa",10,50,0,50);
    Obstacle alsTocsLesserThanResa      = new Obstacle("alsTocsLesserThanResa",4,50,0,50);
    assertEquals(560,ParameterCalculator.getDisplacedLandingThreshold(alsTocsGreaterThanResa.getAlsTocs(),ParameterCalculator.TAKE_OFF_AWAY_LANDING_OVER));
    assertEquals(300,ParameterCalculator.getDisplacedLandingThreshold(alsTocsLesserThanResa.getAlsTocs(),ParameterCalculator.TAKE_OFF_AWAY_LANDING_OVER));
  }
}
