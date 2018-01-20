package frc.team281.robot.subsystems;

import frc.team281.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class ShooterOutTakeSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	WPI_TalonSRX m_shooterOuttake = new WPI_TalonSRX(RobotMap.shooterOuttakeCANid);

	public void stop() {
		m_shooterOuttake.set(0);
	}

	public void shooterOutBelow() {
		m_shooterOuttake.set(-.5);
	}
	public void shooterOutAbove() {
		m_shooterOuttake.set(.5);
	}
		public void initDefaultCommand() {
    }
}
