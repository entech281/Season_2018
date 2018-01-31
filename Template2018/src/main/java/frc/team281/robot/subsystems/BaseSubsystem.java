package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team281.robot.logger.DataLogger;

public abstract class BaseSubsystem extends Subsystem {

    protected DataLogger dataLogger;

    public BaseSubsystem( DataLogger dataLogger) {
	this.dataLogger = dataLogger;
    }

}
