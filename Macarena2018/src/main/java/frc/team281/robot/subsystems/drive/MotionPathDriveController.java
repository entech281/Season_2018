package frc.team281.robot.subsystems.drive;

import frc.team281.robot.trajectory.TwoWheelTrajectory;

import java.util.Iterator;

import frc.team281.robot.trajectory.TrajectoryLogger;
import frc.team281.robot.trajectory.TrajectoryPlanner;

public class MotionPathDriveController extends BaseDriveController {

    private Iterator<TwoWheelTrajectory> trajectoryPoints;
    private FourTalonsWithSettings talons;

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
    
    public MotionPathDriveController(FourTalonsWithSettings talons){
    	this.talons = talons;
    }
    
    
    protected void pushPoint(FourTalonsWithSettings talons, TwoWheelTrajectory t) {
    	talons.frontLeft.pushMotionProfileTrajectory(t.left);
    	talons.rearLeft.pushMotionProfileTrajectory(t.left);
    	talons.frontRight.pushMotionProfileTrajectory(t.right);
    	talons.rearRight.pushMotionProfileTrajectory(t.right);
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
