package frc.team281.robot.logger;

import edu.wpi.first.wpilibj.Timer;

public class ConsoleDataLogger implements DataLogger {

  protected double  startTime = Timer.getFPGATimestamp();
  
  protected double elapsedSeconds() {
    return Timer.getFPGATimestamp() - startTime;
  }

  
  //TODO: use java.util.logging instead
  @Override
  public void logMessage(String key, String value) {
    System.out.printf("[ %.3f ] - %s::%s\n", elapsedSeconds(), key, value);
    
  }

  @Override
  public void logDouble(String key, double value) {
    System.out.printf("[ %.3f ] - %s::%.3f\n", elapsedSeconds(),key, value);
    
  }


	@Override
	public void logInt(String key, int value) {
		System.out.printf("[ %.3f ] - %s::%d\n", elapsedSeconds(),key, value);
		
	}

}
