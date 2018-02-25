package frc.team281.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team281.robot.talons.TalonEncoderStatus;

public class TestTalonEncoderStatus {

    public static final int VELOCITY = 10;
    protected TalonEncoderStatus status = new TalonEncoderStatus();
    
    @Test
    public void testInitalStatusIsOk(){
        assertTrue(status.isEncoderOk());
    }
    
    @Test
    public void testOkWhileMoving(){
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);
        assertTrue(status.isEncoderOk());
        
        status.updateEncoderStatus(200,VELOCITY,ControlMode.PercentOutput,1.0);
        assertTrue(status.isEncoderOk());
        
        status.updateEncoderStatus(300,VELOCITY,ControlMode.PercentOutput,1.0);
        assertTrue(status.isEncoderOk());
       
        //should report broken encoder after two updates without an increase in position
        status.updateEncoderStatus(300,VELOCITY,ControlMode.PercentOutput,1.0);
        status.updateEncoderStatus(300,VELOCITY,ControlMode.PercentOutput,1.0);
        status.updateEncoderStatus(300,VELOCITY,ControlMode.PercentOutput,1.0);
        assertFalse(status.isEncoderOk());    
        assertEquals(status.getPosition(), TalonEncoderStatus.NOT_READING);
        assertEquals(status.getVelocity(), TalonEncoderStatus.NOT_READING);
        
        //now a good update should give us the ok
        status.updateEncoderStatus(400,VELOCITY,ControlMode.PercentOutput,1.0);
        assertTrue(status.isEncoderOk());            
        assertEquals(status.getPosition(), 400);
        assertEquals(status.getVelocity(), VELOCITY);
    }
    
    @Test
    public void testNotBrokenAfterOnlyTwoSample(){
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);
        assertTrue(status.isEncoderOk());
        
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);
        assertTrue(status.isEncoderOk());        
                   
    }
    
    @Test
    public void testBrokenAfterFourSamples(){
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);
        assertTrue(status.isEncoderOk());
        
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);
        status.updateEncoderStatus(0,VELOCITY,ControlMode.PercentOutput,1.0);        
        assertFalse(status.isEncoderOk());                           
    }    
    
    @Test
    public void testNotBrokenWhenNotCommandedToRun(){
        status.updateEncoderStatus(0,VELOCITY,ControlMode.Disabled,1.0);
        status.updateEncoderStatus(0,VELOCITY,ControlMode.Disabled,1.0);
        status.updateEncoderStatus(0,VELOCITY,ControlMode.Disabled,1.0);
        status.updateEncoderStatus(0,VELOCITY,ControlMode.Follower,1.0);        
        assertTrue(status.isEncoderOk()); 
    }
    
    
}
