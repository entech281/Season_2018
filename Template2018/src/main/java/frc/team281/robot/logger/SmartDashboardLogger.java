package frc.team281.robot.logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardLogger implements DataLogger {

  @Override
  public void logMessage(String key, String message) {
    SmartDashboard.putString(key, message);
  }

  @Override
  public void logDouble(String key, double value) {
    SmartDashboard.putNumber(key, value);
  }

	@Override
	public void logInt(String key, int value) {
		SmartDashboard.putNumber(key, value);	
	}

}
