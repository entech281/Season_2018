package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Sets up closed loop position control It is pretty complex to set this up,
 * with lots of options and such. So it makes sense to dedicate a class to make
 * it easier to set up
 * 
 * Based on the sample here:
 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionMagic/src/org/usfirst/frc/team217/robot/Robot.java
 * https://github.com/CrossTheRoadElec/Phoenix-Documentation/raw/master/Talon%20SRX%20Victor%20SPX%20-%20Software%20Reference%20Manual.pdf
 * 
 * @author dcowden
 *
 */
public class MotorPositionController {

	public static final int TIMEOUT_MS = 10;
	public static final int PID_SLOT = 0;
	public static final int PROFILE_SLOT = 0;

	private int desiredPosition = 0;
	private TalonSRX talon = null;

	private MotorPositionController(TalonSRX talon) {
		// private constructor-- use the builder because this is SO complex!
		this.talon = talon;
	}

	public void zeroSensorPosition() {
		talon.setSelectedSensorPosition(0, PID_SLOT, TIMEOUT_MS);
	}

	public int getSensorPosition() {
		return this.talon.getSelectedSensorPosition(PID_SLOT);
	}

	public void setDesiredPosition(int desiredPosition) {
		this.desiredPosition = desiredPosition;
		this.talon.set(ControlMode.MotionMagic, desiredPosition);
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}
	public static class Builder {
		private TalonSRX talon;

		public Builder(TalonSRX talon) {
			this.talon = talon;
			setDefaults();
		}

		private void setDefaults() {
			talon.selectProfileSlot(PROFILE_SLOT, PID_SLOT);
			talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT_MS);
			talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TIMEOUT_MS);
			talon.setSensorPhase(true);
			talon.setInverted(false);
			talon.setSelectedSensorPosition(0, PID_SLOT, TIMEOUT_MS);
			talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_SLOT, 0);
			
            talon.configNominalOutputForward(0, TIMEOUT_MS);
            talon.configNominalOutputReverse(0, TIMEOUT_MS);
            talon.configPeakOutputForward(1, TIMEOUT_MS);
            talon.configPeakOutputReverse(-1, TIMEOUT_MS);
            
            talon.configPeakCurrentLimit(35, TIMEOUT_MS);
            talon.configPeakCurrentDuration(200, TIMEOUT_MS);
            talon.configContinuousCurrentLimit(30, TIMEOUT_MS);
            talon.enableCurrentLimit(true);   
            
		}

		public Builder inverted(boolean inverted) {
			talon.setInverted(inverted);
			return this;
		}

		public Builder withSensorPhase(boolean sensorPhase) {
			talon.setSensorPhase(sensorPhase);
			return this;
		}

		public Builder withMotionProfile(int acceleration, int cruiseSpeed) {
			talon.configMotionCruiseVelocity(cruiseSpeed, TIMEOUT_MS);
			talon.configMotionAcceleration(acceleration, TIMEOUT_MS);
			return this;
		}

		public Builder withFeedbackSensor(FeedbackDevice feedbackDevice) {
			talon.configSelectedFeedbackSensor(feedbackDevice, PID_SLOT, TIMEOUT_MS);
			return this;
		}

		public Builder withGains(double f, double p, double i, double d) {
			talon.config_kF(PID_SLOT, f, TIMEOUT_MS);
			talon.config_kP(PID_SLOT, p, TIMEOUT_MS);
			talon.config_kI(PID_SLOT, i, TIMEOUT_MS);
			talon.config_kD(PID_SLOT, d, TIMEOUT_MS);
			return this;
		}

		public MotorPositionController build() {
			return new MotorPositionController(this.talon);
		}

	}
}
