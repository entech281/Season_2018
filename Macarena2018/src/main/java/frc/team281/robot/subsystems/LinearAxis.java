package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;

/**
 * Simple implementation of a linear axis.
 * Doesnt use PID-- the assumption is that we can get close
 * Uses a max speed for 'cruise', and then slows down when it gets near the ends.
 * enough with simple control
 * @author dcowden
 *
 */
public class LinearAxis {

    private DataLogger dataLogger;
    private Encoder axisEncoder;
    private WPI_TalonSRX talon;
    private DigitalInput startLimitSwitch;
    private DigitalInput endLimitSwitch;
    
    private int lengthCounts = 0;
    private int positionTolerance = 0;
    private int desiredPositionCounts = 0;
    private double cruiseSpeedPercent = 0.0;
    private double minSpeedPercent = 0.0;
    private double rampCounts = 0.0;
    private double currentSpeed = 0.0;
    private double inverted = 1.0;
    private MoveState moveState = MoveState.STOPPED;
    
    public enum MoveState {
        MOVING_FORWARD,
        MOVING_BACK,
        STOPPED
    }
    
    public LinearAxis(Encoder encoder, DigitalInput startLimit, DigitalInput endLimit, WPI_TalonSRX talon ,
            int lengthCounts, double cruiseSpeedPercent, double minSpeedPercent, int rampCounts, boolean inverted, int positionTolerance){
        dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger("LinearAxis");
        this.axisEncoder = encoder;
        this.startLimitSwitch = startLimit;
        this.endLimitSwitch = endLimit;
        this.talon = talon;
        this.lengthCounts = lengthCounts;
        this.cruiseSpeedPercent = cruiseSpeedPercent;
        this.positionTolerance = positionTolerance;
        this.minSpeedPercent = minSpeedPercent;
        this.rampCounts = rampCounts;
        if ( inverted){
            this.inverted = -1.0;
        }
        
    }
    
    public MoveState getMoveState(){
        return this.moveState;
    }
    
    public void moveToStart(){
        moveToPosition(0);
    }
    public void moveToEnd(){
        moveToPosition(this.lengthCounts);
    }
    
    public void moveToPosition(int positionCounts){
        desiredPositionCounts = limitCommand(positionCounts);
    }
    
    public void init(){
        //assume we're starting on the bottom
        axisEncoder.reset();
        talon.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void stop(){
        talon.set(0.0);
        currentSpeed = 0.0;
        moveState = MoveState.STOPPED;
    }
    
    public double getSpeed(){
        return this.currentSpeed;
    }
    
    public int getCommandedPosition(){
        return desiredPositionCounts;
    }
    
    public int getPosition(){
        return axisEncoder.get();
    }
    
    public void periodic(){
        dataLogger.log("Position",getPosition());
        dataLogger.log("Speed",getSpeed() );
        dataLogger.log("State",this.moveState+"");
        checkLimits();
        computeSpeed();
        talon.set(inverted*currentSpeed);
    }
    
    public void computeSpeed(){
        //this is a basic trapezoidal profile
        //if we're more than rampdown counts, go full speed.
        //other wise, taper down to min speed over ramp counts
        
        int distanceToMove = desiredPositionCounts - getPosition();
        int absDistanceToMove = Math.abs(distanceToMove);
        if ( absDistanceToMove < positionTolerance){
            stop();
        }
        else if ( absDistanceToMove >= rampCounts){
            currentSpeed  = cruiseSpeedPercent;
        }
        else{
            //start ramping down
            double rampPercentage = (double)absDistanceToMove / (double)rampCounts;
            currentSpeed = minSpeedPercent + (( cruiseSpeedPercent - minSpeedPercent) * rampPercentage );
        }
        
        //now apply sign
        if ( distanceToMove < 0 ){
            currentSpeed *= (-1.0);
            moveState = MoveState.MOVING_BACK;
        }
        else{
            moveState = MoveState.MOVING_FORWARD;
        }
    }
   
    public void checkLimits(){
        if ( moveState == MoveState.MOVING_BACK ){
             if (!  startLimitSwitch.get() || getPosition() <= 0 ){
                 stop();
                 axisEncoder.reset();                                  
             }
        }

        if ( moveState == MoveState.MOVING_FORWARD ){
            if (  ! endLimitSwitch.get() || getPosition() > this.lengthCounts ){
                stop();
            }
        }    
    }
    
    public int limitCommand(int commandedPosition){
        if ( commandedPosition > this.lengthCounts){
            return this.lengthCounts;
        }
        else if ( commandedPosition < 0 ){
            return 0;
        }
        else{
            return commandedPosition;
        }
    }
}
