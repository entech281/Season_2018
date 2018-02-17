package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.RobotMap;
import frc.team281.robot.controllers.TalonControllerGroup;
import frc.team281.robot.subsystems.DriveSystemMode.StateResult;

import com.kauailabs.navx.frc.*;

/**
 * This is the drive system that will run in the robot. All the wpilib stuff
 * goes here.
 *
 * ref https://www.chiefdelphi.com/forums/showthread.php?p=1633629 motionMagic
 * is basically a trade name for a trapezoidal motion profile a java example :
 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java/MotionMagic/src/org/usfirst/frc/team217/robot
 * ctre api doc
 * http://www.ctr-electronics.com/downloads/api/java/html/index.html software
 * manual
 * https://github.com/CrossTheRoadElec/Phoenix-Documentation/raw/master/Talon%20SRX%20Victor%20SPX%20-%20Software%20Reference%20Manual.pdf
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

    // recommended value per Software Reference manual
    // 10ms for calls during init, and 0ms for calls during periodic loop
    public static final int MOTOR_POSITION_TIMEOUT_MILLIS = 10;
    public static final int MOTOR_ACCELERATION = 1;
    public static final int MOTOR_CRUISE_VELOCITY = 1;

    public RealDriveSubsystem(DriveInstructionSource driveInstructionSource) {
        super(driveInstructionSource);
    }

    @Override
    public synchronized void initialize() {

        frontLeftMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
        frontRightMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);
        rearLeftMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
        rearRightMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);

        TalonSRX talon = (TalonSRX) frontLeftMotor;

        drive = new DifferentialDrive(new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
                new SpeedControllerGroup(frontRightMotor, rearRightMotor));

        // frontLeftMotor.getSensorCollection().getQuadraturePosition();

        /* CTRE Magnetic Encoder relative, same as Quadrature */
        // talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
        // 0, 0); /* PIDLoop=0, timeoutMs=0 */
        /*
         * CTRE Magnetic Encoder absolute (within one rotation), same as
         * PulseWidthEncodedPosition
         */
        // talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,
        // 0, 0); /* PIDLoop=0, timeoutMs=0 */

        /* quadrature */

        // talon.configPeakCurrentLimit(35, 10); /* 35 A */
        // talon.configPeakCurrentDuration(200, 10); /* 200ms */
        // talon.configContinuousCurrentLimit(30, 10); /* 30A */
        // talon.enableCurrentLimit(true); /* turn it on */
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /* PIDLoop=0, timeoutMs=0 */
    }

    // recommended-- start with all gains but P, and work from there
    // then double the gain until oscillations occur
    // then add d gain at 10 to 100x P
    // finally, add I = 0.01xP
    private void setPID(WPI_TalonSRX talon, double F, double P, double I, double D, int timeoutMillis) {

        talon.config_kD(FIRST_MOTOR_INDEX, D, timeoutMillis);
        talon.config_kI(FIRST_MOTOR_INDEX, I, timeoutMillis);
        talon.config_kP(FIRST_MOTOR_INDEX, P, timeoutMillis);
        talon.config_kF(FIRST_MOTOR_INDEX, F, timeoutMillis);
    }

    public synchronized void driveDistance(double leftDistanceInches, double rightDistanceInches) {
        // ref https://www.chiefdelphi.com/forums/showthread.php?p=1633629
        // motionMagic is basically a trade name for a trapezoidal motion profile
        // a java example :
        // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java/MotionMagic/src/org/usfirst/frc/team217/robot
        // ctre api doc
        // http://www.ctr-electronics.com/downloads/api/java/html/index.html
        // software manual
        // https://github.com/CrossTheRoadElec/Phoenix-Documentation/raw/master/Talon%20SRX%20Victor%20SPX%20-%20Software%20Reference%20Manual.pdf

        positionDrivingMode = true;
        double leftEncoderClicks = leftDistanceInches * ENCODER_CLICKS_PER_INCH;
        double rightEncoderClicks = rightDistanceInches * ENCODER_CLICKS_PER_INCH;

        setPID(frontLeftMotor, F_MOTOR, P_MOTOR, I_MOTOR, D_MOTOR, MOTOR_POSITION_TIMEOUT_MILLIS);
        setPID(rearLeftMotor, F_MOTOR, P_MOTOR, I_MOTOR, D_MOTOR, MOTOR_POSITION_TIMEOUT_MILLIS);
        setPID(frontRightMotor, F_MOTOR, P_MOTOR, I_MOTOR, D_MOTOR, MOTOR_POSITION_TIMEOUT_MILLIS);
        setPID(rearRightMotor, F_MOTOR, P_MOTOR, I_MOTOR, D_MOTOR, MOTOR_POSITION_TIMEOUT_MILLIS);

        frontLeftMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
        frontLeftMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);

        rearLeftMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
        rearLeftMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);

        frontRightMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
        frontRightMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);

        rearRightMotor.configMotionAcceleration(MOTOR_ACCELERATION, MOTOR_POSITION_TIMEOUT_MILLIS);
        rearRightMotor.configMotionCruiseVelocity(MOTOR_CRUISE_VELOCITY, MOTOR_POSITION_TIMEOUT_MILLIS);

        frontLeftMotor.set(ControlMode.MotionMagic, leftEncoderClicks);
        rearLeftMotor.set(ControlMode.MotionMagic, leftEncoderClicks);
        frontRightMotor.set(ControlMode.MotionMagic, rightEncoderClicks);
        rearRightMotor.set(ControlMode.MotionMagic, rightEncoderClicks);

    }



    


	public static final int NAVX_CALIBRATION_LOOP_TIME_MS = 50;  // in ms
	public static final double I_GAIN = 0.0;
	public static final double P_GAIN = 0.1;
	public static final double D_GAIN = 0.0;
	public static final double F_GAIN = 0.0;

	// for speed control


	// for position control
	private TalonControllerGroup positionControllerGroup;
	private FourWheelTankTalonCalibrator calibrator = new FourWheelTankTalonCalibrator();

	// have to hold on to these to change control modes.
	

	private TalonSettings leftTalonSettings;
	private TalonSettings rightTalonSettings;

	// estimated based on 6" diameter wheels, with 80 counts per turn, gear ratio
	// 14/52 * 14/52 13.8:1
	private EncoderInchesConverter encoderConverter = new EncoderInchesConverter(46.0);

    private AHRS navX;

	



	/**
	 *
	 * This method tests that the encoders are working by driving forward a bit, and
	 * then making sure we read the encoders
	 */
	public void startCalibration() {
		StateResult r = driveMode.enterCalibrate();
		if ( r == StateResult.ENTERED) {
			calibrator.startCalibration( frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor,
					leftTalonSettings, rightTalonSettings);
		}
	}

	public void finishCalibration() {
		this.positionControllerGroup = calibrator.finishCalibration();
		driveMode.finishCalibrating();
	}

	protected boolean enterSpeedMode() {
		StateResult r = driveMode.enterSpeed();
		if ( r == StateResult.REJECTED) {
			return false;
		}
		if ( r == StateResult.ENTERED) {
			drive = new DifferentialDrive(
					new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
					new SpeedControllerGroup(frontRightMotor, rearRightMotor));
		}		
		return true;

	}

	protected boolean enterPositionMode() {
		StateResult r = driveMode.enterPosition();
		if ( r == StateResult.REJECTED) {
			return false;
		}
		if ( r == StateResult.ENTERED) {
			positionControllerGroup.resetMode();
		}
		return true;
	}

	public void stop() {
		if ( enterSpeedMode() ) {
			drive.tankDrive(0., 0.);
		}
	}

	public void arcadeDrive(double forw, double turn) {
		if ( enterSpeedMode() ) {
			drive.arcadeDrive(-forw, turn, true);
		}
	}

	public void tankDrive(double left, double right) {
		if ( enterSpeedMode() ) {
			drive.tankDrive(left, right, true);
		}
	}

	@Override
	public void periodic() {
        // method called by scheduler automatically
		if (this.navX != null) {
            dataLogger.log("NavX: ", this.navX);
            dataLogger.log("NavX Yaw Angle: ", this.navX.getYaw());
		}
	}

	@Override
	public void drive(Position desiredPosition) {
		if ( enterPositionMode() ) {
			int encoderCountsLeft = encoderConverter.toCounts(desiredPosition.getLeftInches());
			int encoderCountsRight = encoderConverter.toCounts(desiredPosition.getRightInches());
			positionControllerGroup.setDesiredPosition(encoderCountsLeft, encoderCountsRight);	
			dataLogger.log("DesiredPositionLeft", encoderCountsLeft);
			dataLogger.log("DesiredPositionRight",encoderCountsRight );
		}

	}

	@Override
	public Position getCurrentPosition() {

		int leftEncoderCount = positionControllerGroup.computeLeftEncoderCounts();
		int rightEncoderCount = positionControllerGroup.computeRightEncoderCounts();

		double leftInches = encoderConverter.toInches(leftEncoderCount);
		double rightInches = encoderConverter.toInches(rightEncoderCount);

		// use the average
		return new Position(leftInches, rightInches);

	}

}
