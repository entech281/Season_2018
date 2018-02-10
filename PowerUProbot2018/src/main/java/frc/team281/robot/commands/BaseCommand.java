package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class BaseCommand extends Command {

    @Override
    protected boolean isFinished() {
        return false;
    }
}
