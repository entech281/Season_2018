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
		DISABLED, SPEED_DRIVE, POSITION_DRIVE
	}

	protected DriveMode driveMode = DriveMode.DISABLED;
	protected DriveComponent currentComponent = new DoNothingDriveComponent();	
	protected PositionBuffer positionBuffer = new PositionBuffer();
	protected boolean calibrated = false;
	
	
	/**
	 * Changes modes, and returns true if the change worked, false if it didnt
	 * @param newDriveMode
	 * @return
	 */
	public boolean setMode ( DriveMode newDriveMode) {
		if ( newDriveMode == driveMode) {
			dataLogger.warn("Stayed in mode " + newDriveMode );
			return false;
		}
		
		driveMode = newDriveMode;
		dataLogger.warn("Drive Mode Changed:" + driveMode + "-->" + newDriveMode);
		return true;
		
	}
	
	public void activate(DriveComponent newComponent) {
	    if ( newComponent == null ){
	        dataLogger.warn("Trying to run null component! Use doNothing controller, never use null!");
	        return;
	    }

	    if (!newComponent.equals(currentComponent)) {		    
			dataLogger.warn("Switching Controllers: " +  currentComponent.getName() + "-->" + newComponent.getName());
			currentComponent.deactivate();	
			newComponent.activate();
			currentComponent = newComponent;
		}
		
		dataLogger.log("CurrentComponent", currentComponent.getName());

	}
	
	public PositionBuffer getPositionBuffer() {
		return positionBuffer;
	}

	@Override
	public void initialize() {
	}

}
