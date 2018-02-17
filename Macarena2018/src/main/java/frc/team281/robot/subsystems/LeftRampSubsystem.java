package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.RobotMap;

public class LeftRampSubsystem extends BaseSubsystem {

    WPI_TalonSRX leftRampMotor = new WPI_TalonSRX(RobotMap.CAN.LEFT_RAMP_MOTOR);

    public void initDefaultCommand() {

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }
}