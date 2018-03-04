package frc.team281.robot;

public class RobotConfig{
    
    public Can CAN = new Can();
    public DriveJoystick DriveJoystick = new DriveJoystick();
    public ControlPanel ControlPanel = new ControlPanel();
    public PCMChannel PCMChannel = new PCMChannel();
    public DigitalIoSettings DigitalIO = new DigitalIoSettings();
    public PCM PCM = new PCM();
    
    public static class Can {
        
        public Lifter Lifter = new Lifter();
        public Grabber Grabber = new Grabber();
        
        public int FRONT_LEFT_MOTOR;
        public int FRONT_RIGHT_MOTOR;
        public int REAR_LEFT_MOTOR;
        public int REAR_RIGHT_MOTOR;
        
        public static class Lifter {
            public int MOTOR_ONE;
            public int MOTOR_TWO;  
        }
        
        public static class Grabber {
            public int MOTOR_LEFT;
            public int MOTOR_RIGHT;
        }
        
        public int PC_MODULE;

    }
    public static class DriveJoystick {
        
        public int PORT;
        public Buttons Buttons = new Buttons();
        
        public static class Buttons {   
            public int FIELD_ABSOLUTE_DRIVING;
        }
    }
    
    public static class ControlPanel {
        
        public int PORT;
        public Buttons Buttons = new Buttons();
        
        public static class Buttons {
            public int LIFTER_RAISE;
            public int LIFTER_LOWER;
            public int LIFTER_TO_TOP;
            public int LIFTER_TO_GROUND;
            public int GRABBER_LOAD;
            public int GRABBER_SHOOT;
            public int GRABBER_OPEN;
            public int WRIST_UP;
        }
    }

    public static class PCMChannel {

    }

    public static class DigitalIoSettings {
        public int LIFTER_AT_BOTTOM;
        public int LIFTER_AT_TOP;
        public int GRABBER_CUBE_LOADED;
        public int LEFT_SWITCH_POSITION;
        public int RIGHT_SWITCH_POSITION;
        public int PREFERENCE_SWITCH;

    }
    
    public static class PCM {
        
        public Grabber Grabber = new Grabber();
        public Wrist Wrist = new Wrist();
        
        public static class Grabber {
            public int OUTSIDE;
            public int INSIDE;
        }
        
        public static class Wrist {
            public int UP;
            public int DOWN;
        }
    }
    
    public double JOYSTICK_Y_SOFTNESS;
    public double JOYSTICK_X_SOFTNESS;
    
}
