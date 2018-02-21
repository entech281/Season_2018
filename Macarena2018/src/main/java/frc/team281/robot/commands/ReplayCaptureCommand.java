package frc.team281.robot.commands;

import frc.team281.robot.subsystems.drive.RealDriveSubsystem;
import frc.team281.robot.subsystems.drive.BaseDriveSubsystem.DriveMode;

public class ReplayCaptureCommand extends BaseCommand {

    private RealDriveSubsystem drive;
    private String pathName;
    public ReplayCaptureCommand(RealDriveSubsystem subsystem, String pathName) {
        super(subsystem);
        this.drive = subsystem;
        this.pathName = pathName;
    }

    @Override
    protected void initialize() {
        drive.setMode(DriveMode.PATH_DRIVE);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

}
