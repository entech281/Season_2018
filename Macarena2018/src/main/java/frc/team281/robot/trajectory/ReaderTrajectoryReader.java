package frc.team281.robot.trajectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.ctre.phoenix.motion.TrajectoryPoint;

//see https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionProfile/src/org/usfirst/frc/team217/robot/MotionProfileExample.java
//http://www.ctr-electronics.com/downloads/api/java/html/index.html
public class ReaderTrajectoryReader implements TrajectoryReader {

    public static final int PROFILE_SLOT = 0;
    private BufferedReader source;
    private List<TwoWheelTrajectory> trajectories = new ArrayList<>();
    
    public ReaderTrajectoryReader(BufferedReader source) {
        this.source = source;
        readAllLines(); //reading into memory is probably ok for now, and id' rather use memory than read disk each loop
    }
    
    protected void readAllLines(){
        try {
            String line;
            while ((line = source.readLine()) != null) {
              TwoWheelTrajectory newPoint = parseTrajectoryPoint(line);
              if ( newPoint != null ){
                  trajectories.add(newPoint);
              }
            }            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }        
        
        //set the last point as the last one
        TwoWheelTrajectory lastOne = trajectories.get(trajectories.size()-1);
        lastOne.left.isLastPoint=true;
        lastOne.right.isLastPoint=true;
    }
    
    public List<TwoWheelTrajectory> asList(){
        return this.trajectories;
    }
    @Override
    public Iterator<TwoWheelTrajectory> iterator() {
        return this.trajectories.iterator();
    }
    
    protected TrajectoryPoint makeTrajectoryPoint(double pos, double velocity) {
        TrajectoryPoint tp = new TrajectoryPoint();
        tp.profileSlotSelect = PROFILE_SLOT;
        tp.isLastPoint = false;
        tp.zeroPos = false;
        tp.position = pos;
        tp.velocity = velocity;
        //why cant i set tp.timeDur ? 
        return tp;
    }

    protected TwoWheelTrajectory parseTrajectoryPoint(String data) {

        List<String> items = Arrays.asList(data.split("\\s*,\\s*"));
        if ( items.size() != 5 ){
            return null;
        }
        long leftPos = Long.parseLong(items.get(0));
        long leftVel = Long.parseLong(items.get(1));
        long rightPos = Long.parseLong(items.get(2));
        long rightVel = Long.parseLong(items.get(3));

        return new TwoWheelTrajectory(makeTrajectoryPoint(leftPos, leftVel), makeTrajectoryPoint(rightPos, rightVel));

    }




}
