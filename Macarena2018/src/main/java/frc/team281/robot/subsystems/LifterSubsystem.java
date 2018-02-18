package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team281.robot.RobotMap;

public class LifterSubsystem extends BaseSubsystem {
    
    private WPI_TalonSRX motorOne;
    private WPI_TalonSRX motorTwo;
    
    private DigitalInput limitSwitch;
    
    public LifterSubsystem() {
    }

    @Override
    public void initialize() {
        motorOne = new WPI_TalonSRX(RobotMap.CAN.Lifter.MOTOR_ONE);
        motorTwo = new WPI_TalonSRX(RobotMap.CAN.Lifter.MOTOR_TWO);
        
    }

    public void gotoHeight(double heightInches) {
        // run the motors until the encoder says we're at a certain height
    }
    
    public void home() {
        // go down until you hit the limit switch, then set encoders to 0
    }
    
}