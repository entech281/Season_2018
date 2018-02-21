package frc.team281.robot.subsystems.drive;

import frc.team281.robot.trajectory.TwoWheelTrajectory;

import java.util.List;

import frc.team281.robot.trajectory.TrajectoryLoggerFactory;

public class MotionPathDriveController extends BaseDriveController {

    private String pathName;
    private List<TwoWheelTrajectory> trajectoryPoints;
    
    public MotionPathDriveController(String pathName){
        this.pathName = pathName;
    }
    
    @Override
    public void activate() {        
        trajectoryPoints = new TrajectoryLoggerFactory().readLoggerOnRoboRio(pathName).asList();
    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deactivate() {
        //nothing to do
    }

}
