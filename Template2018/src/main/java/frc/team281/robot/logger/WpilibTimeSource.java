package frc.team281.robot.logger;

import edu.wpi.first.wpilibj.Timer;

public class WpilibTimeSource implements TimeSource{

	@Override
	public double getSystemTime() {
		return Timer.getFPGATimestamp();
	}

}
