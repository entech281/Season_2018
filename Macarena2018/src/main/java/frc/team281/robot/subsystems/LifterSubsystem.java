package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.team281.robot.RobotMap;
import frc.team281.robot.talons.TalonSpeedController;

public class LifterSubsystem extends BaseSubsystem {

	private TalonSpeedController motorOneController;
	private TalonSpeedController motorTwoController;

	public static final double FULL_SPEED_PERCENT = 100;
	public static final double HOMING_SPEED_PERCENT = 20;

	public static final double MIN_HEIGHT_INCHES = 1.0;
	public static final double MAX_HEIGHT_INCHES = 100;

	private DigitalInput limitSwitch;

	public LifterSubsystem() {

	}

	@Override
	public void initialize() {
		WPI_TalonSRX motorOne = new WPI_TalonSRX(RobotMap.CAN.Lifter.MOTOR_ONE);
		WPI_TalonSRX motorTwo = new WPI_TalonSRX(RobotMap.CAN.Lifter.MOTOR_TWO);
		limitSwitch = new DigitalInput(RobotMap.DigitalIO.LIFTER_AT_BOTTOM);

		motorOneController = TalonSpeedController.defaults(motorOne).withCurrentLimits(10, 5, 200).coastInNeutral()
				.withDirections(false, false).noMotorOutputLimits().build();

		motorTwoController = TalonSpeedController.defaults(motorTwo).withCurrentLimits(10, 5, 200).coastInNeutral()
				.withDirections(false, true).noMotorOutputLimits().build();

	}

	public void motorsUp(double speedPercent) {
		motorOneController.setSpeed(speedPercent);
		motorTwoController.setSpeed(-speedPercent);

	}

	public void motorsDown(double speedPercent) {
		motorOneController.setSpeed(-speedPercent);
		motorTwoController.setSpeed(speedPercent);
	}

	public void motorsOff() {
		motorOneController.setSpeed(0);
		motorTwoController.setSpeed(0);
	}

	@Override
	public void periodic() {
		// if ( limitSwitch.get() ){
		// motorsOff();
		// }
	}

	public boolean isLifterAtBottom() {
		return limitSwitch.get();
	}

}
