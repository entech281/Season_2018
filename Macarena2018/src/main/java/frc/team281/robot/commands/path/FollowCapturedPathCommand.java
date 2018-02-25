package frc.team281.robot.commands.path;

import frc.team281.robot.commands.BaseCommand;
import frc.team281.robot.subsystems.drive.BaseDriveSubsystem.DriveMode;
import frc.team281.robot.subsystems.drive.MotionPathDriveController;
import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class FollowCapturedPathCommand extends BaseCommand{
    public static final int TIMEOUT = 1000;
    
    private RealDriveSubsystem drive;
    private MotionPathDriveController pathController;
    private String captureName;
    
    public FollowCapturedPathCommand(RealDriveSubsystem subsystem, String captureName) {
        super(subsystem, TIMEOUT);
        this.drive = subsystem;
        this.captureName = captureName;
        this.pathController = this.drive.getMotionPathController();
    }

    
    @Override
    protected void initialize() {
        drive.setMode(DriveMode.PATH_DRIVE);
        pathController.activate();
    }


    @Override
    protected void end() {
        this.drive.setMode(DriveMode.SPEED_DRIVE);
    }


    @Override
    protected boolean isFinished() {
        return pathController.getStatus() == MotionPathDriveController.Status.READY;
    }
}
