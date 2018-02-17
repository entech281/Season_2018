package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.RobotMap;

public class CubeGripperSubsystem extends BaseSubsystem {

    WPI_TalonSRX rightMotor = new WPI_TalonSRX(RobotMap.rightCubeGripperCANid);
    WPI_TalonSRX leftMotor = new WPI_TalonSRX(RobotMap.leftCubeGripperCANid);
    WPI_TalonSRX clawMotor = new WPI_TalonSRX(RobotMap.clawCubeGripperCANid);

    public void initDefaultCommand() {

    }
}