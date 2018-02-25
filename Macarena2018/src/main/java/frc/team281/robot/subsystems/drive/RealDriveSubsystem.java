package frc.team281.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import frc.team281.robot.DriveInstruction;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.RobotMap;
import frc.team281.robot.subsystems.NavXIntializer;
import frc.team281.robot.talons.DriveEncoderStatus;
import frc.team281.robot.talons.FourTalonGroup;

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

	public static final int NAVX_CALIBRATION_LOOP_TIME_MS = 50;
	public static final double ENCODER_TICKS_PER_INCH = 52.;

	public static final int POSITION_ENCODER_TOLERANCE = 25;
	public static final double POSITION_TOLERANCE_INCHES = (double) POSITION_ENCODER_TOLERANCE / ENCODER_TICKS_PER_INCH;

	private AHRS navX;

	private FourWheelSpeedDrive speedDrive;
	private FourWheelPositionDrive positionDrive;

	protected DoNothingDriveComponent doNothing = new DoNothingDriveComponent();
	protected FourTalonEncoderChecker encoderChecker;
	private DriveInstructionSource driveInstructionSource;
	protected DriveEncoderStatus driveEncoderStatus = new DriveEncoderStatus(
			new EncoderInchesConverter(ENCODER_TICKS_PER_INCH));

	private FourTalonGroup talons;

	public RealDriveSubsystem(DriveInstructionSource driveInstructionSource) {
		this.driveInstructionSource = driveInstructionSource;
	}

	@Override
	public void initialize() {

		this.navX = new NavXIntializer(SerialPort.Port.kMXP, NAVX_CALIBRATION_LOOP_TIME_MS).getCalibratedNavX();

		talons.frontLeft = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
		talons.frontRight = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);
		talons.rearLeft = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
		talons.rearRight = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);

		speedDrive = FourWheelSpeedDrive
				.defaults(talons)
				.withCurrentLimits(35, 30, 200)
				.coastInNeutral()
				.withDirections(false, true)
				.noMotorOutputLimits()
				.build();

		positionDrive = FourWheelPositionDrive.defaults(talons)
				.withCurrentLimits(35, 30, 200)
				.coastInNeutral()
				.withDirections(false, false)
				.limitMotorOutputs(1.0, 0.25)
				.noMotorStartupRamping()
				.withGains(0.3, 5.0, 0.0, 0.0)
				.withMotionProfile(150, 150, POSITION_ENCODER_TOLERANCE)
				.build();

		encoderChecker = new FourTalonEncoderChecker();
	}

	public DriveEncoderStatus getDriveEncoderStatus() {
		return driveEncoderStatus;
	}

	@Override
	public void periodic() {
		dataLogger.log("DriveMode", driveMode + "");

		displayControllerStatus(talons.frontLeft, "frontLeft");
		displayControllerStatus(talons.frontRight, "frontRight");
		displayControllerStatus(talons.rearLeft, "rearLeft");
		displayControllerStatus(talons.rearRight, "rearRight");

		driveEncoderStatus.updateStatusFromTalons(talons);
		encoderChecker.updateMotorsToWorkAroundBrokenEncodersIfEnabled(talons, driveEncoderStatus);

		if (driveMode == DriveMode.POSITION_DRIVE) {
			activate(positionDrive);
			positionDrive.updatePosition(getPositionBuffer(), driveEncoderStatus);

		} else if (driveMode == DriveMode.SPEED_DRIVE) {
			activate(speedDrive);
			DriveInstruction di = driveInstructionSource.getNextInstruction();
			speedDrive.arcadeDrive(di.getForward(), di.getLateral());
		} else {
			activate(doNothing);
		}
	}

	protected void displayControllerStatus(WPI_TalonSRX talon, String name) {
		dataLogger.log(name + "_errorMsg", talon.getLastError());
		dataLogger.log(name + "_get", talon.get());
		dataLogger.log(name + "_percent", talon.getMotorOutputPercent());
		dataLogger.log(name + "_pos", talon.getSelectedSensorPosition(0));
		dataLogger.log(name + "_vel", talon.getSelectedSensorVelocity(0));
	}

}
