package frc.team281.robot.trajectory;

public interface TrajectoryLogger {
    public void init();
    public void pause();
    public void unpause();
    public void close();
    public void logTrajectoryPoint (int leftEncoderClicks, int leftEncoderClicksPer100ms, 
            int rightEncoderClicks, int rightEncoderClicksPer100ms,int millisAtThisPoint );
}
