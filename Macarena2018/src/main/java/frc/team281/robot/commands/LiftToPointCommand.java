package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.subsystems.ElevatorSubsystem;


public class LiftToPointCommand extends BaseCommand {

	private ElevatorSubsystem _ES;
	private double height;
	
	public LiftToPointCommand (ElevatorSubsystem ES, double height) {
		super(ES);
		this._ES = ES;
		this.height = height;
		requires (_ES);
	}
	
	protected void execute() {
		_ES.setHeight(height);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return !_ES.lifting;
	}
	 public void end() {
	    }

	 @Override
	 public void interrupted() {
	 	}
	
	

}
