package frc.team281.robot.commands;


import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class EndPathCaptureCommand extends BaseCommand {

    public static final double TIMEOUT_SEC = 0.1;
    
    private RealDriveSubsystem drive;
    public EndPathCaptureCommand(RealDriveSubsystem subsystem) {
        super(subsystem,TIMEOUT_SEC); 
        this.drive = subsystem;
    }

    @Override
    protected void initialize() {
        drive.endPathCapture();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
