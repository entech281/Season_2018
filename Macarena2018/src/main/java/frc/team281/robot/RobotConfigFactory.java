package frc.team281.robot;

public class RobotConfigFactory {
    
    @SuppressWarnings("unused")
    private RobotConfig matchBotConfig = new RobotConfig();
    @SuppressWarnings("unused")
    private RobotConfig practiceBotConfig = new RobotConfig();
    
    public RobotConfig configToMatch() {
        matchBotConfig.CAN.PC_MODULE = 10;
        matchBotConfig.CAN.FRONT_LEFT_MOTOR = 1;
        matchBotConfig.CAN.FRONT_RIGHT_MOTOR = 2;
        matchBotConfig.CAN.REAR_LEFT_MOTOR = 3;
        matchBotConfig.CAN.REAR_RIGHT_MOTOR = 4;
        matchBotConfig.CAN.Grabber.MOTOR_LEFT = 8;
        matchBotConfig.CAN.Grabber.MOTOR_RIGHT = 7;
        matchBotConfig.CAN.Lifter.MOTOR_ONE = 5;
        matchBotConfig.CAN.Lifter.MOTOR_TWO = 6;
        matchBotConfig.DriveJoystick.PORT = 0;
        matchBotConfig.DriveJoystick.Buttons.FIELD_ABSOLUTE_DRIVING = 11;
        matchBotConfig.ControlPanel.PORT = 1;
        matchBotConfig.ControlPanel.Buttons.LIFTER_RAISE = 4;
        matchBotConfig.ControlPanel.Buttons.LIFTER_LOWER = 5;
        matchBotConfig.ControlPanel.Buttons.LIFTER_TO_TOP = 3;
        matchBotConfig.ControlPanel.Buttons.LIFTER_TO_GROUND = 2;
        matchBotConfig.ControlPanel.Buttons.GRABBER_LOAD = 8;
        matchBotConfig.ControlPanel.Buttons.GRABBER_SHOOT = 9;
        matchBotConfig.ControlPanel.Buttons.GRABBER_OPEN = 7;
        matchBotConfig.ControlPanel.Buttons.WRIST_UP = 6;
        matchBotConfig.DigitalIO.LIFTER_AT_BOTTOM = 0;
        matchBotConfig.DigitalIO.LIFTER_AT_TOP = 1;
        matchBotConfig.DigitalIO.GRABBER_CUBE_LOADED = 2;
        matchBotConfig.DigitalIO.LEFT_SWITCH_POSITION = 3;
        matchBotConfig.DigitalIO.RIGHT_SWITCH_POSITION = 4;
        matchBotConfig.DigitalIO.PREFERENCE_SWITCH = 5;
        matchBotConfig.PCM.Grabber.INSIDE = 2;
        matchBotConfig.PCM.Grabber.OUTSIDE = 3;
        matchBotConfig.PCM.Wrist.UP = 0;
        matchBotConfig.PCM.Wrist.DOWN = 0;
        matchBotConfig.JOYSTICK_X_SOFTNESS = 1.5;
        return matchBotConfig;
    }
    public RobotConfig configToPractice() {
        //TODO Fix port #.
        matchBotConfig.CAN.PC_MODULE = 10;
        matchBotConfig.CAN.FRONT_LEFT_MOTOR = 1;
        matchBotConfig.CAN.FRONT_RIGHT_MOTOR = 2;
        matchBotConfig.CAN.REAR_LEFT_MOTOR = 3;
        matchBotConfig.CAN.REAR_RIGHT_MOTOR = 4;
        matchBotConfig.CAN.Grabber.MOTOR_LEFT = 8;
        matchBotConfig.CAN.Grabber.MOTOR_RIGHT = 7;
        matchBotConfig.CAN.Lifter.MOTOR_ONE = 5;
        matchBotConfig.CAN.Lifter.MOTOR_TWO = 6;
        matchBotConfig.DriveJoystick.PORT = 0;
        matchBotConfig.DriveJoystick.Buttons.FIELD_ABSOLUTE_DRIVING = 11;
        matchBotConfig.ControlPanel.PORT = 1;
        matchBotConfig.ControlPanel.Buttons.LIFTER_RAISE = 4;
        matchBotConfig.ControlPanel.Buttons.LIFTER_LOWER = 5;
        matchBotConfig.ControlPanel.Buttons.LIFTER_TO_TOP = 3;
        matchBotConfig.ControlPanel.Buttons.LIFTER_TO_GROUND = 2;
        matchBotConfig.ControlPanel.Buttons.GRABBER_LOAD = 8;
        matchBotConfig.ControlPanel.Buttons.GRABBER_SHOOT = 9;
        matchBotConfig.ControlPanel.Buttons.GRABBER_OPEN = 7;
        matchBotConfig.ControlPanel.Buttons.WRIST_UP = 6;
        matchBotConfig.DigitalIO.LIFTER_AT_BOTTOM = 0;
        matchBotConfig.DigitalIO.LIFTER_AT_TOP = 1;
        matchBotConfig.DigitalIO.GRABBER_CUBE_LOADED = 2;
        matchBotConfig.DigitalIO.LEFT_SWITCH_POSITION = 3;
        matchBotConfig.DigitalIO.RIGHT_SWITCH_POSITION = 4;
        matchBotConfig.DigitalIO.PREFERENCE_SWITCH = 5;
        matchBotConfig.PCM.Grabber.INSIDE = 2;
        matchBotConfig.PCM.Grabber.OUTSIDE = 3;
        matchBotConfig.PCM.Wrist.UP = 0;
        matchBotConfig.PCM.Wrist.DOWN = 0;
        matchBotConfig.JOYSTICK_X_SOFTNESS = 1.5;
        matchBotConfig.JOYSTICK_Y_SOFTNESS = 1.5;
        return matchBotConfig;
    }
    
}
