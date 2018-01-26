package frc.team281.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team281.robot.Robot;
import frc.team281.robot.Robot;
import frc.team281.robot.commands.*;
public class CompleteBallShoot extends CommandGroup {
	public CompleteBallShoot() {
		addSequential(new ProngsDown(Robot._prongsSubsystem));
		addParallel(new ShooterOutTakeBelow(Robot.m_ShooterOutTakeSubsystem));
		addSequential(new ShooterOutTakeAbove(Robot.m_ShooterOutTakeSubsystem));
	}
}
