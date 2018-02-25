package frc.team281.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mockito;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.subsystems.Position;
import frc.team281.robot.subsystems.PositionBuffer;
import frc.team281.robot.subsystems.drive.EncoderInchesConverter;
import frc.team281.robot.subsystems.drive.FourWheelPositionDrive;
import frc.team281.robot.talons.DriveEncoderStatus;
import frc.team281.robot.talons.FourTalonGroup;

public class TestFourWheelPositionDrive extends BaseTest{

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

		FourWheelPositionDrive drive = FourWheelPositionDrive
			.defaults(g)
			.withPrettySafeCurrentLimits()
			.brakeInNeutral()
			.defaultDirectionSettings()
			.noMotorOutputLimits()
			.noMotorStartupRamping()
			.withGains(0, 0, 0, 0)
			.withMotionProfile(0, 0, 0)
			.build();
		drive.activate();
		//make sure the talons are in position mode
		verify(lf).set(ControlMode.MotionMagic, 0);
		verify(lr).set(ControlMode.MotionMagic, 0);
		verify(rf).set(ControlMode.MotionMagic, 0);
		verify(rr).set(ControlMode.MotionMagic, 0);		
	}
	
	@Test
	public void testBuildingAdvanced() {
		WPI_TalonSRX lf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX lr = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rf = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rr = Mockito.mock(WPI_TalonSRX.class);
		FourTalonGroup g = new FourTalonGroup();
		g.frontLeft = lf;
		g.frontRight =rf;
		g.rearLeft = lr;
		g.rearRight = rr;

		FourWheelPositionDrive drive = FourWheelPositionDrive
			.defaults(g)
			.withCurrentLimits(30, 25, 200)
			.coastInNeutral()
			.withDirections(false, true)
			.limitMotorOutputs(1.0, 0.1)
			.withMotorRampUpOnStart(1.0)
			.withGains(0, 0, 0, 0)
			.withMotionProfile(0, 0, 0)
			.build();
		
		drive.activate();
		//make sure the talons are in position mode
		verify(lf).set(ControlMode.MotionMagic, 0);
		verify(lr).set(ControlMode.MotionMagic, 0);
		verify(rf).set(ControlMode.MotionMagic, 0);
		verify(rr).set(ControlMode.MotionMagic, 0);		
		
				
		PositionBuffer b = new PositionBuffer();
		b.addPosition( new Position(5.0,6.0));
		DriveEncoderStatus des = new DriveEncoderStatus(new EncoderInchesConverter(50));
		
		drive.updatePosition(b, des);
		
		Position c = drive.getCurrentCommand();
		assertEquals(c.getLeftInches(),5.0,0.001);
		assertEquals(c.getRightInches(),6.0,0.001);

		verify(lf).set(50*5);
		verify(lr).set(50*5);
		verify(rf).set(50*6);
		verify(rr).set(50*6);		
		
	}	
}
