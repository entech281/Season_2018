package org.usfirst.frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team281.robot.Robot;

/**
 *
 */
public class DriveUsingJoystick extends Command {
	public DriveUsingJoystick() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot._driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot._driveSubsystem.arcadeDrive(Robot.oi.getDriveJoystickForward(), Robot.oi.getDriveJoystickLateral());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
