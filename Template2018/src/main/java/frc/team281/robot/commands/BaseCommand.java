package frc.team281.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team281.robot.logger.DataLogger;

public abstract class BaseCommand extends Command{

	protected DataLogger dataLogger;
	
	public BaseCommand(double timeOut, DataLogger dataLogger){
		super(timeOut);
		this.dataLogger = dataLogger;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		super.execute();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		super.end();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		super.interrupted();
	}

	@Override
	protected synchronized boolean isTimedOut() {
		// TODO Auto-generated method stub
		return super.isTimedOut();
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public synchronized boolean doesRequire(Subsystem system) {
		// TODO Auto-generated method stub
		return super.doesRequire(system);
	}

	@Override
	public void clearRequirements() {
		// TODO Auto-generated method stub
		super.clearRequirements();
	}

	@Override
	public synchronized void setInterruptible(boolean interruptible) {
		// TODO Auto-generated method stub
		super.setInterruptible(true);
	}

}
