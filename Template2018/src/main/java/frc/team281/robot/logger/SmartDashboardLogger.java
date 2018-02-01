package frc.team281.robot.logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardLogger extends DataLogger {


	@Override
    public void log(String key, Object value) {
    	SmartDashboard.putString(computePath(key), value.toString());	
    }

    @Override
    public void log(String key, double value) {
    	SmartDashboard.putNumber(computePath(key), value);	
    }

    @Override
    public void log(String key, int value) {
    	SmartDashboard.putNumber(computePath(key), value);	
    }

    @Override
    public void log(String key, String value) {
    	SmartDashboard.putString(computePath(key), value);	
    }

    @Override
    public void log(String key, long value) {
    	SmartDashboard.putNumber(computePath(key), value);	
    }

    @Override
    public void log(String key, boolean value) {
	SmartDashboard.putBoolean(computePath(key), value);	
    }

}
