package frc.team281.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * Note use of inner classes. Subscoping is much more powerful and expressive
 * than concatentation
 */
public class RobotMap {

    public static class CAN {
        public static final int FRONT_LEFT_MOTOR = 0;
        public static final int FRONT_RIGHT_MOTOR = 2;
        public static final int REAR_LEFT_MOTOR = 1;
        public static final int REAR_RIGHT_MOTOR = 3;

        public static final int SHOOTER_INTAKE = 4;
        public static final int LIFTER_UP_SOLENOID = 5;
        public static final int PC_MODULE = 10;

    }

    public static class JoystickPorts {
        public static final int JOYSTICK_1 = 1;
    }

    public static class JoystickButtons {
        public static final int SHOOTER = 1;
        public static final int INTAKE = 2;
        public static final int LIFTER_UP = 3;
        public static final int LIFTER_DOWN = 4;

    }

    public static class PCMChannel {
        public static final int LIFTER_UP_SOLENOID = 2;
        public static final int LIFTER_DOWN_SOLENOID = 3;

    }

    public static class DigitalIO {
        public static final int UPPER_LIFTER_LIMIT = 0;
        public static final int LOWER_LFTER_LIMIT = 1;

    }

    public static final double JOYSTICK_Y_SOFTNESS = 1.5;
    public static final double JOYSTICK_X_SOFTNESS = 1.5;

}
