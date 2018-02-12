package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.RobotMap;

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

	public static final int MOTOR_CRUISE_VELOCITY = 1200;
	public static final int MOTOR_ACCELERATION = 800;
	public static final double I_GAIN = 0.0;
	public static final double P_GAIN = 0.1;
	public static final double D_GAIN = 0.0;
	public static final double F_GAIN = 0.0;

	// for speed control
	private DifferentialDrive drive;

	// for position control
	private TalonPositionController frontLeftMotorPosition;
	private TalonPositionController rearLeftMotorPosition;
	private TalonPositionController frontRightMotorPosition;
	private TalonPositionController rearRightMotorPosition;

	// have to hold on to these to change control modes.
	private WPI_TalonSRX frontLeftMotor;
	private WPI_TalonSRX frontRightMotor;
	private WPI_TalonSRX rearLeftMotor;
	private WPI_TalonSRX rearRightMotor;

	private TalonSettings leftTalonSettings;
	private TalonSettings rightTalonSettings;

	// estimated based on 6" diameter wheels, with 80 counts per turn, gear ratio
	// 14/52 * 14/52 13.8:1
	private EncoderInchesConverter encoderConverter = new EncoderInchesConverter(46.0);

	public enum DriveMode {
		SPEED, POSITION, DISABLED
	}

	private DriveMode driveMode = DriveMode.DISABLED;

	public RealDriveSubsystem(DriveInstructionSource driveInstructionSource) {
		super(driveInstructionSource);
	}

	@Override
	public void initialize() {

		this.frontLeftMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
		this.frontRightMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);
		this.rearLeftMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
		this.rearRightMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);

		this.leftTalonSettings = TalonSettingsBuilder.defaults().withCurrentLimits(35, 30, 200).coastInNeutral()
				.withDirections(false, false).limitMotorOutputs(0.5, 0.01).noMotorStartupRamping().usePositionControl()
				.withGains(F_GAIN, P_GAIN, I_GAIN, D_GAIN).withMotionProfile(MOTOR_CRUISE_VELOCITY, MOTOR_ACCELERATION)
				.build();

		this.rightTalonSettings = TalonSettingsBuilder.inverted(leftTalonSettings, false, true);

	}

	/**
	 * 
	 * This method tests that the encoders are working by driving forward a bit, and
	 * then making sure we read the encoders
	 */
	public void testEncoderMotionForPositionDrive() {

		TalonSpeedController frontLeft = new TalonSpeedController(frontLeftMotor, leftTalonSettings);
		TalonSpeedController frontRight = new TalonSpeedController(frontRightMotor, rightTalonSettings);
		TalonSpeedController rearLeft = new TalonSpeedController(rearLeftMotor, leftTalonSettings);
		TalonSpeedController rearRight = new TalonSpeedController(rearRightMotor, rightTalonSettings);

		MotorEncoderTester tester = new MotorEncoderTester(frontLeft, rearLeft, frontRight, rearRight);

		EncoderCheck check = tester.testMotors();

		// unless we detect problems, left and right settings match
		TalonSettings frontLeftSettings = TalonSettingsBuilder.copy(leftTalonSettings);
		TalonSettings frontRightSettings = TalonSettingsBuilder.copy(rightTalonSettings);
		TalonSettings rearLeftSettings = TalonSettingsBuilder.copy(leftTalonSettings);
		TalonSettings rearRightSettings = TalonSettingsBuilder.copy(rightTalonSettings);

		// if we have problems but
		if (check.hasProblems()) {
			if (check.canDrive()) {
				if (check.hasLeftProblems()) {
					if (check.isLeftFrontOk()) {
						rearLeftSettings = TalonSettingsBuilder.follow(frontLeftSettings,
								RobotMap.CAN.FRONT_LEFT_MOTOR);
					}
					// left rear is ok
					else {
						frontLeftSettings = TalonSettingsBuilder.follow(rearLeftSettings,
								RobotMap.CAN.FRONT_RIGHT_MOTOR);
					}
				}
				if (check.hasRightProblems()) {
					if (check.isRightFrontOk()) {
						rearRightSettings = TalonSettingsBuilder.follow(frontRightSettings,
								RobotMap.CAN.FRONT_RIGHT_MOTOR);
					}
					// left rear is ok
					else {
						frontRightSettings = TalonSettingsBuilder.follow(rearRightSettings,
								RobotMap.CAN.FRONT_RIGHT_MOTOR);
					}
				}
			}
			// cant drive-- because we have broken encoders on one side
			// disable all motors
			else {
				frontLeftSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
				frontRightSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
				rearLeftSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
				rearRightSettings = TalonSettingsBuilder.disabledCopy(rearLeftSettings);
			}
		}

		frontLeftMotorPosition = new TalonPositionController(frontLeftMotor, frontLeftSettings);
		rearLeftMotorPosition = new TalonPositionController(rearLeftMotor, frontRightSettings);
		frontRightMotorPosition = new TalonPositionController(frontRightMotor, rearLeftSettings);
		rearRightMotorPosition = new TalonPositionController(rearRightMotor, rearRightSettings);
	}

	protected void enableSpeedModeIfNeeded() {
		if (!driveMode.equals(DriveMode.SPEED)) {
			// set up for speed control
			drive = new DifferentialDrive(new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
					new SpeedControllerGroup(frontRightMotor, rearRightMotor));

		}
	}

	protected void enablePositionModeIfNeeded() {
		if (!driveMode.equals(DriveMode.POSITION)) {
			frontLeftMotorPosition.resetMode();
			rearLeftMotorPosition.resetMode();
			frontRightMotorPosition.resetMode();
			rearRightMotorPosition.resetMode();
		}
	}

	public void stop() {
		drive.tankDrive(0., 0.);
	}

	public void arcadeDrive(double forw, double turn) {
		enableSpeedModeIfNeeded();
		drive.arcadeDrive(-forw, turn, true);
	}

	public void tankDrive(double left, double right) {
		enableSpeedModeIfNeeded();
		drive.tankDrive(left, right, true);
	}

	@Override
	public void drive(Position desiredPosition) {
		enablePositionModeIfNeeded();
		int encoderCountsLeft = encoderConverter.toCounts(desiredPosition.getLeftInches());
		int encoderCountsRight = encoderConverter.toCounts(desiredPosition.getRightInches());

		frontLeftMotorPosition.setDesiredPosition(encoderCountsLeft);
		rearLeftMotorPosition.setDesiredPosition(encoderCountsLeft);
		frontRightMotorPosition.setDesiredPosition(encoderCountsRight);
		rearRightMotorPosition.setDesiredPosition(encoderCountsRight);
	}

	@Override
	public Position getCurrentPosition() {
		double frontLeftInches = encoderConverter.toInches(frontLeftMotorPosition.getActualPosition());
		double rearLeftInches = encoderConverter.toInches(rearLeftMotorPosition.getActualPosition());
		double frontRightInches = encoderConverter.toInches(frontRightMotorPosition.getActualPosition());
		double rearRightInches = encoderConverter.toInches(rearRightMotorPosition.getActualPosition());

		double avgLeftInches = (frontLeftInches + rearLeftInches) / 2.0;
		double avgRightInches = (frontRightInches + rearRightInches) / 2.0;
		// use the average
		return new Position(avgLeftInches, avgRightInches);

	}

}
