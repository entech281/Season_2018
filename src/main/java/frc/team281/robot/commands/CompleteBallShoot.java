package frc.team281.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.RobotMap;
import frc.team281.robot.subsystems.*;
public class CompleteBallShoot extends Command {
	private ProngsSubsystem Prongs;
	private ShooterInTakeSubsystem SIntake;
	private ShooterOutTakeSubsystem SOutake;
	public CompleteBallShoot(ProngsSubsystem Prongs,ShooterInTakeSubsystem SIntake,ShooterOutTakeSubsystem SOutake) {
		this.Prongs  = Prongs;
		this.SIntake = SIntake;
		this.SOutake = SOutake;
		requires(Prongs);
		requires(SIntake);
		requires(SOutake);
	}
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Prongs.lower();
		SIntake.shooterIn();
		for(int i=0;i<RobotMap.timeDown;i++) {
			SOutake.shooterOutAbove();
		}
		for(int i=0;i<RobotMap.timeUp;i++) {
			SOutake.shooterOutSLOW();
		}
		
		SIntake.stop();
		SOutake.stop();
	}

	
	

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
