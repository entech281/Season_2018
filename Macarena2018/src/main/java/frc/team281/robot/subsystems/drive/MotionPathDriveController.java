package frc.team281.robot.subsystems.drive;

import frc.team281.robot.trajectory.TwoWheelTrajectory;

import java.util.Iterator;
import java.util.List;

import frc.team281.robot.controllers.TalonPositionControllerGroup;
import frc.team281.robot.controllers.TalonWithSettings;
import frc.team281.robot.trajectory.TrajectoryLogger;
import frc.team281.robot.trajectory.TrajectoryLoggerFactory;

public class MotionPathDriveController extends BaseDriveController {

    private String pathName;
    private Iterator<TwoWheelTrajectory> trajectoryPoints;
    private TalonWithSettings talons;

    public static final int INITIAL_POINT_COUNT = 10;
    
	private TrajectoryLogger trajectoryLogger;
	
	
	public void startPathCapture(String pathName){
	    trajectoryLogger = new TrajectoryLoggerFactory().makeLoggerOnRoboRio(pathName);
	    trajectoryLogger.init();
	}
	
	public void endPathCapture(){
	    trajectoryLogger.close();
	}    
    
    public MotionPathDriveController(TalonWithSettings talons, String pathName){
    	this.talons = talons;
        this.pathName = pathName;
    }
    
    
    @Override
    public void activate() {        
    	//TODO: clean this up
        trajectoryPoints = new TrajectoryLoggerFactory().readLoggerOnRoboRio(pathName).asList().iterator();
        talons.configureAll();
        
        //push a few points
        for (int i=0;i<INITIAL_POINT_COUNT;i++) {

        }
    }

    @Override
    public void periodic() {
        //push one profile point
    	trajectoryPoints.

    }

    @Override
    public void deactivate() {
        //nothing to do
    }

}
