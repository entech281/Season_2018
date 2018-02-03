package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.Robot;
import frc.team281.robot.subsystems.DriveSubsystem;

/**
 *
 */
public class DisableHoldYaw extends Command {
	private DriveSubsystem m_drive;
	public DisableHoldYaw(DriveSubsystem drive) {
		// Use requires() here to declare subsystem dependencies
		m_drive = drive;
		requires(drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		m_drive.disableHoldYaw();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
