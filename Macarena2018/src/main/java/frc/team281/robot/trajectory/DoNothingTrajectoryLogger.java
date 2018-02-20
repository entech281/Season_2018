package frc.team281.robot.trajectory;


public class DoNothingTrajectoryLogger implements TrajectoryLogger{

    public DoNothingTrajectoryLogger() {

    }

    @Override
    public void init() {
    }

    @Override
    public void logTrajectoryPoint(int leftEncoderClicks, int leftEncoderClicksPer100ms, int rightEncoderClicks,
            int rightEncoderClicksPer100ms, int millisAtThisPoint) {

    }

}
