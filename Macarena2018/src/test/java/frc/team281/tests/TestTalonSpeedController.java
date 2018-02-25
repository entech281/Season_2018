package frc.team281.tests;

import org.junit.Test;
import org.mockito.Mockito;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.talons.TalonSpeedController;

public class TestTalonSpeedController {

	@Test
	public void testUsingTheDefaults() {
		WPI_TalonSRX talon = Mockito.mock(WPI_TalonSRX.class);
		TalonSpeedController speedController = TalonSpeedController.defaults(talon).withPrettySafeCurrentLimits()
				.coastInNeutral().defaultDirectionSettings().noMotorOutputLimits().build();

	}

	@Test
	public void testComplexConfiguration() {
		WPI_TalonSRX talon = Mockito.mock(WPI_TalonSRX.class);
		TalonSpeedController speedController = TalonSpeedController.defaults(talon).withCurrentLimits(35, 30, 200)
				.brakeInNeutral().withDirections(false, false).limitMotorOutputs(1.0, 0.1).build();
	}
}
