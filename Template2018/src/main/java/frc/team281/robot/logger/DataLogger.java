package frc.team281.robot.logger;

public abstract class DataLogger {

	public final String SEPARATOR = ".";
	private String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String computePath(String key) {
		return getName() + SEPARATOR + key;
	}

	public abstract void log(String key, Object value);

	public abstract void log(String key, double value);

	public abstract void log(String key, int value);

	public abstract void log(String key, String value);

	public abstract void log(String key, long value);

	public abstract void log(String key, boolean value);
	
	public static DataLogger realMatchConfiguration() {
		return new CompositeLogger ( 
				new SmartDashboardLogger(), 
				new ConsoleDataLogger(new WpilibTimeSource())
				);
	}
	public static DataLogger testingConfiguration() {
		return new ConsoleDataLogger(new WpilibTimeSource());
	}

}
