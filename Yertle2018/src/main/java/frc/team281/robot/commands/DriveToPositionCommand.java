package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team281.robot.subsystems.DriveSubsystem;
import frc.team281.robot.subsystems.Position;

public class DriveToPositionCommand extends Command {

    private Position desiredPosition;
    private DriveSubsystem driveSubsystem;

    public static final double TOLERANCE = 1.0;
    private int counter =0;
    public DriveToPositionCommand(DriveSubsystem subsystem, Position position) {
        this.desiredPosition = position;
        this.driveSubsystem = subsystem;
    }

    public void execute() {
        counter++;
        System.out.println("DriveToPosition::Execute");
        SmartDashboard.putNumber("DriveToPosition",counter);
    }
    @Override
    protected void initialize() {
        driveSubsystem.drive(desiredPosition);
    }

    @Override
    protected boolean isFinished() {
        Position currentPosition = driveSubsystem.getCurrentPosition();
        SmartDashboard.putNumber("PositionLeft", currentPosition.getLeftInches());
        SmartDashboard.putNumber("PositionRight", currentPosition.getRightInches());
        SmartDashboard.putNumber("DesiredPositionLeft", desiredPosition.getLeftInches());
        SmartDashboard.putNumber("DesiredPositionRight",desiredPosition.getRightInches());
        boolean isFinished = this.desiredPosition.isCloseTo(currentPosition, TOLERANCE);
        SmartDashboard.putBoolean("DriveCommandFinished", isFinished);
        return isFinished;
    }

}
