package frc.team281.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// CAN ids (must be unique)
	public static int frontLeftMotorCANid  = 0;
	public static int frontRightMotorCANid = 2;
	public static int rearLeftMotorCANid   = 1;
	public static int rearRightMotorCANid  = 3;
	public static int PCModuleCANid        = 10;
	public static int driveJoystick        = 0;
	
	//shooter CANids
	public static int shooterIntakeCANid  = 4;
	public static int shooterOuttakeCANid = 5;
	// Pneumatic solenoids
	public static int prongsUpSolenoid   = 0;
	public static int prongsDownSolenoid = 1;
	
	// Driver Joystick Buttons
	public static int prongsUpButton   = 5;
	public static int prongsDownButton = 3;
	public static int shootUpButton    = 6;
	public static int shootDownButton  = 4;
	public static int InTakeButton     = 2;
	public static int Effshooter     = 1;
	//Joystick drive adjustments 
	public static double JoystickYSoftness = 1.5;
	public static double JoystickXSoftness = 1.5;
	//CompleteBallShoot adjustments
	public static int timeUp=6;
	public static int timeDown=16;
}
