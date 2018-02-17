package frc.team281.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.RobotMap;
import frc.team281.robot.subsystems.NavXIntializer;
import frc.team281.robot.subsystems.TalonSettings;
import frc.team281.robot.subsystems.TalonSettingsBuilder;

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
	public static final int CALIBRATION_DRIVE_TIME_MS = 200;
	public static final double ENCODER_TICKS_PER_INCH = 43.0;

	//protected FourTalonGroup talons;
	private AHRS navX;

	private FourDriveTalonCalibratorController calibrator;
	private BasicArcadeDriveController arcadeDrive;
	private PositionDriveController positionDrive;
	private DriveInstructionSource driveInstructionSource;
	
	public RealDriveSubsystem(DriveInstructionSource driveInstructionSource) {
		this.driveInstructionSource = driveInstructionSource;
	}

	@Override
	public void initialize() {

		this.navX = new NavXIntializer(SerialPort.Port.kMXP,NAVX_CALIBRATION_LOOP_TIME_MS).getCalibratedNavX();	
		
		/**
		 * These are the intial (base) settings for the talons
		 * Sub-controllers may change them as needed to do their work!
		 */
		TalonSettings leftSpeedSettings = TalonSettingsBuilder.defaults()
				.withCurrentLimits(35, 30, 200)
				.coastInNeutral()
				.withDirections(true, false)
				.limitMotorOutputs(0.5, 0.01)
				.noMotorStartupRamping()
				.useSpeedControl()
				.build();

		TalonSettings rightSpeedSettings = TalonSettingsBuilder.inverted(leftSpeedSettings, false, true);

		FourTalonsWithSettings talonsInSpeedMode = new FourTalonsWithSettings(
				new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR),
				new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR), 
				new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR),
				new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR));
		
		talonsInSpeedMode.applySettings(leftSpeedSettings, rightSpeedSettings);

		TalonSettings leftPositionSettings = TalonSettingsBuilder.defaults()
				.withCurrentLimits(35, 30, 200)
				.coastInNeutral()
				.withDirections(true, false)
				.limitMotorOutputs(0.5, 0.01)
				.noMotorStartupRamping()
				.usePositionControl()
				.withGains(0.3,0.2, 0.0, 0.0)
				.withMotionProfile(1200, 1200)
				.build();

		TalonSettings rightPositionSettings = TalonSettingsBuilder.inverted(leftPositionSettings, false, true);
		
		FourTalonsWithSettings talonsInPositionMode = new FourTalonsWithSettings(
				new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR),
				new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR), 
				new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR),
				new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR));
		
		talonsInPositionMode.applySettings(leftPositionSettings, rightPositionSettings);
		
		arcadeDrive = new BasicArcadeDriveController(talonsInSpeedMode, driveInstructionSource);
		positionDrive = new PositionDriveController(talonsInPositionMode, getPositionBuffer(), new EncoderInchesConverter(ENCODER_TICKS_PER_INCH));
		calibrator = new FourDriveTalonCalibratorController(talonsInPositionMode, CALIBRATION_DRIVE_TIME_MS);
		
		
	}

	@Override
	public void periodic() {
		dataLogger.log("DriveMode", driveMode + "");
		if (driveMode == DriveMode.CALIBRATE) {
			runController(calibrator);
			if (calibrator.isCalibrationReady()) {
				driveMode = DriveMode.READY;
			}
		} else if (driveMode == DriveMode.POSITION_DRIVE) {
			runController(positionDrive);
		} else if (driveMode == DriveMode.SPEED_DRIVE) {
			runController(arcadeDrive);
		} else {
			// do nothing
		}
	}

}