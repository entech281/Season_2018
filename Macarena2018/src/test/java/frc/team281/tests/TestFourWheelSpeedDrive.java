package frc.team281.tests;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.subsystems.drive.FourWheelSpeedDrive;
import frc.team281.robot.talons.FourTalonGroup;

public class TestFourWheelSpeedDrive {

	
	@Test
	public void testBuildingWithBasics() {
		WPI_TalonSRX lf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX lr = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rr = Mockito.mock(WPI_TalonSRX.class);
		
		FourTalonGroup g = new FourTalonGroup();
		g.frontLeft = lf;
		g.frontRight =rf;
		g.rearLeft = lr;
		g.rearRight = rr;

		FourWheelSpeedDrive drive = FourWheelSpeedDrive
			.defaults(g)
			.withPrettySafeCurrentLimits()
			.brakeInNeutral()
			.defaultDirectionSettings()
			.noMotorOutputLimits()
			.build();
		drive.activate();
		//make sure the talons are in speed mode
		verify(lf).set(ControlMode.PercentOutput, 0);
		verify(lr).set(ControlMode.PercentOutput, 0);
		verify(rf).set(ControlMode.PercentOutput, 0);
		verify(rr).set(ControlMode.PercentOutput, 0);
	}
	
	@Test
	public void testBuildingWithAdvancedOptions() {
		WPI_TalonSRX lf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX lr = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rr = Mockito.mock(WPI_TalonSRX.class);
		
		FourTalonGroup g = new FourTalonGroup();
		g.frontLeft = lf;
		g.frontRight =rf;
		g.rearLeft = lr;
		g.rearRight = rr;

		FourWheelSpeedDrive drive = FourWheelSpeedDrive
			.defaults(g)
			.withCurrentLimits(30, 25, 200)
			.coastInNeutral()
			.withDirections(false, true)
			.limitMotorOutputs(1.0, 0.1)
			.build();
		
		drive.activate();
		
		//make sure the talons are in speed mode
		verify(lf).set(ControlMode.PercentOutput, 0);
		verify(lr).set(ControlMode.PercentOutput, 0);
		verify(rf).set(ControlMode.PercentOutput, 0);
		verify(rr).set(ControlMode.PercentOutput, 0);
		
		drive.tankDrive(0.5, 1.0);
		verify(lf).set(0.5);
		verify(lr).set(0.5);
		verify(rf).set(1.0);
		verify(rr).set(1.0);
		
	}	
	
	@Test
	public void testArcadeDrive() {
		WPI_TalonSRX lf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX lr = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rr = Mockito.mock(WPI_TalonSRX.class);
		
		FourTalonGroup g = new FourTalonGroup();
		g.frontLeft = lf;
		g.frontRight =rf;
		g.rearLeft = lr;
		g.rearRight = rr;

		FourWheelSpeedDrive drive = FourWheelSpeedDrive
			.defaults(g)
			.withCurrentLimits(30, 25, 200)
			.coastInNeutral()
			.withDirections(false, true)
			.limitMotorOutputs(1.0, 0.1)
			.build();
		
		drive.activate();

		drive.arcadeDrive(0.5, 0.1);
		verify(lf).set(0.5);
		verify(lr).set(0.5);
		verify(rf).set(0.4);
		verify(rr).set(0.4);
		
	}		
}
