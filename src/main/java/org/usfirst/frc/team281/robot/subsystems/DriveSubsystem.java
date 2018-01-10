package org.usfirst.frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team281.robot.RobotMap;
import org.usfirst.frc.team281.robot.commands.DriveUsingJoystick;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class DriveSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftMotorCANid);
	WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightMotorCANid);
	WPI_TalonSRX _rearLeftMotor = new WPI_TalonSRX(RobotMap.rearLeftMotorCANid);
	WPI_TalonSRX _rearRightMotor = new WPI_TalonSRX(RobotMap.rearRightMotorCANid);

	DifferentialDrive _drive = new DifferentialDrive(
			new SpeedControllerGroup(_frontLeftMotor, _rearLeftMotor),
			new SpeedControllerGroup(_frontRightMotor,_rearRightMotor) );

	public void stop() {
		_drive.tankDrive(0.,0.);
	}

	public void arcadeDrive(double forw, double turn) {
		_drive.arcadeDrive(forw, turn, true);
	}

	public void tankDrive(double left, double right) {
		_drive.arcadeDrive(left, right, true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveUsingJoystick(this));
    }
}
