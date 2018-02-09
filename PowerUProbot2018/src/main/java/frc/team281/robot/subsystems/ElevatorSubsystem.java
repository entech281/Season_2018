package frc.team281.robot.subsystems;

import frc.team281.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class ElevatorSubsystem extends Subsystem {

    
    WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorCANid );
    WPI_TalonSRX wristMotor    = new WPI_TalonSRX(RobotMap.cubeWristCANid);
   
    public void initDefaultCommand() {
        
    }
}