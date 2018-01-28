package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team281.robot.RobotMap;
import frc.team281.robot.YawPIDInterface;
import frc.team281.robot.commands.DriveUsingJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.*;

public class DriveSubsystem extends Subsystem {

    WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftMotorCANid);
    WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightMotorCANid);
    WPI_TalonSRX _rearLeftMotor = new WPI_TalonSRX(RobotMap.rearLeftMotorCANid);
    WPI_TalonSRX _rearRightMotor = new WPI_TalonSRX(RobotMap.rearRightMotorCANid);

    DifferentialDrive _drive = new DifferentialDrive(
        new SpeedControllerGroup(_frontLeftMotor, _rearLeftMotor),
        new SpeedControllerGroup(_frontRightMotor,_rearRightMotor) );

    static final double kYaw_P = 0.03;
    static final double kYaw_I = 0.0001;
    static final double kYaw_D = 0.01;
    static final double kYaw_ToleranceDegrees = 2.0;

    private AHRS m_ahrs;
    private boolean m_navXok;
    private boolean m_yawCorrectionActive = false;
    YawPIDInterface m_yawPIDInterface;
    PIDController m_yawController;

    public DriveSubsystem() {
        try {
            m_ahrs = new AHRS(SerialPort.Port.kMXP);
            m_ahrs.reset();
            DriverStation.getInstance().reportWarning("NavX MXP found",false);
            m_navXok = true;
        }
        catch(Exception e) {
            m_navXok = false;
            DriverStation.getInstance().reportError("Trouble with NavX MXP",false);
        }

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
        if (m_yawCorrectionActive) {
            turn += m_yawPIDInterface.getYawCorrection();
        }
		_drive.arcadeDrive(-forw, turn, true);
	}

	public void tankDrive(double left, double right) {
        if (m_yawCorrectionActive) {
            left  += m_yawPIDInterface.getYawCorrection();
            right -= m_yawPIDInterface.getYawCorrection();
        }
		_drive.tankDrive(left, right, true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveUsingJoystick(this));
    }
}
