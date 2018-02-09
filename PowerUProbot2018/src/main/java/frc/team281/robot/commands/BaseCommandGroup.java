package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class BaseCommandGroup extends CommandGroup {
    @Override
    protected boolean isFinished() {
        return false;
    }
}
