package frc.team281.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public static Joystick driveJoystick = new Joystick(RobotMap.driveJoystick);

    public OI() {

    }

    public double getDriveJoystickForward() {
        double a = driveJoystick.getY();
        if (a < 0) {
            return -Math.pow(-a, RobotMap.JoystickYSoftness);
        } else {
            return Math.pow(a, RobotMap.JoystickYSoftness);
        }
    }

    public double getDriveJoystickLateral() {
        double a = driveJoystick.getX();
        if (a < 0) {
            return -Math.pow(-a, RobotMap.JoystickXSoftness);
        } else {
            return Math.pow(a, RobotMap.JoystickXSoftness);
        }
    }

    public double getJoystickMagnitude() {
        return Math.sqrt(Math.pow(getDriveJoystickForward(), 2) + Math.pow(getDriveJoystickLateral(), 2));
    }

    public double getJoystickDirection() {
        return Math.atan2(getDriveJoystickForward(), getDriveJoystickLateral());
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
