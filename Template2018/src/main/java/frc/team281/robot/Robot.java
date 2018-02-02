
package frc.team281.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team281.robot.commands.LifterLowerCommand;
import frc.team281.robot.commands.LifterRaiseCommand;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.subsystems.RealDriveSubsystem;
import frc.team281.robot.subsystems.RealLifterSubsystem;

/**
 * The robot, only used in the real match. Cannot be instantated outside of the
 * match, so we want to minimize its functionality here.
 * 
 * In short-- anything in here can't be tested outside of running the real
 * robot, so we want to be careful.
 * 
 * Since the robot knows about its subsystems, it makes sense for Robot to
 * implement CommandFactory-- though that is not strictly necessary. In fact, it
 * would be easy to move all of the subsystems out into another class, and have
 * that one implmeent CommandFactory
 */
public class Robot extends TimedRobot implements CommandFactory {

	private RealDriveSubsystem driveSubsystem;
	private RealLifterSubsystem lifterSubsystem;
	private OperatorInterface operatorInterface;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		// create the objects for the real match
		DataLoggerFactory.configureForMatch();
		lifterSubsystem = new RealLifterSubsystem();
		operatorInterface = new OperatorInterface(this);
		driveSubsystem = new RealDriveSubsystem(operatorInterface);

		driveSubsystem.initialize();
		operatorInterface.initialize();
		operatorInterface.initialize();
	}

	@Override
	public void autonomousInit() {
		// m_AutonomousCommand.start();
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
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
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
		return new LifterRaiseCommand(this.lifterSubsystem);
	}

	@Override
	public LifterLowerCommand createLowerCommand() {
		return new LifterLowerCommand(this.lifterSubsystem);
	}
}
