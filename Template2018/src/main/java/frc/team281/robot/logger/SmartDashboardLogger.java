package frc.team281.robot.logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardLogger implements DataLogger {

    @Override
    public void log(String key, Object value) {
	SmartDashboard.putString(key, value.toString());	
    }

    @Override
    public void log(String key, double value) {
	SmartDashboard.putNumber(key, value);	
    }

    @Override
    public void log(String key, int value) {
	SmartDashboard.putNumber(key, value);	
    }

    @Override
    public void log(String key, String value) {
	SmartDashboard.putString(key, value);	
    }

    @Override
    public void log(String key, long value) {
	SmartDashboard.putNumber(key, value);	
    }

    @Override
    public void log(String key, boolean value) {
	SmartDashboard.putBoolean(key, value);	
    }

}
