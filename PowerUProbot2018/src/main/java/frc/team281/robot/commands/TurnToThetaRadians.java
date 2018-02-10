package frc.team281.robot.commands;

import frc.team281.robot.subsystems.DriveSubsystem;

public class TurnToThetaRadians extends BaseCommand {
    private DriveSubsystem drive;
    private double x;

    public TurnToThetaRadians(DriveSubsystem drive, double theta) {
        this.drive = drive;
        this.x = theta;
        requires(drive);
        setTimeout(x);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        drive.arcadeDrive(0, 1);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
