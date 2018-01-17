package frc.team281.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team281.robot.commands.ProngsUp;
import frc.team281.robot.commands.ProngsDown;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick _driveJoystick = new Joystick(RobotMap.driveJoystick);
	private static JoystickButton m_prongUpButton = new JoystickButton(_driveJoystick, RobotMap.prongsUpButton);
	private static JoystickButton m_prongDownButton = new JoystickButton(_driveJoystick, RobotMap.prongsDownButton);
	
	public void teleopPeriodic() {
	    m_prongUpButton.whenPressed(new ProngsUp(Robot._prongsSubsystem));
	    m_prongDownButton.whenPressed(new ProngsDown(Robot._prongsSubsystem));
	}
	
	public double getDriveJoystickForward() {
		return _driveJoystick.getY();
	}
	public double getDriveJoystickLateral() {
		return _driveJoystick.getX();
	}
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
