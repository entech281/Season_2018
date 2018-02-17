package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.subsystems.ElevatorSubsystem;


public class LiftToPointCommand extends BaseCommand {

	public ElevatorSubsystem _ES;
	public LiftToPointCommand (ElevatorSubsystem ES) {
		super(ES);
		this._ES = ES;
		requires (_ES);
	}
	
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	 public void end() {
	    }

	 @Override
	 public void interrupted() {
	 	}
	
	

}
