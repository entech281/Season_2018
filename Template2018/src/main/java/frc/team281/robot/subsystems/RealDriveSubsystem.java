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
	private TalonPositionController frontLeftMotorPosition;
	private TalonPositionController rearLeftMotorPosition;
	private TalonPositionController frontRightMotorPosition;
	private TalonPositionController rearRightMotorPosition;

	
	//have to hold on to these to change control modes.
    private WPI_TalonSRX frontLeftMotor;
    private WPI_TalonSRX frontRightMotor;
    private WPI_TalonSRX rearLeftMotor;
    private WPI_TalonSRX rearRightMotor;	
	
	//estimated based on 6" diameter wheels, with 80 counts per turn, gear ratio 14/52 * 14/52 13.8:1
	public static final double ENCODER_CLICKS_PER_INCH = 40.0833;
	    
    //units are counts/sec
    //max possible speed:
    //estimated at 3200 rev/min * 80 counts/rev * 1 min / 60 sec = 4266 counts/sec 
	//note that this speed is not affected by the gear ratio!
    //75% of that is 3200 counts/sec
    public static final int MOTOR_CRUISE_VELOCITY = 4500;

    //lets get to full speed in 1 second
    //not affected by gear ratio
    public static final int MOTOR_ACCELERATION = 4500;
    public static final int PID_SLOT = 0;
    
    // recommended-- start with all gains but P, and work from there
    // then double the gain until oscillations occur
    // then add d gain at 10 to 100x P
    // finally, add I = 0.01xP
	public static class MotorConstants{
	    public static final double I_MOTOR = 0.0;
	    public static final double P_MOTOR = 0.1;
	    public static final double D_MOTOR = 0.0;
	    public static final double F_MOTOR = 0.0;	    
	}	

	public RealDriveSubsystem(DriveInstructionSource driveInstructionSource) {
		super(driveInstructionSource);
	}

	@Override
	public void initialize() {
	
	    this.frontLeftMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
	    this.frontRightMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);
	    this.rearLeftMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
	    this.rearRightMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);

	    //set up for speed control
		drive = new DifferentialDrive(new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
				new SpeedControllerGroup(frontRightMotor, rearRightMotor));
		
		//so that we wont time out the drive when we're in position mode
		drive.setSafetyEnabled(false);        

	}
	
	/**
	 * 
	 * This method tests that the encoders are working by 
	 * driving forward a bit, and then making sure we read the encoders
	 */
	public void testEncodersAreWorking(){
		
		//TODO: drive forward a bit
		
		int encoderLeftFront = frontLeftMotor.getSelectedSensorPosition(PID_SLOT);
		int encoderLeftRear = rearLeftMotor.getSelectedSensorPosition(PID_SLOT);
		int encoderRightFront = frontRightMotor.getSelectedSensorPosition(PID_SLOT);
		int encoderRightRear = rearRightMotor.getSelectedSensorPosition(PID_SLOT);
		
		//configure based on what we see.
		//if we dont have good encoders on either side, we're hosed.
		EncoderCheck check = new EncoderCheck(encoderLeftRear,  encoderLeftFront, encoderRightFront, encoderRightRear);
		
		TalonSettings frontLeftSettings =  TalonSettingsBuilder.defaults()
				.withCurrentLimits(35, 30, 200)
				.coastInNeutral()
				.withDirections(false, false)
				.limitMotorOutputs(0.5, 0.01)
				.noMotorStartupRamping()
				.usePositionControl()
				.withGains(MotorConstants.F_MOTOR, MotorConstants.P_MOTOR, MotorConstants.I_MOTOR, MotorConstants.D_MOTOR)
				.withMotionProfile(MOTOR_CRUISE_VELOCITY, MOTOR_ACCELERATION)
				.build();

		TalonSettings frontRightSettings =  TalonSettingsBuilder.inverted(frontLeftSettings, false, true);
		TalonSettings rearLeftSettings = TalonSettingsBuilder.copy(frontLeftSettings);
		TalonSettings rearRightSettings = TalonSettingsBuilder.copy(frontRightSettings);
		
		//if we have problems but
		if ( check.hasProblems() ){
			if ( check.canDrive() ) {
				if ( check.hasLeftProblems()) {
					if ( check.isLeftFrontOk()) {
						rearLeftSettings = TalonSettingsBuilder.follow(frontLeftSettings, RobotMap.CAN.FRONT_LEFT_MOTOR);
					}
					//left rear is ok
					else {
						frontLeftSettings = TalonSettingsBuilder.follow(rearLeftSettings, RobotMap.CAN.FRONT_RIGHT_MOTOR);
					}
				}
				if ( check.hasRightProblems()) {
					if ( check.isRightFrontOk()) {
						rearRightSettings = TalonSettingsBuilder.follow(frontRightSettings, RobotMap.CAN.FRONT_RIGHT_MOTOR);
					}
					//left rear is ok
					else {
						frontRightSettings = TalonSettingsBuilder.follow(rearRightSettings, RobotMap.CAN.FRONT_RIGHT_MOTOR);
					}
				}
			}
			//cant drive-- because we have broken encoders on one side
			//disable all motors
			else {
				frontLeftSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
				frontRightSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
				rearLeftSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
				rearRightSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
			}
		}
		
        frontLeftMotorPosition = new TalonPositionController(frontLeftMotor,frontLeftSettings);
        rearLeftMotorPosition = new TalonPositionController(rearLeftMotor,frontRightSettings);
        frontRightMotorPosition = new TalonPositionController(frontRightMotor,rearLeftSettings);
        rearRightMotorPosition = new TalonPositionController(rearRightMotor,rearRightSettings);		
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
	protected static double convertFromEncoderCountsToInches(int encoderCounts){
	    return (double)encoderCounts / (double)ENCODER_CLICKS_PER_INCH;
	}

	public void stop() {
	    setSpeedMode();
		drive.tankDrive(0., 0.);
	}

	public void arcadeDrive(double forw, double turn) {
	    setSpeedMode();
		drive.arcadeDrive(-forw, turn, true);
	}

	public void tankDrive(double left, double right) {
	    setSpeedMode();
		drive.tankDrive(left, right, true);
	}

    @Override
    public void drive(Position desiredPosition) {
        int encoderCountsLeft = convertFromInchesToEncoderCounts(desiredPosition.getLeftInches());
        int encoderCountsRight = convertFromInchesToEncoderCounts(desiredPosition.getRightInches());
        
        frontLeftMotorPosition.setDesiredPosition(encoderCountsLeft);
        rearLeftMotorPosition.setDesiredPosition(encoderCountsLeft);
        frontRightMotorPosition.setDesiredPosition(encoderCountsRight);
        rearRightMotorPosition.setDesiredPosition(encoderCountsRight);        
    }

    @Override
    public Position getCurrentPosition() {
        double frontLeftInches = convertFromEncoderCountsToInches(frontLeftMotorPosition.getActualPosition());
        double rearLeftInches = convertFromEncoderCountsToInches(rearLeftMotorPosition.getActualPosition());
        double frontRightInches = convertFromEncoderCountsToInches(frontRightMotorPosition.getActualPosition());
        double rearRightInches = convertFromEncoderCountsToInches(rearRightMotorPosition.getActualPosition());
        
        double avgLeftInches = (frontLeftInches + rearLeftInches) / 2.0 ;
        double avgRightInches = (frontRightInches + rearRightInches) / 2.0 ;
        //use the average 
        return new Position(avgLeftInches,avgRightInches);    

    }

}
