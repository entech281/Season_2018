package frc.team281.robot.logger;

public interface DataLogger {

  void logMessage (String key, String value);
  void logDouble ( String key, double value);
  void logInt ( String key, int value);
}
