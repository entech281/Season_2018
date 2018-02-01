package frc.team281.robot.logger;

public class ConsoleDataLogger extends DataLogger {

	private TimeSource timer;
	public ConsoleDataLogger(TimeSource systemTimer) {
		this.timer = systemTimer;
	}
	protected double startTime = timer.getSystemTime();

	protected double elapsedSeconds() {
		return timer.getSystemTime()- startTime;
	}

	private void printMessage(String format, String key, Object value) {
		System.out.printf("[ %.3f ] - %s::" + format + "\n", elapsedSeconds(), computePath(key), value);
	}

	@Override
	public void log(String key, String value) {
		printMessage("%s", key, value);
	}

	@Override
	public void log(String key, Object value) {
		printMessage("%s", key, value.toString());
	}

	@Override
	public void log(String key, double value) {
		printMessage("%.3f", key, value);
	}

	@Override
	public void log(String key, int value) {
		printMessage("%d", key, value);
	}

	@Override
	public void log(String key, long value) {
		printMessage("%d", key, value);
	}

	@Override
	public void log(String key, boolean value) {
		printMessage("%s", key, Boolean.toString(value));
	}

}
