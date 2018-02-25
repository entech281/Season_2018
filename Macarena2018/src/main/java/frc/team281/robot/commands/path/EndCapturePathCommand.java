package frc.team281.robot.commands.path;

import frc.team281.robot.commands.BaseCommand;
import frc.team281.robot.subsystems.drive.BaseDriveSubsystem.DriveMode;
import frc.team281.robot.subsystems.drive.MotionPathDriveController;
import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class EndCapturePathCommand extends BaseCommand {

    public static final int TIMEOUT = 1000;
    
    private RealDriveSubsystem drive;
    private MotionPathDriveController pathController;
    
    public EndCapturePathCommand(RealDriveSubsystem subsystem) {
        super(subsystem, TIMEOUT);
        this.drive = subsystem;
        this.pathController = this.drive.getMotionPathController();
    }
    
    @Override
    protected void initialize() {
        drive.setMode(DriveMode.SPEED_DRIVE);
        pathController.endPathCapture();
    }


    @Override
    protected boolean isFinished() {
        return pathController.getStatus() == MotionPathDriveController.Status.READY;
    }

}
