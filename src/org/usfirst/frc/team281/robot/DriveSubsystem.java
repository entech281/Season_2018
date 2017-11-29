package org.usfirst.frc.team281.robot;

import org.strongback.components.Motor;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import com.ctre.CANTalon;


public class DriveSubsystem {

	
	public static TankDrive createDriveFromMotors( Motor lf, Motor lr, Motor rf, Motor rr ) {
		Motor left = Motor.compose(lf, lr);
        Motor right = Motor.compose(rf, rr).invert();
        TankDrive td = new TankDrive(left,right);
        
        return new TankDrive(left,right);		
	}
	
	public static TankDrive createDrive() {
		CANTalon LF = new CANTalon(RobotMap.LeftFrontMotor);
		CANTalon LR = new CANTalon(RobotMap.LeftRearMotor);
		CANTalon RF = new CANTalon(RobotMap.RightFrontMotor);
		CANTalon RR = new CANTalon(RobotMap.RightRearMotor);
		
		return createDriveFromMotors( 
             Hardware.Motors.talonSRX(LF),
             Hardware.Motors.talonSRX(LR),
             Hardware.Motors.talonSRX(RF),
             Hardware.Motors.talonSRX(RR));
 
	}

}
