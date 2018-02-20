package frc.team281.robot.trajectory;

public interface TrajectoryLogger {
    public init();
    public void logTrajectoryPoint (int leftEncoderClicks, int leftEncoderClicksPer100ms, 
            int rightEncoderClicks, int rightEncoderClicksPer100ms,int millisAtThisPoint );
}
