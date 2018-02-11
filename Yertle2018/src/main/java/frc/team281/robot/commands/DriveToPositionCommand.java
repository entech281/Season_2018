package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
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
        requires(subsystem);
    }

    public void execute() {
        counter++;        
        SmartDashboard.putNumber("DriveToPosition",counter);
    }
    @Override
    protected void initialize() {
        driveSubsystem.drive(desiredPosition);
        SmartDashboard.putString("Init command 0", "Success0");
    }

    public void end() {
        driveSubsystem.setSpeedMode();
    }
    
    @Override
    protected boolean isFinished() {
        //SmartDashboard.putString("Got to place 1", "Success1");
        ///DriverStation.reportWarning("Got to place 1",false);
        Position currentPosition = driveSubsystem.getCurrentPosition();
        //SmartDashboard.putString("Got to place 2", "Success2");
        //DriverStation.reportWarning("Got to place 2",false);
        SmartDashboard.putNumber("PositionLeft", currentPosition.getLeftInches());
        //SmartDashboard.putString("Got to place 3", "Success3");
        ///DriverStation.reportWarning("Got to place 3",false);
        SmartDashboard.putNumber("PositionRight", currentPosition.getRightInches());
        ///SmartDashboard.putString("Got to place 4", "Success4");
        //DriverStation.reportWarning("Got to place 4",false);
        SmartDashboard.putNumber("DesiredPositionLeft", desiredPosition.getLeftInches());
        //SmartDashboard.putString("Got to place 5", "Success5");
        //DriverStation.reportWarning("Got to place 5",false);
        SmartDashboard.putNumber("DesiredPositionRight",desiredPosition.getRightInches());
        //SmartDashboard.putString("Got to place 6", "Success6");
        //DriverStation.reportWarning("Got to place 6",false);
        boolean isFinished = this.desiredPosition.isCloseTo(currentPosition, TOLERANCE);
        SmartDashboard.putBoolean("DriveCommandFinished", isFinished);
        return isFinished;
    }

}
