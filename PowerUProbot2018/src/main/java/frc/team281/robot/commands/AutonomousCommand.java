package frc.team281.robot.commands;

import java.util.ArrayList;

public class AutonomousCommand extends BaseCommandGroup {
    private BaseCommand LastC;

    public AutonomousCommand(ArrayList<BaseCommand> l) {
        this.LastC = l.get(l.size() - 1);
        for (int i = 0; i < l.size(); i++) {
            addSequential(l.get(i));
        }
    }

    @Override
    protected boolean isFinished() {
        return LastC.isFinished();
    }

}
