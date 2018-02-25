package frc.team281.robot.commands.path;

import frc.team281.robot.commands.BaseCommand;
import frc.team281.robot.subsystems.drive.BaseDriveSubsystem.DriveMode;
import frc.team281.robot.subsystems.drive.MotionPathDriveController;
import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class StartCapturePathCommand extends BaseCommand {

    public static final int TIMEOUT = 1000;
    
    private RealDriveSubsystem drive;
    private MotionPathDriveController pathController;
    private String captureName;
    
    //TODO: this reveals a problem with base Command-- here we really just want to inject
    //a MotionPathDriveController, not the whole subsystem
    
    public StartCapturePathCommand(RealDriveSubsystem subsystem, String captureName) {
        super(subsystem, TIMEOUT);
        this.drive = subsystem;
        this.captureName = captureName;
        this.pathController = this.drive.getMotionPathController();
    }

    
    @Override
    protected void initialize() {
        pathController.startPathCapture(captureName);
        drive.setMode(DriveMode.PATH_CAPTURE);        
    }


    @Override
    protected boolean isFinished() {
        return pathController.getStatus() == MotionPathDriveController.Status.CAPTURING;
    }

}
