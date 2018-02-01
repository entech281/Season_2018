package frc.team281.robot.subsystems;

import frc.team281.robot.logger.DataLogger;

public abstract class BaseLifterSubsystem extends BaseSubsystem{

	public static final double LIFTER_TIMEOUT = 2.0;
	public BaseLifterSubsystem(DataLogger dataLogger) {
		super(dataLogger);
	}
	
	public abstract void raise();
	public abstract void lower();
	public abstract boolean isAtTop();
	public abstract boolean isAtBottom();

}
