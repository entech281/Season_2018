package frc.team281.robot.commands;

import frc.team281.robot.subsystems.BaseLifterSubsystem;

public class LifterLowerCommand extends BaseCommand {

    private BaseLifterSubsystem lifter;
    private boolean completed = false;

    public LifterLowerCommand(BaseLifterSubsystem lifter) {
        this(lifter, BaseLifterSubsystem.LIFTER_TIMEOUT);
    }

    public LifterLowerCommand(BaseLifterSubsystem lifter, double timeout) {
        super(lifter, timeout);
        this.lifter = lifter;
    }

    @Override
    protected void execute() {
        if (!lifter.isAtBottom()) {
            dataLogger.log("Execute", "Lower");
            lifter.lower();
        } else {
            dataLogger.log("Execute", "Cannot Lower");
            completed = true;
        }
    }

    @Override
    public boolean isFinished() {
        return completed;
    }

}
