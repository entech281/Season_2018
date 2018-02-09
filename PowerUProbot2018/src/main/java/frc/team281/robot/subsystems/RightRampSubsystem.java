package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.team281.robot.RobotMap;

public class RightRampSubsystem extends BaseSubsystem {
    
    WPI_TalonSRX RightRampMotor = new WPI_TalonSRX(RobotMap.rightRampMotorCANid);
    
    public void initDefaultCommand() {
        
    }
}
