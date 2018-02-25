package frc.team281.robot.trajectory;

import com.ctre.phoenix.motion.TrajectoryPoint;

public class TwoWheelTrajectory {

    public TwoWheelTrajectory(TrajectoryPoint left,TrajectoryPoint right){
        this.left = left;
        this.right = right;
    }
    
    public TrajectoryPoint left;
    public TrajectoryPoint right;
}
