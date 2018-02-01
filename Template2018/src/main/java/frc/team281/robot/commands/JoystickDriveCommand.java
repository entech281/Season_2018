package frc.team281.robot.commands;

import frc.team281.robot.DriveInstruction;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.subsystems.BaseDriveSubsystem;

public class JoystickDriveCommand extends BaseCommand {

	private BaseDriveSubsystem drive;
	private DriveInstructionSource driveInstructionSource;

	public JoystickDriveCommand(BaseDriveSubsystem drive, DataLogger logger,
			DriveInstructionSource driveInstructionSource) {
		super(drive, UNLIMITED_TIMEOUT, logger);
		this.drive = drive;
		this.driveInstructionSource = driveInstructionSource;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		DriveInstruction di = driveInstructionSource.getNextInstruction();
		drive.arcadeDrive(di.getForward(), di.getLateral());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
