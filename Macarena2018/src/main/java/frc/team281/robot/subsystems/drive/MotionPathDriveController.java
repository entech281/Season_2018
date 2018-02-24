package frc.team281.robot.subsystems.drive;

import frc.team281.robot.trajectory.TwoWheelTrajectory;

import java.util.Iterator;

import frc.team281.robot.controllers.TalonGroup;
import frc.team281.robot.trajectory.TrajectoryLogger;
import frc.team281.robot.trajectory.TrajectoryPlanner;

public class MotionPathDriveController extends BaseDriveController {

    private Iterator<TwoWheelTrajectory> trajectoryPoints;
    private TalonGroup talons;

    public static final int INITIAL_POINT_COUNT = 10;
    
	private TrajectoryPlanner planner = new TrajectoryPlanner();
	private TrajectoryLogger trajectoryLogger;
	
	public void startPathCapture(String fileName){
	    trajectoryLogger = planner.startLogging(fileName);
	    trajectoryLogger.init();
	}
	
	public void endPathCapture(){
	    trajectoryLogger.close();
	}    
    
    public MotionPathDriveController(TalonGroup talons){
    	this.talons = talons;
    }
    
    
    protected void pushPoint(TalonGroup talons, TwoWheelTrajectory t) {
    	talons.leftFront.getTalon().pushMotionProfileTrajectory(t.left);
    	talons.leftRear.getTalon().pushMotionProfileTrajectory(t.left);
    	talons.rightFront.getTalon().pushMotionProfileTrajectory(t.right);
    	talons.rightRear.getTalon().pushMotionProfileTrajectory(t.right);
    }
    
    @Override
    public void activate() {        
    	//TODO: clean this up
        trajectoryPoints = planner.readLoggedPoints().asList().iterator();
        talons.configureAll();
        
        //push a few points
        for (int i=0;i<INITIAL_POINT_COUNT;i++) {
        	pushPoint(talons,trajectoryPoints.next());
        }
    }

    @Override
    public void periodic() {
        //push one profile point
    	pushPoint(talons,trajectoryPoints.next());

    }

    @Override
    public void deactivate() {
        //nothing to do
    }

}
