
package frc.team281.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team281.robot.commands.LifterLowerCommand;
import frc.team281.robot.commands.LifterRaiseCommand;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.subsystems.RealLifterSubsystem;
import frc.team281.robot.subsystems.drive.BaseDriveSubsystem.DriveMode;
import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

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
public class Robot extends IterativeRobot implements CommandFactory {

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
	}

	@Override
	public void autonomousInit() {
		driveSubsystem.setMode(DriveMode.CALIBRATE);
		
		//Now run some commands.
	}


	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		driveSubsystem.setMode(DriveMode.DISABLED);

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		driveSubsystem.setMode(DriveMode.SPEED_DRIVE);
	}

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
