package frc.team281.robot.commands;

import java.util.List;

import frc.team281.robot.subsystems.Position;
import frc.team281.robot.subsystems.drive.BaseDriveSubsystem;
import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class FollowPositionPathCommand extends BaseCommand{

    private RealDriveSubsystem driveSubsystem;

    protected List<Position> path;
    public FollowPositionPathCommand(RealDriveSubsystem subsystem, List<Position> path) {
        super(subsystem);
        this.driveSubsystem = subsystem;
        this.path = path;
    }

    @Override
    protected void initialize() {
        for (Position p: path){
            driveSubsystem.getPositionSource().addPosition(p);
        }
        
    }
    @Override
    protected boolean isFinished() {
        return ! driveSubsystem.getPositionSource().hasNextPosition();
    }
    
}
