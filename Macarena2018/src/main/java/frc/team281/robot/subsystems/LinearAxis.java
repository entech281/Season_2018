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
    
    private double currentSpeed = 0.0;
    private double inverted = 1.0;
    private TrapezoidalSpeedCalculator motionProfile;

    
    private MoveState moveState = MoveState.STOPPED;
    
    
    public enum MoveState {
        MOVING_FORWARD,
        MOVING_BACK,
        STOPPED
    }
    
    public LinearAxis(Encoder encoder, DigitalInput startLimit, DigitalInput endLimit, WPI_TalonSRX talon ,
            TrapezoidalSpeedCalculator motionProfile, boolean inverted){
        dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger("LinearAxis");
        this.motionProfile = motionProfile;
        this.axisEncoder = encoder;
        this.startLimitSwitch = startLimit;
        this.endLimitSwitch = endLimit;
        this.talon = talon;

        if ( inverted){
            this.inverted = -1.0;
        }
        
    }
    
    protected void setMotorSpeed(double speedPercent){
        talon.set(ControlMode.PercentOutput, speedPercent);
    }
    
    public MoveState getMoveState(){
        return this.moveState;
    }
    
    public void moveToStart(){
        motionProfile.setPositionToStart();
    }
    
    public void moveToEnd(){
        motionProfile.setPositionToEnd();
    }
    
    public void moveToPosition(int positionCounts){
        motionProfile.setDesiredPositionCounts(positionCounts);
    }
    
    public void init(){
        //assume we're starting on the bottom
        axisEncoder.reset();
        setMotorSpeed( 0.0);
    }
    
    public void stop(){
        setMotorSpeed(0.0);
        currentSpeed = 0.0;
        moveState = MoveState.STOPPED;
    }
    
    public double getSpeed(){
        return this.currentSpeed;
    }
    

    
    public int getPosition(){
        return axisEncoder.get();
    }
    
    public void periodic(){
        dataLogger.log("Position",getPosition());
        dataLogger.log("Speed",getSpeed() );
        dataLogger.log("State",this.moveState+"");
        int currentPosition = axisEncoder.get();
        motionProfile.setCurrentPositionCounts(currentPosition);
        checkLimits();
        
        currentSpeed = motionProfile.calculateSpeed();
        talon.set(inverted*currentSpeed);
    }
    

   
    public void checkLimits(){
        if ( moveState == MoveState.MOVING_BACK ){
             if (!  startLimitSwitch.get() || motionProfile.isAtLowerLimit() ){
                 stop();
                 axisEncoder.reset();                                  
             }
        }

        if ( moveState == MoveState.MOVING_FORWARD ){
            if (  ! endLimitSwitch.get() || motionProfile.isAtUpperLimit() ){
                stop();
            }
        }    
    }
    
}
