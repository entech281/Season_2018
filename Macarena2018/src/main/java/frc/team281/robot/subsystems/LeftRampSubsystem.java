package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.RobotMap;

public class LeftRampSubsystem extends BaseSubsystem {

    WPI_TalonSRX leftRampMotor = new WPI_TalonSRX(RobotMap.leftRampMotorCANid);

    public void initDefaultCommand() {

    }
}