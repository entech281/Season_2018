package frc.team281.robot.logger;

public interface DataLogger {
    
  void log(String key, Object value);
  void log(String key, double value);
  void log(String key, int value);
  void log(String key, String value);
  void log(String key, long value);
  void log(String key, boolean value);
}
