package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team281.robot.RobotMap;
import frc.team281.robot.commands.DriveUsingJoystick;

public class DriveSubsystem extends Subsystem {
    static final int cEncoderSignalOK = 200;
    static final int cEncoderSignalBad = 0;
    
    private AHRS mNavX;
    private boolean mNavXok;
    
    public DriveSubsystem() {
        
        try {
            mNavX = new AHRS(SerialPort.Port.kMXP);
            mNavX.reset();
            DriverStation.reportWarning("NavX MXP found", false);
            mNavXok = true;
        } catch (Exception e) {
            mNavXok = false;
            DriverStation.reportError("Trouble with NavX MXP", false);
        }
        
        this.frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftMotorCANid);
        this.frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightMotorCANid);
        this.rearLeftMotor = new WPI_TalonSRX(RobotMap.rearLeftMotorCANid);
        this.rearRightMotor = new WPI_TalonSRX(RobotMap.rearRightMotorCANid);
        
        // set up for speed control
        //drive = new DifferentialDrive(new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
        //        new SpeedControllerGroup(frontRightMotor, rearRightMotor));
        
        // set up for position control
        MotionSettings leftMotionSettings = MotionSettings.defaults()
                .motionProfile(MOTOR_ACCELERATION, MOTOR_CRUISE_VELOCITY).withGains(MotorConstants.F_MOTOR,
                        MotorConstants.P_MOTOR, MotorConstants.I_MOTOR, MotorConstants.D_MOTOR)
                .build();

        MotionSettings rightMotionSettings = leftMotionSettings.getInvertedClone();

        frontLeftMotorPosition = new MotorPositionController(frontLeftMotor, leftMotionSettings,
                "frontLeftMotorPosition");
        rearLeftMotorPosition = new MotorPositionController(rearLeftMotor, leftMotionSettings, "rearLeftMotorPosition");
        frontRightMotorPosition = new MotorPositionController(frontRightMotor, rightMotionSettings,
                "frontRightMotorPosition");
        rearRightMotorPosition = new MotorPositionController(rearRightMotor, rightMotionSettings,
                "rearRightMotorPosition");
        
        SmartDashboard.putData(this);
    }

    // for speed control
    private DifferentialDrive drive;

    // for position control
    private MotorPositionController frontLeftMotorPosition;
    private MotorPositionController rearLeftMotorPosition;
    private MotorPositionController frontRightMotorPosition;
    private MotorPositionController rearRightMotorPosition;

    // have to hold on to these to change control modes.
    private WPI_TalonSRX frontLeftMotor;
    private WPI_TalonSRX frontRightMotor;
    private WPI_TalonSRX rearLeftMotor;
    private WPI_TalonSRX rearRightMotor;

    // estimated based on 6" diameter wheels, with 80 counts per turn, gear ratio
    // 14/52 * 14/52 13.8:1
    public static final double ENCODER_CLICKS_PER_INCH = 46;

    // units are counts/sec
    // max possible speed:
    // estimated at 3200 rev/min * 80 counts/rev * 1 min / 60 sec = 4266 counts/sec
    // note that this speed is not affected by the gear ratio!
    // 75% of that is 3200 counts/sec
    public static final int MOTOR_CRUISE_VELOCITY = 4500;

    // lets get to full speed in 1 second
    // not affected by gear ratio
    public static final int MOTOR_ACCELERATION = 4500;

    // recommended-- start with all gains but P, and work from there
    // then double the gain until oscillations occur
    // then add d gain at 10 to 100x P
    // finally, add I = 0.01xP
    
    public static class MotorConstants {
        public static final double I_MOTOR = 0.0;
        public static final double P_MOTOR = 3.0;
        public static final double D_MOTOR = 20.0;
        public static final double F_MOTOR = 0.4;
    }

    public void initialize() {
    }

    protected void setSpeedMode() {
        frontLeftMotor.set(ControlMode.PercentOutput, 0.);
        frontRightMotor.set(ControlMode.PercentOutput, 0.);
        rearLeftMotor.set(ControlMode.PercentOutput, 0.);
        rearRightMotor.set(ControlMode.PercentOutput, 0.);
    }

    protected static int convertFromInchesToEncoderCounts(double inches) {
        return (int) (ENCODER_CLICKS_PER_INCH * inches);
    }

    protected static double convertFromEncoderCountsToInches(int encoderCounts) {
        return (double) encoderCounts / (double) ENCODER_CLICKS_PER_INCH;
    }

    public void periodic() {
        SmartDashboard.putData(this);
        SmartDashboard.putNumber("frontLeftEncoderDave", frontLeftMotor.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("frontRightEncoderDave", frontRightMotor.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("rearLeftEncoderDave", rearLeftMotor.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("rearRightEncoderDave", rearRightMotor.getSelectedSensorPosition(0));        
    }
    public void stop() {
        //setSpeedMode();
        drive.tankDrive(0., 0.);
    }

    public void arcadeDrive(double forw, double turn) {
        //setSpeedMode();
        //drive.arcadeDrive(-forw, turn, true);
    }

    public void tankDrive(double left, double right) {
        //setSpeedMode();
        //drive.tankDrive(left, right, true);
    }

    public void drive(Position desiredPosition) {
        int encoderCountsLeft = convertFromInchesToEncoderCounts(desiredPosition.getLeftInches());
        int encoderCountsRight = convertFromInchesToEncoderCounts(desiredPosition.getRightInches());

        frontLeftMotorPosition.setDesiredPosition(encoderCountsLeft);
        rearLeftMotorPosition.setDesiredPosition(encoderCountsLeft);
        frontRightMotorPosition.setDesiredPosition(encoderCountsRight);
        rearRightMotorPosition.setDesiredPosition(encoderCountsRight);
    }

    public Position getCurrentPosition() {
        double frontLeftInches = convertFromEncoderCountsToInches(frontLeftMotorPosition.getCurrentPosition());
        double rearLeftInches = convertFromEncoderCountsToInches(rearLeftMotorPosition.getCurrentPosition());
        double frontRightInches = convertFromEncoderCountsToInches(frontRightMotorPosition.getCurrentPosition());
        double rearRightInches = convertFromEncoderCountsToInches(rearRightMotorPosition.getCurrentPosition());

        double avgLeftInches = (frontLeftInches + rearLeftInches) / 2.0;
        double avgRightInches = (frontRightInches + rearRightInches) / 2.0;
        // use the average
        return new Position(avgLeftInches, avgRightInches);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveUsingJoystick(this));
    }
}
