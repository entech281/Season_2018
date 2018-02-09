package frc.team281.robot.commands;

import frc.team281.robot.Robot;
import frc.team281.robot.subsystems.DriveSubsystem;

public class TurnToThetaRadians extends BaseCommand {
	private DriveSubsystem drive;
	private double x;
	public TurnToThetaRadians(DriveSubsystem drive, double theta) {
		// Use requires() here to declare subsystem dependencies
		this.drive = drive;
		this.x=theta;
		requires(drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		drive.arcadeDrive(0, 1);
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
