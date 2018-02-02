package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.ArrayList;

public class AutonomousCommand extends CommandGroup {
	//private Command LastC;
	public AutonomousCommand(ArrayList<Command> l) {
		//this.LastC= l.get(l.size()-1);
		for(int i = 0; i<l.size();i++) {
			addSequential(l.get(i));
		}
	}
	@Override
	protected boolean isFinished() {
		return false;
		//return LastC.isFinished();
	}

}
