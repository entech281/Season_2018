package frc.team281.robot.commands;

import frc.team281.robot.Robot;
import frc.team281.robot.subsystems.DriveSubsystem;

public class DotMoveCommand extends BaseCommandGroup {
    private double x;
    private double y;
    private DriveFowardXFeet d;

    public DotMoveCommand(DriveSubsystem drive, double x, double y) {
        this.x = x;
        this.y = y;
        this.d = new DriveFowardXFeet(drive, Math.sqrt(Math.pow(this.x - Robot.x, 2) + Math.pow(this.y - Robot.y, 2)));
        requires(drive);
        addSequential(new TurnToThetaRadians(drive, Math.atan2(this.y - Robot.y, this.x - Robot.x)));
        addSequential(this.d);
    }

    @Override
    protected boolean isFinished() {
        return d.isFinished();
    }
}
