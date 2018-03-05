package frc.team281.robot;

public class RobotConfigFactory {
    
    @SuppressWarnings("unused")
    private RobotConfig botConfig = new RobotConfig();
    @SuppressWarnings("unused")
    private RobotConfig practiceBotConfig = new RobotConfig();
    
    public RobotConfig configToMatch() {
        botConfig.CAN.PC_MODULE = 10;
        botConfig.CAN.FRONT_LEFT_MOTOR = 1;
        botConfig.CAN.FRONT_RIGHT_MOTOR = 2;
        botConfig.CAN.REAR_LEFT_MOTOR = 3;
        botConfig.CAN.REAR_RIGHT_MOTOR = 4;
        botConfig.CAN.Grabber.MOTOR_LEFT = 8;
        botConfig.CAN.Grabber.MOTOR_RIGHT = 7;
        botConfig.CAN.Lifter.MOTOR_ONE = 5;
        botConfig.CAN.Lifter.MOTOR_TWO = 6;
        botConfig.DriveJoystick.PORT = 0;
        botConfig.DriveJoystick.Buttons.FIELD_ABSOLUTE_DRIVING = 11;
        botConfig.ControlPanel.PORT = 1;
        botConfig.ControlPanel.Buttons.LIFTER_RAISE = 4;
        botConfig.ControlPanel.Buttons.LIFTER_LOWER = 5;
        botConfig.ControlPanel.Buttons.LIFTER_TO_TOP = 3;
        botConfig.ControlPanel.Buttons.LIFTER_TO_GROUND = 2;
        botConfig.ControlPanel.Buttons.GRABBER_LOAD = 8;
        botConfig.ControlPanel.Buttons.GRABBER_SHOOT = 9;
        botConfig.ControlPanel.Buttons.GRABBER_OPEN = 7;
        botConfig.ControlPanel.Buttons.WRIST_UP = 6;
        botConfig.DigitalIO.LIFTER_AT_BOTTOM = 0;
        botConfig.DigitalIO.LIFTER_AT_TOP = 1;
        botConfig.DigitalIO.GRABBER_CUBE_LOADED = 2;
        botConfig.DigitalIO.LEFT_SWITCH_POSITION = 3;
        botConfig.DigitalIO.RIGHT_SWITCH_POSITION = 4;
        botConfig.DigitalIO.PREFERENCE_SWITCH = 5;
        botConfig.PCM.Grabber.INSIDE = 2;
        botConfig.PCM.Grabber.OUTSIDE = 3;
        botConfig.PCM.Wrist.UP = 0;
        botConfig.PCM.Wrist.DOWN = 0;
        botConfig.JOYSTICK_X_SOFTNESS = 1.5;
        return botConfig;
    }
    public RobotConfig configToPractice() {
        //TODO Fix port #.
        botConfig.CAN.PC_MODULE = 10;
        botConfig.CAN.FRONT_LEFT_MOTOR = 1;
        botConfig.CAN.FRONT_RIGHT_MOTOR = 2;
        botConfig.CAN.REAR_LEFT_MOTOR = 3;
        botConfig.CAN.REAR_RIGHT_MOTOR = 4;
        botConfig.CAN.Grabber.MOTOR_LEFT = 8;
        botConfig.CAN.Grabber.MOTOR_RIGHT = 7;
        botConfig.CAN.Lifter.MOTOR_ONE = 5;
        botConfig.CAN.Lifter.MOTOR_TWO = 6;
        botConfig.DriveJoystick.PORT = 0;
        botConfig.DriveJoystick.Buttons.FIELD_ABSOLUTE_DRIVING = 11;
        botConfig.ControlPanel.PORT = 1;
        botConfig.ControlPanel.Buttons.LIFTER_RAISE = 4;
        botConfig.ControlPanel.Buttons.LIFTER_LOWER = 5;
        botConfig.ControlPanel.Buttons.LIFTER_TO_TOP = 3;
        botConfig.ControlPanel.Buttons.LIFTER_TO_GROUND = 2;
        botConfig.ControlPanel.Buttons.GRABBER_LOAD = 8;
        botConfig.ControlPanel.Buttons.GRABBER_SHOOT = 9;
        botConfig.ControlPanel.Buttons.GRABBER_OPEN = 7;
        botConfig.ControlPanel.Buttons.WRIST_UP = 6;
        botConfig.DigitalIO.LIFTER_AT_BOTTOM = 0;
        botConfig.DigitalIO.LIFTER_AT_TOP = 1;
        botConfig.DigitalIO.GRABBER_CUBE_LOADED = 2;
        botConfig.DigitalIO.LEFT_SWITCH_POSITION = 3;
        botConfig.DigitalIO.RIGHT_SWITCH_POSITION = 4;
        botConfig.DigitalIO.PREFERENCE_SWITCH = 5;
        botConfig.PCM.Grabber.INSIDE = 2;
        botConfig.PCM.Grabber.OUTSIDE = 3;
        botConfig.PCM.Wrist.UP = 0;
        botConfig.PCM.Wrist.DOWN = 0;
        botConfig.JOYSTICK_X_SOFTNESS = 1.5;
        botConfig.JOYSTICK_Y_SOFTNESS = 1.5;
        return botConfig;
    }
    
}
