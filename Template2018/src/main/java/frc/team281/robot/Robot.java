
package frc.team281.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team281.robot.commands.JoystickDriveCommand;
import frc.team281.robot.commands.LifterLowerCommand;
import frc.team281.robot.commands.LifterRaiseCommand;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.subsystems.RealDriveSubsystem;
import frc.team281.robot.subsystems.RealLifterSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot implements CommandFactory{

	private RealDriveSubsystem  driveSubsystem ;
	private RealLifterSubsystem lifterSubsystem;
	private OperatorInterface operatorInterface;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		
		lifterSubsystem = new RealLifterSubsystem(DataLogger.realMatchConfiguration());
		operatorInterface = new OperatorInterface(this);
		driveSubsystem = new RealDriveSubsystem( DataLogger.realMatchConfiguration(),operatorInterface);
		
		driveSubsystem.initialize();
		operatorInterface.initialize();
		operatorInterface.initialize();
	}

	@Override
	public void autonomousInit() {
		//m_AutonomousCommand.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {

	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public LifterRaiseCommand createRaiseCommand() {
		return new LifterRaiseCommand( this.lifterSubsystem, DataLogger.realMatchConfiguration());
	}

	@Override
	public LifterLowerCommand createLowerCommand() {
		return new LifterLowerCommand( this.lifterSubsystem, DataLogger.realMatchConfiguration());
	}
}
