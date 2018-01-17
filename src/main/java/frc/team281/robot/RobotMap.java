package frc.team281.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// CAN ids (must be unique)
	public static int frontLeftMotorCANid = 0;
	public static int frontRightMotorCANid = 2;
	public static int rearLeftMotorCANid = 1;
	public static int rearRightMotorCANid = 3;
	public static int PCModuleCANid = 10;

	public static int driveJoystick = 0;
	
	// Pneumatic solenoids
	public static int prongsUpSolenoid = 0;
	public static int prongsDownSolenoid = 1;
	
	// Driver Joystick Buttons
	public static int prongsUpButton = 5;
	public static int prongsDownButton = 3;
}
