package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team281.robot.RobotMap;
import frc.team281.robot.commands.DriveUsingJoystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class DriveSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftMotorCANid);
	WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightMotorCANid);
	WPI_TalonSRX _rearLeftMotor = new WPI_TalonSRX(RobotMap.rearLeftMotorCANid);
	WPI_TalonSRX _rearRightMotor = new WPI_TalonSRX(RobotMap.rearRightMotorCANid);
	
	//Encoder Parameters & Encoder 

	
	private double m_heading;
	private double m_distance;
	DifferentialDrive _drive = new DifferentialDrive(
			new SpeedControllerGroup(_frontLeftMotor, _rearLeftMotor),
			new SpeedControllerGroup(_frontRightMotor,_rearRightMotor) );

	public void stop() {
		_drive.tankDrive(0.,0.);
	}

	public void arcadeDrive(double forw, double turn) {
		_drive.arcadeDrive(-forw, turn, true);
	}

	public void tankDrive(double left, double right) {
		_drive.tankDrive(left, right, true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveUsingJoystick(this));
    }
    public void setHeading(double angle) {
    		m_heading=angle;
    		//input motor details here
    }
    public void driveForward(double distance) {
    		m_distance=distance;
    		//input motor details here
    }
    public boolean isFinished() {
    		return true;
    }
    public void periodic() { 
    	_frontLeftMotor.getSelectedSensorPosition(0);
    }
    	
}
