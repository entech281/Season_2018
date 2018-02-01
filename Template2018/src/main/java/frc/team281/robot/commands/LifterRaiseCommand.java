package frc.team281.robot.commands;

import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.subsystems.BaseLifterSubsystem;

public class LifterRaiseCommand extends BaseCommand {

	private BaseLifterSubsystem lifter;
	private boolean completed = false;
	public LifterRaiseCommand(BaseLifterSubsystem lifter, DataLogger dataLogger) {
		super(lifter, BaseLifterSubsystem.LIFTER_TIMEOUT, dataLogger);
		this.lifter = lifter;
	}

	@Override
	protected void execute() {
		
		if (! lifter.isAtTop()) {
			dataLogger.log("Execute", "Raise");
			lifter.raise();
		} 
		else{
			dataLogger.log("Execute", "Cannot Raise");
			completed = true;
		}
	}	
	
	@Override
	protected boolean isFinished() {
		return completed;
	}

}
