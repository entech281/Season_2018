package frc.team281.robot.subsystems.drive;


// see https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionProfile/src/org/usfirst/frc/team217/robot/MotionProfileExample.java

import frc.team281.robot.trajectory.TwoWheelTrajectory;

import java.util.Iterator;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team281.robot.RobotMap;
import frc.team281.robot.trajectory.TrajectoryLogger;
import frc.team281.robot.trajectory.TrajectoryPlanner;

public class MotionPathDriveController extends BaseDriveController {

    private List<TwoWheelTrajectory> trajectoryPoints;
    private Iterator<TwoWheelTrajectory> trajectoryPointsIterator;
    
    private FourTalonsWithSettings talons;

    public static final int INITIAL_POINT_COUNT = 10;
    public static final int LOOP_MILLIS = 20;
    public static final int PID_SLOT = 0 ;
    
	private TrajectoryPlanner planner = new TrajectoryPlanner();
	private TrajectoryLogger trajectoryLogger;
	private String pathName = null;
	private int pointsCompleted = 0;
	public enum Status {
	    NO_PATH,
	    CAPTURING,
	    READY,
	    RUNNING_PATH
	}
	private Status status = Status.NO_PATH;
	
	public Status getStatus(){
	    return status;
	}

   public MotionPathDriveController(FourTalonsWithSettings talons){
        this.talons = talons;
    }
	
    private void configureTalons(){
        
        //TODO: this sucks-- we need to use the newer code
        //this manually works around the bad yertle encoder
        //need the new DriveEncoderStatus object to work around that more nicely.
        talons.frontLeftSettings.controlMode = ControlMode.MotionProfile;
        talons.rearLeftSettings.controlMode = ControlMode.MotionProfile;
        talons.frontRightSettings.controlMode = ControlMode.MotionProfile;
        talons.rearRightSettings.controlMode = ControlMode.MotionProfile;
        
        talons.frontLeftSettings.framePeriods.pidMilliseconds = 8;
        talons.rearLeftSettings.framePeriods.pidMilliseconds = 8;
        talons.frontRightSettings.framePeriods.pidMilliseconds = 8;
        talons.rearRightSettings.framePeriods.pidMilliseconds = 8;
        
        talons.configureAll();   
        
        talons.frontLeft.clearMotionProfileTrajectories();
        talons.frontRight.clearMotionProfileTrajectories();
        talons.rearLeft.clearMotionProfileTrajectories();
        talons.rearRight.clearMotionProfileTrajectories();
        
        talons.frontLeft.set(ControlMode.MotionProfile,0);
        talons.frontRight.set(ControlMode.MotionProfile,0);
        talons.rearLeft.set(ControlMode.MotionProfile,0);
        talons.rearRight.set(ControlMode.MotionProfile,0);
        
        talons.rearLeft.set(ControlMode.Follower, RobotMap.CAN.FRONT_LEFT_MOTOR);
    }
    
	public void startPathCapture(String fileName){
	    status = Status.CAPTURING;
	    pathName = fileName;
	    trajectoryLogger = planner.startLogging(fileName);
	    trajectoryLogger.init();
	}
	
	public void endPathCapture(){
	    status = Status.READY;
	    trajectoryLogger.close();
	}    
    
 
    
    protected int getNumPathPoints(){
        if ( status == Status.READY || status == Status.RUNNING_PATH){
            return trajectoryPoints.size();
        }
        else{
            return -1;
        }
    }
    
    protected void pushPoint(FourTalonsWithSettings talons, TwoWheelTrajectory t) {
    	talons.frontLeft.pushMotionProfileTrajectory(t.left);
    	talons.rearLeft.pushMotionProfileTrajectory(t.left);
    	talons.frontRight.pushMotionProfileTrajectory(t.right);
    	talons.rearRight.pushMotionProfileTrajectory(t.right);
    }
    
    @Override
    public void activate() {        

        if ( status == Status.READY){
            status = Status.RUNNING_PATH;
            pointsCompleted = 0;
            configureTalons();
            trajectoryPoints = planner.readLoggedPoints().asList();
            trajectoryPointsIterator = trajectoryPoints.iterator();
               
                        
            //push a few points
            for (int i=0;i<INITIAL_POINT_COUNT;i++) {
                pushPoint(talons,trajectoryPointsIterator.next());
                pointsCompleted++;
            }
            
        }
        else{
            dataLogger.warn("Cannot Activate in State" + status);
        }        
    }

    public void captureTrajectoryPoint(){
        trajectoryLogger.logTrajectoryPoint(
                talons.frontLeft.getSelectedSensorPosition(PID_SLOT), 
                talons.frontLeft.getSelectedSensorVelocity(PID_SLOT), 
                talons.frontRight.getSelectedSensorPosition(PID_SLOT), 
                talons.frontRight.getSelectedSensorVelocity(PID_SLOT), 
                LOOP_MILLIS);        
    }
    
    @Override
    public void periodic() {
        dataLogger.log("Status",status+"");
        dataLogger.log("PathName",pathName);
        dataLogger.log("TotalPts",getNumPathPoints());
        dataLogger.log("PtsLoaded",pointsCompleted);
        
        if ( status == Status.RUNNING_PATH){
            //push one profile point
            if ( trajectoryPointsIterator.hasNext()){
                pointsCompleted++;
                pushPoint(talons,trajectoryPointsIterator.next());
            }
            else{
                status = Status.READY;
            }            
        }

    }

    @Override
    public void deactivate() {
        //nothing to do
    }

}
