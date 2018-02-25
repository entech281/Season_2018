package frc.team281.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team281.robot.RobotMap;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.talons.DriveEncoderStatus;
import frc.team281.robot.talons.FourTalonGroup;


public class FourTalonEncoderChecker{
	
	private DataLogger log;

	private boolean workAroundBrokenEncoders = true;	

    public FourTalonEncoderChecker() {	
		this.log = DataLoggerFactory.getLoggerFactory().createDataLogger(getClass().getSimpleName());
	}
	
    public boolean isWorkAroundBrokenEncoders() {
        return workAroundBrokenEncoders;
    }

    public void setWorkAroundBrokenEncoders(boolean workAroundBrokenEncoders) {
        this.workAroundBrokenEncoders = workAroundBrokenEncoders;
    }    
    
	public void updateMotorsToWorkAroundBrokenEncodersIfEnabled(FourTalonGroup talons, DriveEncoderStatus driveEncoderStatus ) {
		if (! workAroundBrokenEncoders ) return;
		
		//set these directly on the talons for immediate results.
		//another way would be to change the settings and then apply them to the talons,
		//but that would make the follower mode persistent, which i dont think we want
		
		if (driveEncoderStatus.shouldDisableAll()) {
			talons.frontLeft.set(ControlMode.Disabled, 0);
			talons.frontRight.set(ControlMode.Disabled, 0);
			talons.rearLeft.set(ControlMode.Disabled, 0);
			talons.rearRight.set(ControlMode.Disabled, 0);
		} else {
			if (driveEncoderStatus.shouldLeftFrontFollowLeftRear()) {
				log.warn("Left Front Encoder appears to be broken. It will follow Left Rear");
				talons.frontLeft.set(ControlMode.Follower, RobotMap.CAN.REAR_LEFT_MOTOR);
			}
			if (driveEncoderStatus.shouldLeftRearFollowLeftFront()) {
				log.warn("Left Rear Encoder appears to be broken. It will follow Left Front");
				talons.rearLeft.set(ControlMode.Follower,RobotMap.CAN.FRONT_LEFT_MOTOR);
			}
			if (driveEncoderStatus.shouldRightFrontFollowRightRear()) {
				log.warn("Right Front Encoder appears to be broken. It will follow Right Rear");
				talons.frontRight.set(ControlMode.Follower,RobotMap.CAN.REAR_RIGHT_MOTOR);
			}
			if (driveEncoderStatus.shouldRightRearFollowRightFront()) {
				log.warn("Right Rear Encoder appears to be broken. It will follow Right Front");
				talons.rearRight.set(ControlMode.Follower,RobotMap.CAN.FRONT_RIGHT_MOTOR );
			}
		}		
		log.log("frontLeftMode",talons.frontLeft.getControlMode());
		log.log("frontRightMode",talons.frontRight.getControlMode());
		log.log("rearLeftMode",talons.rearLeft.getControlMode());
		log.log("rearRightMode",talons.rearRight.getControlMode());
		
	}
	
}
