package frc.team281.robot;

import frc.team281.robot.commands.BaseCommand;

public interface CommandFactory {

    BaseCommand createCommand(String commandName);
}
