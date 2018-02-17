package frc.team281.robot.subsystems.drive;

import frc.team281.robot.subsystems.BaseSubsystem;
import frc.team281.robot.subsystems.PositionBuffer;

/**
 * Common base class for the drive subsystem. This contains stuff that's the
 * same for both real operation and for testing. abstract methods should be
 * implemented for real robot use, and for testing.
 * 
 * Note that this class still doesnt use any wpilib stuff!
 * 
 * 
 * @author dcowden
 *
 */
public abstract class BaseDriveSubsystem extends BaseSubsystem {

	public enum DriveMode {
		DISABLED, CALIBRATE, READY, SPEED_DRIVE, POSITION_DRIVE
	}

	protected DriveMode driveMode = DriveMode.DISABLED;
	protected BaseDriveController currentController;
	protected PositionBuffer positionBuffer = new PositionBuffer();

	public void runController(BaseDriveController newController) {

		if (!newController.equals(currentController)) {
			dataLogger.warn("Switching Controllers: " + currentController + "-->" + newController);
			newController.initialize();

		}
		dataLogger.log("CurrentController", currentController);
		newController.periodic();

	}

	public PositionBuffer getPositionBuffer() {
		return positionBuffer;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

}
