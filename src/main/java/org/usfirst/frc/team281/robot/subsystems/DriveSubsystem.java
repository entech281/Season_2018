package org.usfirst.frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team281.robot.RobotMap;
import org.usfirst.frc.team281.robot.commands.DriveUsingJoystick;
import com.ctre.CANTalon;

public class DriveSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	CANTalon _frontLeftMotor = new CANTalon(RobotMap.frontLeftMotorCANid);
	CANTalon _frontRightMotor = new CANTalon(RobotMap.frontRightMotorCANid);
	CANTalon _rearLeftMotor = new CANTalon(RobotMap.rearLeftMotorCANid);
	CANTalon _rearRightMotor = new CANTalon(RobotMap.rearRightMotorCANid);

	RobotDrive _drive = new RobotDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);

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
        setDefaultCommand(new DriveUsingJoystick());
    }
}
