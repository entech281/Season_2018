package frc.team281.tests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.mockito.Mockito;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.subsystems.TalonSettings;
import frc.team281.robot.subsystems.TalonSettingsBuilder;
import frc.team281.robot.subsystems.drive.FourDriveTalonCalibratorController;
import frc.team281.robot.subsystems.drive.FourTalonsWithSettings;

public class TestFourDriveTalonCalibratorController extends BaseTest{

	@Test
	public void testCalibrationDriveController() {
		
		//not a good test yet-- just constructs it to make sure there are no problems there.
		//TODO: mock out the talons to return bad encoder readings
		
		long CALIBRATION_TIME_MS = 200;
		WPI_TalonSRX frontLeft = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX frontRight = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rearLeft = Mockito.mock(WPI_TalonSRX.class);
		WPI_TalonSRX rearRight = Mockito.mock(WPI_TalonSRX.class);
		
		FourTalonsWithSettings settings = new FourTalonsWithSettings(frontLeft,frontRight,rearLeft,rearRight);


		
		TalonSettings leftSpeedSettingsToAdjust = TalonSettingsBuilder.defaults().withCurrentLimits(35, 30, 200).coastInNeutral()
				.withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

		TalonSettings rightSpeedSettingsToAdjust = TalonSettingsBuilder.defaults().withCurrentLimits(35, 30, 200).coastInNeutral()
				.withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();
		
		
		settings.applySettings(leftSpeedSettingsToAdjust, rightSpeedSettingsToAdjust);
		
		FourDriveTalonCalibratorController controller = new FourDriveTalonCalibratorController(settings,CALIBRATION_TIME_MS);
		assertFalse(controller.isCalibrationReady());
		//TODO: make sure calling before returns unmodified objectgs
		
		
	}
}
