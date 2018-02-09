package frc.team281.robot.subsystems;

import frc.team281.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class LeftRampSubsystem extends BaseSubsystem {

    WPI_TalonSRX leftRampMotor = new WPI_TalonSRX(RobotMap.leftRampMotorCANid );
   
    public void initDefaultCommand() {
        
    }
}