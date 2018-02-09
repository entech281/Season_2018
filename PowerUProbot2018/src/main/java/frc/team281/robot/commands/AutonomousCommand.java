package frc.team281.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;
import java.util.ArrayList;

public class AutonomousCommand extends CommandGroup {
	private BaseCommand LastC;
	public AutonomousCommand(ArrayList<BaseCommand> l) {
		this.LastC= l.get(l.size()-1);
		for(int i = 0; i<l.size();i++) {
			addSequential(l.get(i));
		}
	}
	@Override
	protected boolean isFinished() {
		return LastC.isFinished();
	}

}
