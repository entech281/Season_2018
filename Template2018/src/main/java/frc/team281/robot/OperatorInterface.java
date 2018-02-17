package frc.team281.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;;

/**
 * Reads the hardware that interfaces with real users, and issues commands to a
 * Robot.
 * 
 * Note that this class is not dependent on any Robot subsystems, or the Robot
 * itself.
 * 
 * It translates joysticks and buttons into commands and DriveInstructions
 */
public class OperatorInterface implements DriveInstructionSource {

	private Joystick driveJoystick;
	private JoystickButton lifterUpButton;
	private JoystickButton lifterDownButton;
	private CommandFactory factory;

	public OperatorInterface(CommandFactory factory) {
		this.factory = factory;
	}

	public void initialize() {
		driveJoystick = new Joystick(RobotMap.JoystickPorts.JOYSTICK_1);
		lifterUpButton = new JoystickButton(driveJoystick, RobotMap.JoystickButtons.LIFTER_UP);
		lifterDownButton = new JoystickButton(driveJoystick, RobotMap.JoystickButtons.LIFTER_DOWN);
		lifterUpButton.whenPressed(factory.createRaiseCommand());
		lifterDownButton.whenPressed(factory.createLowerCommand());
	}

	@Override
	public DriveInstruction getNextInstruction() {
		return new DriveInstruction(driveJoystick.getY(), driveJoystick.getX());
	}

}
