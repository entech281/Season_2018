package frc.team281.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team281.robot.commands.LifterStopCommand;


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
    private Joystick controlPanel;
    
    private CommandFactory factory;
    
    private JoystickButton lifterRaiseButton;
    private JoystickButton lifterLowerButton; 
    private JoystickButton lifterGroundButton;  
    private JoystickButton lifterTopButton;
    private JoystickButton grabberLoadButton;
    private JoystickButton grabberShootButton;
    private JoystickButton grabberOpenButton;
    private JoystickButton wristUpButton;
    
    public static class LifterHeights {
        public static final double SCALE_HIGH = 100;
        public static final double SCALE_MID = 80;
        public static final double SCALE_LOW = 60;
        public static final double FENCE = 40;
        public static final double GROUND = 20;
    }
    
    public OperatorInterface(CommandFactory factory) {
        this.factory = factory;
    }

    public void initialize() {
        
        
        driveJoystick = new Joystick(Robot.robotMap.DriveJoystick.PORT);
        controlPanel = new Joystick(Robot.robotMap.ControlPanel.PORT);
        
        lifterRaiseButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.LIFTER_RAISE);
        lifterRaiseButton.whileHeld(factory.createLifterRaiseCommand());
        lifterRaiseButton.whenReleased(factory.createLifterStopCommand());

        
        lifterLowerButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.LIFTER_LOWER);
        lifterLowerButton.whileHeld(factory.createLifterLowerCommand());
        lifterLowerButton.whenReleased(factory.createLifterStopCommand());        

        lifterGroundButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.LIFTER_TO_GROUND);
        lifterGroundButton.whenPressed(factory.createLifterHomeCommand());

        
        lifterTopButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.LIFTER_TO_TOP);
        lifterTopButton.whenPressed(factory.createLifterTopCommand());

        wristUpButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.WRIST_UP);
        wristUpButton.whenPressed(factory.createWristPivotUpCommand());
        wristUpButton.whenReleased(factory.createWristPivotDownCommand());

        grabberLoadButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.GRABBER_LOAD);
        grabberLoadButton.whenPressed(factory.createGrabberLoadCommand());
        grabberLoadButton.whenReleased(factory.createGrabberStopCommand());
        
        grabberShootButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.GRABBER_SHOOT);
        grabberShootButton.whenPressed(factory.createGrabberShootCommand());
        grabberShootButton.whenReleased(factory.createGrabberStopCommand());
        
        grabberOpenButton = new JoystickButton(controlPanel, Robot.robotMap.ControlPanel.Buttons.GRABBER_OPEN);
        grabberOpenButton.whenPressed(factory.createGrabberOpenCommand());
        grabberOpenButton.whenReleased(factory.createGrabberCloseCommand());

    }

    @Override
    public DriveInstruction getNextInstruction() {
        return new DriveInstruction(
                -(1.0)*driveJoystick.getX(),
                driveJoystick.getY()
                );
    }

    public static double adjustJoystickSoftness(double softnessFactor, double rawValue) {
        boolean isNegative = rawValue < 0;
        double adjusted = isNegative? Math.pow(-rawValue, softnessFactor):Math.pow(rawValue, softnessFactor);
        return isNegative?-adjusted:adjusted;
    }
}
