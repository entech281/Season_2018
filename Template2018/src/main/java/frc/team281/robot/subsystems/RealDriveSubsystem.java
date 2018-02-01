package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team281.robot.DriveInstructionSource;
import frc.team281.robot.RobotMap;
import frc.team281.robot.logger.DataLogger;

public class RealDriveSubsystem extends BaseDriveSubsystem {

	private WPI_TalonSRX frontLeftMotor;
	private WPI_TalonSRX frontRightMotor;
	private WPI_TalonSRX rearLeftMotor;
	private WPI_TalonSRX rearRightMotor;
	private DifferentialDrive drive;

	public RealDriveSubsystem(DataLogger dataLogger,DriveInstructionSource driveInstructionSource) {
		super(dataLogger,driveInstructionSource);
	}

	@Override
	public void initialize() {
		frontLeftMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
		frontRightMotor = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);
		rearLeftMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
		rearRightMotor = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);

		drive = new DifferentialDrive(new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
				new SpeedControllerGroup(frontRightMotor, rearRightMotor));

	}

	public void stop() {
		drive.tankDrive(0., 0.);
	}

	public void arcadeDrive(double forw, double turn) {
		drive.arcadeDrive(-forw, turn, true);
	}

	public void tankDrive(double left, double right) {
		drive.tankDrive(left, right, true);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new DriveUsingJoystick(this));
	}

}
