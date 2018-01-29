package frc.team281.robot.subsystems;

import java.lang.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team281.robot.RobotMap;
import frc.team281.robot.YawPIDInterface;
import frc.team281.robot.commands.DriveUsingJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.*;

public class DriveSubsystem extends Subsystem {

    private SmartDashboard m_sd;
    private WPI_TalonSRX _frontLeftMotor;
    private WPI_TalonSRX _frontRightMotor;
    private WPI_TalonSRX _rearLeftMotor;
    private WPI_TalonSRX _rearRightMotor;

    private DifferentialDrive _drive;

    private enum DriveState {
        kDriveJoystick,
        kDriveDistance
    }

    private DriveState m_state;

    static final double kYaw_P = 0.03;
    static final double kYaw_I = 0.0001;
    static final double kYaw_D = 0.01;
    static final double kYaw_ToleranceDegrees = 2.0;

    private AHRS m_ahrs;
    private boolean m_navXok;
    private double  m_yawHoldAngle;
    YawPIDInterface m_yawPIDInterface;
    PIDController m_yawController;

    public DriveSubsystem() {
        try {
            m_ahrs = new AHRS(SerialPort.Port.kMXP);
            m_ahrs.reset();
            DriverStation.reportWarning("NavX MXP found",false);
            m_navXok = true;
        }
        catch(Exception e) {
            m_navXok = false;
            DriverStation.reportError("Trouble with NavX MXP",false);
        }

        m_sd = new SmartDashboard();
        _frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftMotorCANid);
        _frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightMotorCANid);
        _rearLeftMotor = new WPI_TalonSRX(RobotMap.rearLeftMotorCANid);
        _rearRightMotor = new WPI_TalonSRX(RobotMap.rearRightMotorCANid);

        _drive = new DifferentialDrive(
            new SpeedControllerGroup(_frontLeftMotor, _rearLeftMotor),
            new SpeedControllerGroup(_frontRightMotor,_rearRightMotor) );

        m_state = DriveState.kDriveJoystick;
        m_yawHoldAngle = 0.0;
        m_yawPIDInterface = new YawPIDInterface(m_ahrs);
        m_yawController = new PIDController(kYaw_P, kYaw_I, kYaw_D, m_yawPIDInterface, m_yawPIDInterface);
        m_yawController.setAbsoluteTolerance(kYaw_ToleranceDegrees);
        m_yawController.setInputRange(-180.0,180.0);
        m_yawController.setContinuous(true);
        m_yawController.setOutputRange(-1.0,1.0);
        m_yawController.disable();

        // Make sure NavX has finished calibrating
        if (m_navXok) {
            while (m_ahrs.isCalibrating()) {
            	try {
            		Thread.sleep(50);   // 50ms
            	}
            	catch (InterruptedException e) {
            	}
            }
            m_ahrs.zeroYaw();
        }
    }

	public void stop() {
		_drive.tankDrive(0.,0.);
	}

	public void arcadeDrive(double forw, double turn) {
        if (m_yawController.isEnabled()) {
            turn += m_yawPIDInterface.getYawCorrection();
            if ((m_state == DriveState.kDriveJoystick) &&
                (Math.abs(forw) > 0.25) && (Math.abs(turn) > 0.25)) {
                double ang = Math.toDegrees(Math.atan2(turn,forw));
                setHoldYawAngle(ang);
            }
        }
		_drive.arcadeDrive(-forw, turn, true);
	}

	public void tankDrive(double left, double right) {
        if (m_yawController.isEnabled()) {
            left  += m_yawPIDInterface.getYawCorrection();
            right -= m_yawPIDInterface.getYawCorrection();
        }
		_drive.tankDrive(left, right, true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveUsingJoystick(this));
    }

    public void disableHoldYaw() {
        m_yawController.disable();
    }

    public void enableHoldYaw() {
        m_yawController.setSetpoint(m_ahrs.getYaw());
        m_yawController.enable();
    }

    public void setHoldYawAngle(double angle) {
        m_yawHoldAngle = angle;
        m_yawController.setSetpoint(m_yawHoldAngle);
    }

    @Override
    public void periodic() {
        // function called by scheduler automatically
        // If driving by joystick -- then nothing to do
        m_sd.putBoolean("Hold Yaw Active: ", m_yawController.isEnabled());
        m_sd.putNumber("Hold Yaw Angle: ", m_yawHoldAngle);
        if (m_navXok) {
            m_sd.putData("NavX: ", m_ahrs);
            m_sd.putNumber("NavX Yaw Angle: ", m_ahrs.getYaw());
        }
        if (m_state == DriveState.kDriveJoystick) {
            return;
        }
    }
}
