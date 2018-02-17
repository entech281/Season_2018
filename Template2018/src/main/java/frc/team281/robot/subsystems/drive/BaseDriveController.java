package frc.team281.robot.subsystems.drive;

import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;

/**
 * A driveController is a sub-portion of the drive subsystem.
 * 
 * @author dcowden
 *
 */
public abstract class BaseDriveController {

	protected DataLogger dataLogger;
	protected String name;

	public BaseDriveController() {
		this.dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger(getClass().getName());
		this.name = getClass().getName();
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Called when the controller is enabled
	 */
	public abstract void initialize();

	/**
	 * Called per loop
	 */
	public abstract void periodic();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDriveController other = (BaseDriveController) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
