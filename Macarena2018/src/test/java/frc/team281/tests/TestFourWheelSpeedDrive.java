package frc.team281.tests;

import org.junit.Test;

import frc.team281.robot.talons.FourWheelSpeedDrive;

public class TestFourWheelSpeedDrive {

	@Test
	public void testBuildingMostBasicOne() {
		FourWheelSpeedDrive drive = FourWheelSpeedDrive
				.defaults()
				.withPrettySafeCurrentLimits()
				.brakeInNeutral()
				.defaultDirectionSettings()
				.noMotorOutputLimits()
				.build();
	}
}
