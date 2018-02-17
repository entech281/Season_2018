package frc.team281.robot.subsystems.drive;

import frc.team281.robot.RobotMap;
import frc.team281.robot.controllers.EncoderCheck;
import frc.team281.robot.controllers.TalonSpeedController;
import frc.team281.robot.subsystems.TalonSettingsBuilder;

/**
 * Given four CAN Talons and some desired settings, gives a controller group
 * that will control the position, even if some of the encoders are broken
 * 
 * @author dcowden
 *
 */
public class FourDriveTalonCalibratorController extends BaseDriveController {

	private MotorEncoderTester tester;
	private long calibrationTimeMs = 0;
	private long startTimeMillis = 0;
	private FourTalonsWithSettings talons;

	public FourDriveTalonCalibratorController(FourTalonsWithSettings talons, long calibrationTimeMs) {
		this.talons = talons;
		this.calibrationTimeMs = calibrationTimeMs;
	}

	@Override
	public void initialize() {
		
		talons.configureAll();
		
		tester = new MotorEncoderTester(new TalonSpeedController(talons.getFrontLeft(), talons.getFrontLeftSettings()),
				new TalonSpeedController(talons.getRearLeft(), talons.getRearLeftSettings()),
				new TalonSpeedController(talons.getFrontRight(), talons.getFrontRightSettings()),
				new TalonSpeedController(talons.getRearRight(), talons.getRearRightSettings()), calibrationTimeMs);

		tester.startTest();

		startTimeMillis = System.currentTimeMillis();

	}

	protected void finishCalibration() {

		EncoderCheck checker = tester.finishTest();

		dataLogger.log("Calibration Finished", true);
		dataLogger.log("LeftFrontMotorGood", checker.isLeftFrontOk());
		dataLogger.log("LeftRearMotorGood", checker.isLeftRearOk());
		dataLogger.log("RightFrontMotorGood", checker.isRightFrontOk());
		dataLogger.log("RightRearMotorGood", checker.isRightRearOk());

		if (checker.shouldDisableAll()) {
			talons.disableAllSettings();
		} else {
			if (checker.shouldLeftFrontFollowLeftRear()) {
				talons.setFrontLeftSettings(
						TalonSettingsBuilder.follow(talons.getRearLeftSettings(), RobotMap.CAN.FRONT_RIGHT_MOTOR));
			}
			if (checker.shouldLeftRearFollowLeftFront()) {
				talons.setRearLeftSettings(
						TalonSettingsBuilder.follow(talons.getFrontLeftSettings(), RobotMap.CAN.FRONT_LEFT_MOTOR));
			}
			if (checker.shouldRightFrontFollowRightRear()) {
				talons.setFrontRightSettings(
						TalonSettingsBuilder.follow(talons.getRearRightSettings(), RobotMap.CAN.FRONT_RIGHT_MOTOR));
			}
			if (checker.shouldRightRearFollowRightFront()) {
				talons.setRearRightSettings(
						TalonSettingsBuilder.follow(talons.getFrontRightSettings(), RobotMap.CAN.FRONT_RIGHT_MOTOR));

			}
		}

	}

	public boolean isCalibrationReady() {
		long currentTime = System.currentTimeMillis();
		return currentTime > (startTimeMillis + this.calibrationTimeMs);
	}

	@Override
	public void periodic() {
		boolean calibrated = isCalibrationReady();
		dataLogger.log("CalibrationComplete", calibrated);
		if (isCalibrationReady()) {
			finishCalibration();
		}

	}

}
