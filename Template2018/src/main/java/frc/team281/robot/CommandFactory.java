package frc.team281.robot;

import frc.team281.robot.commands.LifterLowerCommand;
import frc.team281.robot.commands.LifterRaiseCommand;

public interface CommandFactory {

    LifterRaiseCommand createRaiseCommand();
    LifterLowerCommand createLowerCommand();

}
