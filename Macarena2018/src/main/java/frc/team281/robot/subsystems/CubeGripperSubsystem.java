package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.RobotMap;

public class CubeGripperSubsystem extends BaseSubsystem {

    WPI_TalonSRX rightMotor = new WPI_TalonSRX(RobotMap.CAN.RIGHT_CUBE_BELT_MOTOR);
    WPI_TalonSRX leftMotor = new WPI_TalonSRX(RobotMap.CAN.LEFT_CUBE_BELT_MOTOR);
    WPI_TalonSRX clawMotor = new WPI_TalonSRX(RobotMap.CAN.CUBE_GRIPPER_MOTOR);

    public void initDefaultCommand() {

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }
}