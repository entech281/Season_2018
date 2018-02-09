package frc.team281.robot.subsystems;

import frc.team281.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ElevatorSubsystem extends BaseSubsystem {

    WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorCANid );
    WPI_TalonSRX wristMotor    = new WPI_TalonSRX(RobotMap.cubeWristCANid);
   
    public void initDefaultCommand() {
        
    }
}