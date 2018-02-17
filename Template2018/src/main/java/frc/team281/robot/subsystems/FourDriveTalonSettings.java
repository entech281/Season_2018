package frc.team281.robot.subsystems;

/**
 * A collection of Talon Settings for 4 wheels, organized by left and right.
 * 
 * @author dcowden
 *
 */
public class FourDriveTalonSettings {

	private TalonSettings leftFront;
	private TalonSettings leftRear;
	private TalonSettings rightFront;
	private TalonSettings rightRear;

	public FourDriveTalonSettings(TalonSettings baseSettings) {
		this.leftFront = baseSettings.copy();
		this.leftRear = baseSettings.copy();
		this.rightFront = baseSettings.copy();
		this.rightRear = baseSettings.copy();
	}

	public FourDriveTalonSettings(TalonSettings leftSettings, TalonSettings rightSettings) {
		this.leftFront = leftSettings.copy();
		this.leftRear = leftSettings.copy();
		this.rightFront = rightSettings.copy();
		this.rightRear = rightSettings.copy();
	}

	public FourDriveTalonSettings(TalonSettings frontLeft, TalonSettings rearLeft, TalonSettings frontRight,
			TalonSettings rearRight) {
		this.leftFront = frontLeft.copy();
		this.leftRear = rearLeft.copy();
		this.rightFront = frontRight.copy();
		this.rightRear = rearRight.copy();
	}

	public TalonSettings getLeftFront() {
		return leftFront;
	}

	public TalonSettings getLeftRear() {
		return leftRear;
	}

	public TalonSettings getRightFront() {
		return rightFront;
	}

	public TalonSettings getRightRear() {
		return rightRear;
	}

}
