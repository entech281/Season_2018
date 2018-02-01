package frc.team281.subsystems;

import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.subsystems.BaseLifterSubsystem;

public class TestLifterSubsystem extends BaseLifterSubsystem{

	public TestLifterSubsystem(DataLogger dataLogger) {
		super(dataLogger);
	}

	public enum Position {
		UP,
		DOWN
	}
	
	private Position position;
	
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public void raise() {
		dataLogger.log("raise", true);
		
	}

	@Override
	public void lower() {
		dataLogger.log("lower", true);
		
	}

	@Override
	public boolean canLower() {
		// TODO Auto-generated method stub
		return position == Position.UP;
	}

	@Override
	public boolean canRaise() {
		// TODO Auto-generated method stub
		return position == Position.DOWN;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
