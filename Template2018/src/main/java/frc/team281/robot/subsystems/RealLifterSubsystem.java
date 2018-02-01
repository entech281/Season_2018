package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import frc.team281.robot.RobotMap;
import frc.team281.robot.logger.DataLogger;

public class RealLifterSubsystem extends BaseLifterSubsystem{

	private Solenoid upSolenoid;
	private Solenoid downSolenoid;	
	private DigitalInput upperLimit;
	private DigitalInput lowerLimit;
	
	public RealLifterSubsystem(DataLogger dataLogger) {
		super(dataLogger);
	}

	@Override
	public void initialize() {
		upperLimit = new DigitalInput(RobotMap.DigitalIO.UPPER_LIFTER_LIMIT);
		lowerLimit = new DigitalInput(RobotMap.DigitalIO.LOWER_LFTER_LIMIT);
		upSolenoid = new Solenoid(RobotMap.CAN.LIFTER_UP_SOLENOID, RobotMap.PCMChannel.LIFTER_UP_SOLENOID);
		downSolenoid = new Solenoid(RobotMap.CAN.LIFTER_UP_SOLENOID, RobotMap.PCMChannel.LIFTER_DOWN_SOLENOID);			
	}	
	
	@Override
	public void raise() {
		upSolenoid.set(true);
		downSolenoid.set(false);
		dataLogger.log("raise", true);
	}

	@Override
	public void lower() {
		upSolenoid.set(true);
		downSolenoid.set(false);
		dataLogger.log("raise", true);
	}

	@Override
	public boolean isAtTop() {
		// TODO Auto-generated method stub
		return upperLimit.get();
	}

	@Override
	public boolean isAtBottom() {
		// TODO Auto-generated method stub
		return lowerLimit.get();
	}

	@Override
	protected void initDefaultCommand() {
		
	}

}
