package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.subsystems.DriveSubsystem;
/**
 *
 */
public class SetAndHoldYaw extends Command {
	private DriveSubsystem m_drive;
	private double m_angle;
	public SetAndHoldYaw(DriveSubsystem drive, double angle) {
		// Use requires() here to declare subsystem dependencies
		m_drive = drive;
		m_angle = angle;
		requires(drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		m_drive.setHoldYawAngle(m_angle);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		m_drive.tankDrive(0.0, 0.0);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return m_drive.isAtAngle();
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
