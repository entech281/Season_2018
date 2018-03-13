package frc.team281.subsystems;

import static org.junit.Assert.*;

import org.junit.Test;

import frc.team281.robot.subsystems.TrapezoidalSpeedCalculator;

public class TestTrapezoidalAxis {

    protected TrapezoidalSpeedCalculator trap;
    
    public static final int LENGTH = 1000;
    public static final double CRUISE = 1.0;
    public static final double CREEP = 0.2;
    public static final int RAMP = 100;
    public static final int TOLERANCE = 10;    
    public static final double CMP_TOLERANCE = 0.001;
    
    @Test
    public void testTopBottomCalculations(){
        trap = new  TrapezoidalSpeedCalculator(LENGTH, CRUISE, CREEP, RAMP, TOLERANCE);        
        trap.setDesiredPositionCounts(1000);
        
        assertTrue(trap.isMoveBasicallyTop());
        assertFalse(trap.isMoveBasicallyBottom());
        
        trap.setDesiredPositionCounts(995);
        assertTrue(trap.isMoveBasicallyTop());
        assertFalse(trap.isMoveBasicallyBottom());        
    }
    
    @Test
    public void testTrapezoidSpeedCalcs(){

        trap = new  TrapezoidalSpeedCalculator(LENGTH, CRUISE, CREEP, RAMP, TOLERANCE);
        
        trap.setDesiredPositionCounts(500);
        
        assertFalse(trap.isMoveBasicallyTop());
        assertFalse(trap.isMoveBasicallyBottom());
        
        //points on the up move
        trap.setCurrentPositionCounts(0);
        assertEquals(trap.calculateSpeed(),CRUISE,CMP_TOLERANCE);
        
        trap.setCurrentPositionCounts(400);
        assertEquals(trap.calculateSpeed(), CRUISE, CMP_TOLERANCE);
        
        trap.setCurrentPositionCounts(450);
        assertEquals(trap.calculateSpeed(), 0.6, CMP_TOLERANCE);
        
        trap.setCurrentPositionCounts(491);
        assertEquals(trap.calculateSpeed(), 0.0, CMP_TOLERANCE);
        
        trap.setCurrentPositionCounts(500);
        assertEquals(trap.calculateSpeed(), 0.0, CMP_TOLERANCE);
        
        trap.setCurrentPositionCounts(509);
        assertEquals(trap.calculateSpeed(), 0.0, CMP_TOLERANCE);
        
        //points on the down move
        trap.setCurrentPositionCounts(550);
        assertEquals(trap.calculateSpeed(), -0.6, CMP_TOLERANCE);
        
        trap.setCurrentPositionCounts(602);
        assertEquals(trap.calculateSpeed(), -CRUISE, CMP_TOLERANCE);
    }    
    
    @Test
    public void testThatMaxPositionIsLimited(){
        trap = new  TrapezoidalSpeedCalculator(LENGTH, CRUISE, CREEP, RAMP, TOLERANCE);
        
        trap.setDesiredPositionCounts(5000);   
        assertEquals(trap.getDesiredPositionCounts(), LENGTH);

        trap.setDesiredPositionCounts(-100);   
        assertEquals(trap.getDesiredPositionCounts(), 0);      
        
        trap.setDesiredPositionCounts(1000);
        trap.setCurrentPositionCounts(5000);
        assertEquals(0.0,trap.calculateSpeed(),CMP_TOLERANCE);
     }
}
