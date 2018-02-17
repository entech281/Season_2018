package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team281.robot.Robot;
import frc.team281.robot.subsystems.DriveSubsystem;
import frc.team281.robot.subsystems.ShooterOutTakeSubsystem;;
/**
 *
 */
public class Autonomous extends CommandGroup{
	private DriveSubsystem drive;
	private ShooterOutTakeSubsystem shooter;
	public Autonomous(DriveSubsystem drive) {
		// Use requires() here to declare subsystem dependencies
		this.drive = drive;
		requires(drive);
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		setTimeout(5);
		drive.arcadeDrive(1,0);
		shooter.shooterOutAbove();
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
