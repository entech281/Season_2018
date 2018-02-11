package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.subsystems.ShooterOutTakeSubsystem;

/**
 *
 */
public class ShooterOutTakeAbove extends Command {
    private ShooterOutTakeSubsystem inTake;

    public ShooterOutTakeAbove(ShooterOutTakeSubsystem inTake) {
        // Use requires() here to declare subsystem dependencies
        this.inTake = inTake;
        requires(inTake);
        setTimeout(0.05);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        inTake.shooterOutAbove();

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        inTake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
