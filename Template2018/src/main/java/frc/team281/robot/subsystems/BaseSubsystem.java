package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team281.robot.logger.DataLogger;

public abstract class BaseSubsystem extends Subsystem{

  protected DataLogger dataLogger;
  
  public BaseSubsystem (DataLogger dataLogger ) {
    this.dataLogger = dataLogger;
  }
  
  public abstract void init();
  
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void setDefaultCommand(Command command) {
		// TODO Auto-generated method stub
		super.setDefaultCommand(command);
	}

	@Override
	public Command getDefaultCommand() {
		// TODO Auto-generated method stub
		return super.getDefaultCommand();
	}

	@Override
	public String getDefaultCommandName() {
		// TODO Auto-generated method stub
		return super.getDefaultCommandName();
	}

	@Override
	public Command getCurrentCommand() {
		// TODO Auto-generated method stub
		return super.getCurrentCommand();
	}

	@Override
	public String getCurrentCommandName() {
		// TODO Auto-generated method stub
		return super.getCurrentCommandName();
	}

}
