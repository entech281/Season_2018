package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.Robot;
import frc.team281.robot.subsystems.DriveSubsystem;

/**
 *
 */
public class Autonomous extends Command {
	private DriveSubsystem drive;
	public Autonomous(DriveSubsystem drive) {
		// Use requires() here to declare subsystem dependencies
		this.drive = drive;
		requires(drive);
		setTimeout(2.5);
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		drive.arcadeDrive(.5,0);
			}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
