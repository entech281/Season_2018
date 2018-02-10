package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.RobotMap;

/**
 * This is the drive system that will run in the robot. All the wpilib stuff
 * goes here.
 * 
 * ref https://www.chiefdelphi.com/forums/showthread.php?p=1633629
 * motionMagic is basically a trade name for a trapezoidal motion profile
 * a java example :
 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java/MotionMagic/src/org/usfirst/frc/team217/robot
 * ctre api doc
 * http://www.ctr-electronics.com/downloads/api/java/html/index.html
 * software manual
 * https://github.com/CrossTheRoadElec/Phoenix-Documentation/raw/master/Talon%20SRX%20Victor%20SPX%20-%20Software%20Reference%20Manual.pdf
 * 
 * @author dcowden
 *
 */
public class RealDriveSubsystem extends BaseDriveSubsystem {

    //for speed control
	private DifferentialDrive drive;
	
	//for position control
	private MotorPositionController frontLeftMotorPosition;
	private MotorPositionController rearLeftMotorPosition;
	private MotorPositionController frontRightMotorPosition;
	private MotorPositionController rearRightMotorPosition;

	//have to hold on to these to change control modes.
    private WPI_TalonSRX frontLeftMotor;
    private WPI_TalonSRX frontRightMotor;
    private WPI_TalonSRX rearLeftMotor;
    private WPI_TalonSRX rearRightMotor;	
	
	//estimated based on 6" diameter wheels, with 80 counts per turn, gear ratio 14/52 * 14/52 13.8:1
	public static final double ENCODER_CLICKS_PER_INCH = 3.5;
	    
    //units are counts/sec
    //max possible speed:
    //estimated at 3200 rev/min * 80 counts/rev * 1 min / 60 sec = 4266 counts/sec 
	//note that this speed is not affected by the gear ratio!
    //75% of that is 3200 counts/sec
    public static final int MOTOR_CRUISE_VELOCITY = 3200;

    //lets get to full speed in 1 second
    //not affected by gear ratio
    public static final int MOTOR_ACCELERATION = 3200;
    
    // recommended-- start with all gains but P, and work from there
    // then double the gain until oscillations occur
    // then add d gain at 10 to 100x P
    // finally, add I = 0.01xP
	public static class MotorConstants{
	    public static final double K_MOTOR = 0.0;
	    public static final double I_MOTOR = 0.0;
	    public static final double P_MOTOR = 0.1;
	    public static final double D_MOTOR = 0.0;
	    public static final double F_MOTOR = 0.0;	    
	}	

	public RealDriveSubsystem(DriveInstructionSource driveInstructionSource) {
		super(driveInstructionSource);
	}

	@Override
	public synchronized void initialize() {
	
	    this.frontLeftMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
	    this.frontRightMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);
	    this.rearLeftMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
	    this.rearRightMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);

	    //set up for speed control
		drive = new DifferentialDrive(new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
				new SpeedControllerGroup(frontRightMotor, rearRightMotor));
	}
	
	protected void initPositionControllers(Position desiredPosition){
	    
        frontLeftMotorPosition = new MotorPositionController.Builder(frontLeftMotor) 
                .withGains(MotorConstants.F_MOTOR, MotorConstants.P_MOTOR, 
                           MotorConstants.I_MOTOR, MotorConstants.D_MOTOR)
                .withMotionProfile(MOTOR_ACCELERATION, MOTOR_CRUISE_VELOCITY)
                .inverted(true)
                .build();
        frontLeftMotorPosition.setDesiredPosition(convertFromInchesToEncoderCounts(desiredPosition.getLeftInches()));
        
        rearLeftMotorPosition = new MotorPositionController.Builder(frontLeftMotor) 
                .withGains(MotorConstants.F_MOTOR, MotorConstants.P_MOTOR, 
                        MotorConstants.I_MOTOR, MotorConstants.D_MOTOR)
             .withMotionProfile(MOTOR_ACCELERATION, MOTOR_CRUISE_VELOCITY)
             .inverted(true)
             .build();
        rearLeftMotorPosition.setDesiredPosition(convertFromInchesToEncoderCounts(desiredPosition.getLeftInches()));
        
        frontRightMotorPosition = new MotorPositionController.Builder(frontLeftMotor) 
                .withGains(MotorConstants.F_MOTOR, MotorConstants.P_MOTOR, 
                        MotorConstants.I_MOTOR, MotorConstants.D_MOTOR)
             .withMotionProfile(MOTOR_ACCELERATION, MOTOR_CRUISE_VELOCITY)
             .build();
        frontRightMotorPosition.setDesiredPosition(convertFromInchesToEncoderCounts(desiredPosition.getRightInches()));
        
        rearRightMotorPosition  = new MotorPositionController.Builder(frontLeftMotor) 
                .withGains(MotorConstants.F_MOTOR, MotorConstants.P_MOTOR, 
                        MotorConstants.I_MOTOR, MotorConstants.D_MOTOR)
             .withMotionProfile(MOTOR_ACCELERATION, MOTOR_CRUISE_VELOCITY)
             .build();
        rearRightMotorPosition.setDesiredPosition(convertFromInchesToEncoderCounts(desiredPosition.getRightInches()));
	}
	
	protected void setSpeedMode( ){
	    frontLeftMotor.set(ControlMode.Velocity, 0.);
	    frontRightMotor.set(ControlMode.Velocity, 0.);
	    rearLeftMotor.set(ControlMode.Velocity,0.);
	    rearRightMotor.set(ControlMode.Velocity, 0.);    	    
	}

	protected static int convertFromInchesToEncoderCounts(double inches ){
	    return (int) ( ENCODER_CLICKS_PER_INCH * inches);
	}


	public synchronized void stop() {
	    setSpeedMode();
		drive.tankDrive(0., 0.);
	}

	public synchronized void arcadeDrive(double forw, double turn) {
	    setSpeedMode();
		drive.arcadeDrive(-forw, turn, true);
	}

	public synchronized void tankDrive(double left, double right) {
	    setSpeedMode();
		drive.tankDrive(left, right, true);
	}

    @Override
    public synchronized void drive(Position desiredPosition) {
        initPositionControllers(desiredPosition);
    }

    @Override
    public synchronized Position getCurrentPosition() {
        //use the front motors as the position
        if ( frontLeftMotorPosition != null && frontRightMotorPosition != null  ){
            return new Position( 
                    frontLeftMotorPosition.getSensorPosition(),
                    frontRightMotorPosition.getSensorPosition()
                    );        
        }
        else{
            return new Position(0.0,0.0);
        }
    }

    @Override
    public synchronized void resetPosition() {
        if ( frontLeftMotorPosition != null ){
            frontLeftMotorPosition.zeroSensorPosition();
        }
        if (rearLeftMotorPosition != null  ){
            rearLeftMotorPosition.zeroSensorPosition();
        }
        if ( frontRightMotorPosition != null ){
            frontRightMotorPosition.zeroSensorPosition();
        }
        if ( rearRightMotorPosition != null ){
            rearRightMotorPosition.zeroSensorPosition();
        }                
    }

}
