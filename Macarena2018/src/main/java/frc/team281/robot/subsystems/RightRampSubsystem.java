package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.RobotMap;

public class RightRampSubsystem extends BaseSubsystem {

    WPI_TalonSRX RightRampMotor = new WPI_TalonSRX(RobotMap.CAN.RIGHT_RAMP_MOTOR);

    public void initDefaultCommand() {

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }
}
