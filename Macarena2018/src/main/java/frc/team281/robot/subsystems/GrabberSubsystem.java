package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team281.robot.RobotMap;
import frc.team281.robot.RobotMap.DigitalIO;
import frc.team281.robot.RobotMap.PCM;
import frc.team281.robot.controllers.TalonSpeedController;

public class GrabberSubsystem extends BaseSubsystem {
    
    private WPI_TalonSRX leftMotor;
    private WPI_TalonSRX rightMotor;
    private TalonSpeedController leftMotorController;
    private TalonSpeedController rightMotorController;
    
    public static final double LEFT_LOAD_PERCENT = 100;
    public static final double RIGHT_LOAD_PERCENT = 90;
    public static final double SHOOT_PERCENT = 100;
    
    private DigitalInput limitSwitch;
    
    private DoubleSolenoid Solenoid;

    
    public GrabberSubsystem() {
    }

    @Override
    public void initialize() {
        leftMotor = new WPI_TalonSRX(RobotMap.CAN.Grabber.MOTOR_LEFT);
        rightMotor = new WPI_TalonSRX(RobotMap.CAN.Grabber.MOTOR_RIGHT);

        Solenoid = new DoubleSolenoid(PCM.Grabber.INSIDE, PCM.Grabber.OUTSIDE);
        
        limitSwitch = new DigitalInput(DigitalIO.GRABBER_CUBE_LOADED);
        
        TalonSettings leftMotorSettings = TalonSettingsBuilder
                .defaults()
                .withCurrentLimits(10, 5, 200)
                .brakeInNeutral()
                .defaultDirectionSettings()
                .noMotorOutputLimits()
                .noMotorStartupRamping()
                .useSpeedControl()
                .build();
        
        TalonSettings rightMotorSettings = TalonSettingsBuilder.inverted(leftMotorSettings);
        leftMotorController = new TalonSpeedController(leftMotor, leftMotorSettings);
        rightMotorController = new TalonSpeedController(rightMotor, rightMotorSettings);        
        
    }

    public boolean isCubeTouchingSwitch() {
        return limitSwitch.get();
    }
    
    public void startLoading() {
        // run the motors until the switch is tripped then stop the motors
        leftMotorController.setDesiredSpeed(LEFT_LOAD_PERCENT);
        rightMotorController.setDesiredSpeed(RIGHT_LOAD_PERCENT);        
    }
    
    public void startShooting() {
        leftMotorController.setDesiredSpeed(-LEFT_LOAD_PERCENT);
        rightMotorController.setDesiredSpeed(-RIGHT_LOAD_PERCENT);  
    }
    
    public void stopMotors() {
        leftMotorController.setDesiredSpeed(0);
        rightMotorController.setDesiredSpeed(0);  
    }
    
    public void solenoidsOff() {
        Solenoid.set(DoubleSolenoid.Value.kOff);
    }
    
    public void open() {
        Solenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void close() {
        Solenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
}
