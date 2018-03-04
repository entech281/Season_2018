package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import frc.team281.robot.Robot;

public class WristSubsystem extends BaseSubsystem {

    private Solenoid up;
    private Solenoid down;

    //2 private DoubleSolenoid solenoid;

    private boolean wristUp;

    
    public WristSubsystem() {
    }

    @Override
    public void initialize() {
        //2 solenoid = new DoubleSolenoid(RobotMap.CAN.PC_MODULE, PCM.Wrist.UP, PCM.Wrist.DOWN);
        wristUp = true;
        up = new Solenoid(Robot.robotMap.CAN.PC_MODULE, Robot.robotMap.PCM.Wrist.UP);
        down = new Solenoid(Robot.robotMap.CAN.PC_MODULE,Robot.robotMap.PCM.Wrist.DOWN);

    }

    @Override
    public void periodic() {
        //2 if (wristUp) {
        //2     solenoid.set(DoubleSolenoid.Value.kReverse);
        //2 } else {
        //2     solenoid.set(DoubleSolenoid.Value.kForward);
        //2 }
    }

    public void pivotUp() {
        wristUp = true;
        up.set(false);
        down.set(false);
    }
    
    public void pivotDown() {
        wristUp = false;
        up.set(true);
        down.set(true);
    }
    
}
