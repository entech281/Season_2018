package frc.team281.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team281.robot.subsystems.DriveSubsystem;
import frc.team281.robot.Robot;
public class DotMoveCommand extends CommandGroup{
	private DriveSubsystem drive;
	private double x;
	private double y;
	public DotMoveCommand(DriveSubsystem d,double x,double y) {
		this.drive=d;
		this.x=x;
		this.y=y;
		requires(d);
	}
	private double CalculateAngle() {
		return Math.atan2(y-Robot.y, x-Robot.x);
	}
	protected void execute() {
		
	}
	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
