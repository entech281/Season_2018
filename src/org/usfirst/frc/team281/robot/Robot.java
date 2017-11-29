package org.usfirst.frc.team281.robot;

import org.strongback.Strongback;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;
import org.usfirst.frc.team281.command.TimedDriveCommand;

import edu.wpi.first.wpilibj.IterativeRobot;


public class Robot extends IterativeRobot {

	private TankDrive drive;
    private ContinuousRange driveSpeed;
    private ContinuousRange turnSpeed;
    
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		Strongback.configure().recordNoData();
				
		drive = DriveSubsystem.createDrive();
				
        FlightStick joystick = Hardware.HumanInterfaceDevices.logitechAttack3D(RobotMap.JoyStickPort);
        ContinuousRange sensitivity = joystick.getThrottle().map(t -> (t + 1.0) / 2.0);
        driveSpeed = joystick.getPitch().scale(sensitivity::read); // scaled
        turnSpeed = joystick.getRoll().scale(sensitivity::read).invert(); // scaled and inverted		
	}


	@Override
    public void autonomousInit() {
        // Start Strongback functions ...
        Strongback.start();
        Strongback.submit(new TimedDriveCommand(drive, 0.5, 0.5, 5.0));
    }
    
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // Kill anything running if it is ...
        Strongback.disable();
        // Start Strongback functions if not already running...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
        drive.arcade(driveSpeed.read(), turnSpeed.read());
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }
}

