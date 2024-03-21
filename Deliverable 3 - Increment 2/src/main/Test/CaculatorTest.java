import Model.ParameterCalculator;
import Model.LogicalRunway;
import Model.Obstacle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    LogicalRunway lR09L = new LogicalRunway("09L",3902,3902,3902,3595);
    LogicalRunway lR27R = new LogicalRunway("27R",3884,3962,3884,3884);
    LogicalRunway lR27L = new LogicalRunway("27L", 3660, 3660, 3660, 3660);
    Obstacle obstacle1 = new Obstacle("obstacle1",12,0,0,-50);
    Obstacle obstacle2 = new Obstacle("obstacle2",12,0,0,3646);

    @Test
    @DisplayName("Test calculation of Tora for Takeoff Away")
    void calcTora_TA() {
        assertEquals(3345, ParameterCalculator.calculateTORA(obstacle1,lR09L));
    }

    @Test
    @DisplayName("Test calculation of Lda for Landing Over")
    void calcLda_LO() {
        assertEquals(2985,ParameterCalculator.calculateLDA(obstacle1,lR09L));
    }

    @Test
    @DisplayName("Test calculation of Tora for Takeoff Towards")
    void calcTora_TT() {
        assertEquals(2986,ParameterCalculator.calculateTORA(obstacle2,lR27R));
    }

    @Test
    @DisplayName("Test calculation of Lda for Landing Towards")
    void calcLda_LT() {
        assertEquals(2986,ParameterCalculator.calculateTORA(obstacle2,lR27R));
    }

    @Test
    @DisplayName("Test calculation of Asda for Takeoff Away Landing Over")
    void calcAsda_TALO() {
        ParameterCalculator.calculateTORA(obstacle1,lR09L);
        assertEquals(3345,ParameterCalculator.calculateASDA(obstacle1,lR09L));
    }

    @Test
    @DisplayName("Test calculation of Toda for Takeoff Away Landing Over")
    void calcToda_TALO() {
        ParameterCalculator.calculateTORA(obstacle1,lR09L);
        assertEquals(3345,ParameterCalculator.calculateTODA(obstacle1,lR09L));
    }

    @Test
    @DisplayName("Test calculation of Asda for Takeoff Towards Landing Towards")
    void calcAsda_TTLT() {
        ParameterCalculator.calculateTORA(obstacle2,lR27R);
        assertEquals(2986,ParameterCalculator.calculateASDA(obstacle2,lR27R));
    }

    @Test
    @DisplayName("Test calculation of Toda for Takeoff Towards Landing Towards")
    void calcToda_TTLT() {
        ParameterCalculator.calculateTORA(obstacle2,lR27R);
        assertEquals(2986,ParameterCalculator.calculateTODA(obstacle2,lR27R));
    }

    //GT = greater than
    //LT = less than
    @Test
    @DisplayName("Test if we needs to redeclare given obstacle and logicalRunway")
    void needRedeclare(){
        Obstacle obstacleGTCentreline = new Obstacle("obstacle1",10,10,200,50);
        Obstacle obstacleLTCentreline = new Obstacle("obstacle2",10,10,-200,50);
        Obstacle obstacleGTStripEnd = new Obstacle("obstacle3",10,10,50,10000);
        Obstacle obstacleLTStripEnd = new Obstacle("obstacle4",10,10,50,-370);
        assertFalse(ParameterCalculator.needRedeclare(obstacleGTStripEnd, lR09L));
        assertFalse(ParameterCalculator.needRedeclare(obstacleLTStripEnd, lR09L));
        assertFalse(ParameterCalculator.needRedeclare(obstacleGTCentreline, lR09L));
        assertFalse(ParameterCalculator.needRedeclare(obstacleLTCentreline, lR09L));
        assertTrue(ParameterCalculator.needRedeclare(obstacle1, lR09L));
    }

    @Test
    void ldaBreakdownChoice(){
        assertEquals(3, ParameterCalculator.ldaBreakdownChoice(obstacle1));
    }

    @Test
    void toraBreakdownChoice(){
        assertEquals(2, ParameterCalculator.toraBreakdownChoice(obstacle2));
        Obstacle obstacle3 = new Obstacle("obs3", 20, 0, 20, 3546);
        assertEquals(2, ParameterCalculator.toraBreakdownChoice(obstacle3));
    }

    @Test
    void getFlightMethod(){
        assertEquals(ParameterCalculator.TAKE_OFF_AWAY_LANDING_OVER,ParameterCalculator.getFlightMethod(obstacle1,lR09L));
        assertEquals(ParameterCalculator.TAKE_OFF_TOWARDS_LANDING_TOWARDS,ParameterCalculator.getFlightMethod(obstacle2,lR27R));
    }

    @Test
    void getDisplacedLandingThreshold(){
        Obstacle alsTocsLTResa      = new Obstacle("alsTocsLTResa",2,50,0,50); //alstocs = 2*50=100 < 240 (resa)
        Obstacle alsTocsGTResa      = new Obstacle("alsTocsGTResa",5,50,0,50); //alstocs = 5*50=250 > 240 (resa)
        assertEquals(300,ParameterCalculator.getDisplacedLandingThreshold(alsTocsLTResa.getAlsTocs(),ParameterCalculator.TAKE_OFF_AWAY_LANDING_OVER));
        assertEquals(310,ParameterCalculator.getDisplacedLandingThreshold(alsTocsGTResa.getAlsTocs(),ParameterCalculator.TAKE_OFF_AWAY_LANDING_OVER));
    }
}
