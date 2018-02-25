package frc.team281.robot.trajectory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DoNothingTrajectoryReader implements TrajectoryReader {

    private List<TwoWheelTrajectory> t = new ArrayList<>();
    
    @Override
    public Iterator<TwoWheelTrajectory> iterator() {
        return t.iterator();
    }

    @Override
    public List<TwoWheelTrajectory> asList() {
        return t;
    }

}
