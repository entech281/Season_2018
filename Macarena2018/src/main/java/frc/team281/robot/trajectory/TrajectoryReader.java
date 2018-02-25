package frc.team281.robot.trajectory;

import java.util.Iterator;
import java.util.List;

public interface TrajectoryReader extends  Iterable<TwoWheelTrajectory>{
    public List<TwoWheelTrajectory> asList();
}
