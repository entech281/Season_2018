package frc.team281.robot.logger;

public class CompositeLogger extends DataLogger{

	private DataLogger[] loggers;
	public CompositeLogger ( DataLogger ... loggers ) {
		this.loggers = loggers;
	}
	@Override
	public void log(String key, Object value) {
		for(DataLogger l: loggers) {
			l.log(key, value);
		}		
	}

	@Override
	public void log(String key, double value) {
		for(DataLogger l: loggers) {
			l.log(key, value);
		}		
	}

	@Override
	public void log(String key, int value) {
		for(DataLogger l: loggers) {
			l.log(key, value);
		}		
	}

	@Override
	public void log(String key, String value) {
		for(DataLogger l: loggers) {
			l.log(key, value);
		}		
	}

	@Override
	public void log(String key, long value) {
		for(DataLogger l: loggers) {
			l.log(key, value);
		}
	}

	@Override
	public void log(String key, boolean value) {
		// TODO Auto-generated method stub
		
	}

}
