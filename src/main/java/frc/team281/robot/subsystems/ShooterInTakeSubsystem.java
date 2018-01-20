package frc.team281.robot.subsystems;

import frc.team281.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class ShooterInTakeSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	WPI_TalonSRX m_shooterIntake = new WPI_TalonSRX(RobotMap.shooterIntakeCANid);

	public void stop() {
		m_shooterIntake.set(0);
	}

	public void shooterIn() {
		m_shooterIntake.set(.5);
	}

    public void initDefaultCommand() {
    }
}
