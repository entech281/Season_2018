package frc.team281.robot.subsystems;

import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.commands.JoystickDriveCommand;
import frc.team281.robot.logger.DataLogger;

public abstract class BaseDriveSubsystem extends BaseSubsystem {

	private DriveInstructionSource driveInstructionSource;
	
	public BaseDriveSubsystem(DataLogger dataLogger, DriveInstructionSource driveInstructionSource) {
		super(dataLogger);
		this.driveInstructionSource = driveInstructionSource;
	}

	public abstract void stop();

	public abstract void arcadeDrive(double forw, double turn);

	public abstract void tankDrive(double left, double right);

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(  new JoystickDriveCommand(this,dataLogger, driveInstructionSource));
		
	}


}
