package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team281.robot.RobotMap;

public class ProngsSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Solenoid m_upSolenoid = new Solenoid(RobotMap.PCModuleCANid, RobotMap.prongsUpSolenoid);
    Solenoid m_downSolenoid = new Solenoid(RobotMap.PCModuleCANid, RobotMap.prongsDownSolenoid);

    public void stop() {
    }

    public void raise() {
        m_upSolenoid.set(false);
        m_downSolenoid.set(false);
    }

    public void lower() {
        m_upSolenoid.set(true);
        m_downSolenoid.set(true);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new DriveUsingJoystick(this));
    }
}
