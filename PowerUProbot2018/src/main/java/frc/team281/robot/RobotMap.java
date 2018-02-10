package frc.team281.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
    public static int timeOutMs=500;
    
    // CAN ids (must be unique)
    
    public class driveMotors{
        public static final int frontLeftMotorCANid  = 0;
        public static final int frontRightMotorCANid = 2;
        public static final int rearLeftMotorCANid   = 1;
        public static final int rearRightMotorCANid  = 3;
        public static final double secondsFromNeutralToFull= 1.;
    }
	public static int leftCubeGripperCANid = 0;
	public static int rightCubeGripperCANid= 0;
	public static int clawCubeGripperCANid = 0;
	
	public static int elevatorCANid        = 0;
	public static int cubeWristCANid       = 0;
	
	public static int leftRampMotorCANid   = 0;
	public static int rightRampMotorCANid  = 0;
	public static int PCModuleCANid        = 10;
	public static int driveJoystick        = 0;
	
	//Joystick drive adjustments 
	public static double JoystickYSoftness = 1.5;
	public static double JoystickXSoftness = 1.5;
}
