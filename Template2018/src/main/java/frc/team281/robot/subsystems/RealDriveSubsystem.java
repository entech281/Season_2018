package frc.team281.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.RobotMap;

/**
 * This is the drive system that will run in the robot. All the wpilib stuff
 * goes here.
 * 
 * @author dcowden
 *
 */
public class RealDriveSubsystem extends BaseDriveSubsystem {

	private WPI_TalonSRX frontLeftMotor;
	private WPI_TalonSRX frontRightMotor;
	private WPI_TalonSRX rearLeftMotor;
	private WPI_TalonSRX rearRightMotor;
	private DifferentialDrive drive;
	
	private boolean positionDrivingMode = false;
	
	public static final double ENCODER_CLICKS_PER_INCH = 1.0;
	public static final double K_MOTOR = 0.0;
	public static final double I_MOTOR = 0.0;
	public static final double P_MOTOR = 0.1;
	public static final double D_MOTOR = 0.0;
	public static final double F_MOTOR = 0.0;
	public static final int FIRST_MOTOR_INDEX = 0;
	public static final int MOTOR_POSITION_TIMEOUT_MILLIS=20000;
	public static final int MOTOR_ACCELERATION=1;
	public static final int MOTOR_CRUISE_VELOCITY=1;
	
	public RealDriveSubsystem(DriveInstructionSource driveInstructionSource) {
		super(driveInstructionSource);
	}

	@Override
	public synchronized void initialize() {
	    	//CANTalon ct = new CANTalon(RobotMap.CAN.FRONT_LEFT_MOTOR);
	    	
		frontLeftMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
		frontRightMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);
		rearLeftMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
		rearRightMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);

		drive = new DifferentialDrive(new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
				new SpeedControllerGroup(frontRightMotor, rearRightMotor));
		
		//frontLeftMotor.getSensorCollection().getQuadraturePosition();
		
	}

	private void setPID(WPI_TalonSRX talon,  double F, double P, double I, double D , int timeoutMillis){

	    talon.config_kD(FIRST_MOTOR_INDEX, D, timeoutMillis);
	    talon.config_kI(FIRST_MOTOR_INDEX, I, timeoutMillis);
	    talon.config_kP(FIRST_MOTOR_INDEX, P, timeoutMillis);
	    talon.config_kF(FIRST_MOTOR_INDEX, F, timeoutMillis);
	}
	
	public synchronized void driveDistance(double leftDistanceInches, double rightDistanceInches ){
	    //ref https://www.chiefdelphi.com/forums/showthread.php?p=1633629
	    //motionMagic is basically a trade name for a trapezoidal motion profile	 
	    //a java example : https://github.com/CrossTheRoadElec/FRC-Examples-STEAMWORKS/tree/master/JAVA_MotionMagicExample/src/org/usfirst/frc/team217/robot
	    //another example of closed loop position
	    //https://github.com/CrossTheRoadElec/FRC-Examples-STEAMWORKS/blob/master/JAVA_PositionClosedLoop/src/org/usfirst/frc/team469/robot/Robot.java
	    positionDrivingMode = true;
	    double leftEncoderClicks = leftDistanceInches * ENCODER_CLICKS_PER_INCH;
	    double rightEncoderClicks = rightDistanceInches * ENCODER_CLICKS_PER_INCH;
	    
	    setPID(frontLeftMotor,F_MOTOR,P_MOTOR,I_MOTOR,D_MOTOR,MOTOR_POSITION_TIMEOUT_MILLIS);
	    setPID(rearLeftMotor,F_MOTOR,P_MOTOR,I_MOTOR,D_MOTOR,MOTOR_POSITION_TIMEOUT_MILLIS);
	    setPID(frontRightMotor,F_MOTOR,P_MOTOR,I_MOTOR,D_MOTOR,MOTOR_POSITION_TIMEOUT_MILLIS);
	    setPID(rearRightMotor,F_MOTOR,P_MOTOR,I_MOTOR,D_MOTOR,MOTOR_POSITION_TIMEOUT_MILLIS);
	    
	    frontLeftMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
	    frontLeftMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);

	    rearLeftMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
	    rearLeftMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);
	    
	    frontRightMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
	    frontRightMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);
	    
	    rearRightMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
	    rearRightMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);	    
	    
	    frontLeftMotor.set(ControlMode.MotionMagic,leftEncoderClicks);
	    rearLeftMotor.set(ControlMode.MotionMagic,leftEncoderClicks);
	    frontRightMotor.set(ControlMode.MotionMagic,rightEncoderClicks);
	    rearRightMotor.set(ControlMode.MotionMagic,rightEncoderClicks);	    
	    
	}
	public synchronized void stop() {
	    if ( ! positionDrivingMode ){
		drive.tankDrive(0., 0.);
	    }
		
	}

	public synchronized void arcadeDrive(double forw, double turn) {
	    if ( ! positionDrivingMode){
		drive.arcadeDrive(-forw, turn, true);
	    }
		
	}

	public synchronized void tankDrive(double left, double right) {
	    if ( ! positionDrivingMode){
		drive.tankDrive(left, right, true);
	    }
	}

}
