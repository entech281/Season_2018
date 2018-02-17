package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.RobotMap;

public class ElevatorSubsystem extends BaseSubsystem {




    WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.CAN.ELEVATOR_MOTOR);
    
    
    public void initDefaultCommand() {

    }
    public void setHeight(double d) {
        
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }

}