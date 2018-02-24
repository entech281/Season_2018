package frc.team281.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team281.robot.RobotMap;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.subsystems.drive.BaseDriveController;
import frc.team281.robot.subsystems.drive.EncoderInchesConverter;
import frc.team281.robot.subsystems.drive.FourTalonsWithSettings;

public class FourTalonEncoderCheckController extends BaseDriveController{
	
	private DataLogger log;
	private FourTalonsWithSettings talons;
	private DriveEncoderStatus driveEncoderStatus;
	private boolean workAroundBrokenEncoders = true;	

    public FourTalonEncoderCheckController(FourTalonsWithSettings talons, EncoderInchesConverter converter) {
		this.talons = talons;		
		this.log = DataLoggerFactory.getLoggerFactory().createDataLogger(getClass().getSimpleName());
		driveEncoderStatus = new DriveEncoderStatus(converter);
	}
	
    public boolean isWorkAroundBrokenEncoders() {
        return workAroundBrokenEncoders;
    }

    public void setWorkAroundBrokenEncoders(boolean workAroundBrokenEncoders) {
        this.workAroundBrokenEncoders = workAroundBrokenEncoders;
    }    

    public DriveEncoderStatus getDriveEncoderStatus(){
        return driveEncoderStatus;
    }
    
	public void setMotorsWithBrokenEncodersToFollowers( ) {
		
		//set these directly on the talons for immediate results.
		//another way would be to change the settings and then apply them to the talons,
		//but that would make the follower mode persistent, which i dont think we want
		
		if (driveEncoderStatus.shouldDisableAll()) {
			talons.getFrontLeft().set(ControlMode.Disabled, 0);
			talons.getFrontRight().set(ControlMode.Disabled, 0);
			talons.getRearLeft().set(ControlMode.Disabled, 0);
			talons.getRearRight().set(ControlMode.Disabled, 0);
		} else {
			if (driveEncoderStatus.shouldLeftFrontFollowLeftRear()) {
				log.warn("Left Front Encoder appears to be broken. It will follow Left Rear");
				talons.getFrontLeft().set(ControlMode.Follower, RobotMap.CAN.REAR_LEFT_MOTOR);
			}
			if (driveEncoderStatus.shouldLeftRearFollowLeftFront()) {
				log.warn("Left Rear Encoder appears to be broken. It will follow Left Front");
				talons.getRearLeft().set(ControlMode.Follower,RobotMap.CAN.FRONT_LEFT_MOTOR);
			}
			if (driveEncoderStatus.shouldRightFrontFollowRightRear()) {
				log.warn("Right Front Encoder appears to be broken. It will follow Right Rear");
				talons.getFrontRight().set(ControlMode.Follower,RobotMap.CAN.REAR_RIGHT_MOTOR);
			}
			if (driveEncoderStatus.shouldRightRearFollowRightFront()) {
				log.warn("Right Rear Encoder appears to be broken. It will follow Right Front");
				talons.getRearRight().set(ControlMode.Follower,RobotMap.CAN.FRONT_RIGHT_MOTOR );
			}
		}		
		log.log("frontLeftMode",talons.getFrontLeft().getControlMode());
		log.log("frontRightMode",talons.getFrontRight().getControlMode());
		log.log("rearLeftMode",talons.getRearLeft().getControlMode());
		log.log("rearRightMode",talons.getRearRight().getControlMode());
		
	}

    @Override
    public void activate() {
         //nothing to do
        
    }

    @Override
    public void periodic() {
        driveEncoderStatus.updateStatus(talons);
        if (workAroundBrokenEncoders ){
            setMotorsWithBrokenEncodersToFollowers();
        }
    }

    @Override
    public void deactivate() {
        //nothing to do
        
    }	
	
	
}
