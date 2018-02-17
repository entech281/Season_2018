package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.RobotMap;

public class ElevatorSubsystem extends BaseSubsystem {

    WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorCANid);
    WPI_TalonSRX wristMotor = new WPI_TalonSRX(RobotMap.cubeWristCANid);

    public void initDefaultCommand() {

    }

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}