package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.subsystems.CubeGripperSubsystem;

public class CubeIntakeCommand extends BaseCommand {
	private CubeGripperSubsystem _CGS;
	
	public CubeIntakeCommand (CubeGripperSubsystem CGS) {
		super(CGS);
		this._CGS = CGS;
		requires (_CGS);
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
		}
		
	 public void end() {
	    }

	 public void interrupted() {
	   }
}
