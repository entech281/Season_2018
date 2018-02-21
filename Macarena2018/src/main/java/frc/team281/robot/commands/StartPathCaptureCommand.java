package frc.team281.robot.commands;

import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class StartPathCaptureCommand extends BaseCommand{

    public static final double TIMEOUT_SEC = 0.1;
    
    private RealDriveSubsystem drive;
    private String pathName;
    
    public StartPathCaptureCommand(RealDriveSubsystem subsystem, String pathName) {
        super(subsystem);
        this.drive = subsystem;
        this.pathName = pathName;
    }

    @Override
    protected void initialize() {
        drive.startPathCapture(pathName);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
