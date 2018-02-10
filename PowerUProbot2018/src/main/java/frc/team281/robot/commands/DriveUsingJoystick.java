package frc.team281.robot.commands;

import frc.team281.robot.Robot;
import frc.team281.robot.subsystems.DriveSubsystem;

public class DriveUsingJoystick extends BaseCommand {
    private DriveSubsystem drive;

    public DriveUsingJoystick(DriveSubsystem drive) {
        this.drive = drive;
        requires(drive);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        drive.arcadeDrive(Robot.oi.getDriveJoystickForward(), Robot.oi.getDriveJoystickLateral());
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
