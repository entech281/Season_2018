package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.logger.DataLogger;

public abstract class BaseCommand extends Command{

    protected DataLogger dataLogger;

    public BaseCommand(double timeOut, DataLogger dataLogger) {
	super(timeOut);
	this.dataLogger = dataLogger;
    }	
}
