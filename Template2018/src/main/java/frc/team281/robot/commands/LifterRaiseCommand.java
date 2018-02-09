package frc.team281.robot.commands;

import frc.team281.robot.subsystems.BaseLifterSubsystem;

public class LifterRaiseCommand extends BaseCommand {

    private BaseLifterSubsystem lifter;
    private boolean completed = false;

    public LifterRaiseCommand(BaseLifterSubsystem lifter) {
        this(lifter, BaseLifterSubsystem.LIFTER_TIMEOUT);
    }

    public LifterRaiseCommand(BaseLifterSubsystem lifter, double timeout) {
        super(lifter, timeout);
        this.lifter = lifter;
    }

    @Override
    protected void execute() {

        if (!lifter.isAtTop()) {
            dataLogger.log("Execute", "Raise");
            lifter.raise();
        } else {
            dataLogger.log("Execute", "Raise Complete");
            completed = true;
        }
    }

    @Override
    public boolean isFinished() {
        return completed;
    }

}
