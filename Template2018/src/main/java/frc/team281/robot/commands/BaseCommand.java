package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.subsystems.BaseSubsystem;

public abstract class BaseCommand extends Command {

	protected DataLogger dataLogger;
	public static final double UNLIMITED_TIMEOUT = 100000000;
	
	public BaseCommand(BaseSubsystem subsystem, double timeOut, DataLogger dataLogger) {
		super(timeOut);
		this.dataLogger = dataLogger;
		dataLogger.setName(this.getName());
		
		if ( subsystem != null) {
			requires(subsystem);
		}

	}
	
}
