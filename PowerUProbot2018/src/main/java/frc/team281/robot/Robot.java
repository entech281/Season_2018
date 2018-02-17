
package frc.team281.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team281.robot.subsystems.DriveSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot implements CommandCreator {

    public final DriveSubsystem _driveSubsystem = new DriveSubsystem();
    public static OI oi;
    public static double x;
    public static double y;
    public static double theta;
    public static WPI_TalonSRX FrontRightMotor;
    public static WPI_TalonSRX FrontLeftMotor;
    public static WPI_TalonSRX RearRightMotor;
    public static WPI_TalonSRX RearLeftMotor;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    private void talonConfig() {
        FrontRightMotor = new WPI_TalonSRX(RobotMap.driveMotors.frontRightMotorCANid);
        FrontLeftMotor = new WPI_TalonSRX(RobotMap.driveMotors.frontLeftMotorCANid);
        RearRightMotor = new WPI_TalonSRX(RobotMap.driveMotors.rearRightMotorCANid);
        RearLeftMotor = new WPI_TalonSRX(RobotMap.driveMotors.rearLeftMotorCANid);
    }

    @Override
    public void robotInit() {
        oi = new OI();
        Compressor compressor = new Compressor(RobotMap.PCModuleCANid);
        compressor.start();
        talonConfig();

    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
