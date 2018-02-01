package frc.team281.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.subsystems.DriveSubsystem;
public class DotMoveCommand<x,y> extends Command{
	private DriveSubsystem drive;
	public DotMoveCommand(DriveSubsystem d) {
		this.drive=d;
		requires(d);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
