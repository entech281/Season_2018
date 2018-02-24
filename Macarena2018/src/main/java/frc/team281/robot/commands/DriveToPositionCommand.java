package frc.team281.robot.commands;

import frc.team281.robot.subsystems.Position;
import frc.team281.robot.subsystems.drive.BaseDriveSubsystem;
import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    private Position desiredPosition;
    private RealDriveSubsystem driveSubsystem;

    public static final double TOLERANCE = 1.0;

    public DriveToPositionCommand(RealDriveSubsystem subsystem, Position position) {
        super(subsystem);
        this.desiredPosition = position;
        this.driveSubsystem = subsystem;
    }

    @Override
    protected void initialize() {
        driveSubsystem.getPositionSource().addPosition(desiredPosition);
    }

    @Override
    protected boolean isFinished() {
        return ! driveSubsystem.getPositionSource().hasNextPosition();
    }

}
