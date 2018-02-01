package frc.team281.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OperatorInterface implements DriveInstructionSource{

	private Joystick driveJoystick;
    private JoystickButton lifterUpButton;
    private JoystickButton lifterDownButton;
    private CommandFactory factory;
    
    public OperatorInterface( CommandFactory factory) {
    	this.factory = factory;
    }

    public void initialize() {
    	driveJoystick = new Joystick(RobotMap.JoystickPorts.JOYSTICK_1);
        lifterUpButton = new JoystickButton(driveJoystick, RobotMap.JoystickButtons.LIFTER_UP);
        lifterDownButton = new JoystickButton(driveJoystick, RobotMap.JoystickButtons.LIFTER_DOWN);  	
    }
    
    public OperatorInterface() {
    	lifterUpButton.whenPressed(factory.createRaiseCommand());
    	lifterDownButton.whenPressed(factory.createLowerCommand());
    
    }

	@Override
	public DriveInstruction getNextInstruction() {
		return new DriveInstruction(
			adjustJoystickSoftness( RobotMap.JOYSTICK_Y_SOFTNESS, driveJoystick.getY()),
			adjustJoystickSoftness( RobotMap.JOYSTICK_X_SOFTNESS, driveJoystick.getX())
		);
	}
	
	protected double adjustJoystickSoftness(double softnessFactor, double rawValue) {
		double adjusted = Math.pow(rawValue, softnessFactor);
		
		if ( rawValue < 0 ) {
			return -adjusted;
		}
		else {
			return adjusted;
		}
	}
}
