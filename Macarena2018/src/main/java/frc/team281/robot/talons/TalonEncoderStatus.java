package frc.team281.robot.talons;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonEncoderStatus {

	public static final int PID_SLOT = 0;
	public static final double MIN_MOTOR_PERCENT = 0.05;
	public static final int MIN_ENCODER_MOVE_COUNTS = 5;
	public static final int NOT_READING = -9999999;
	public static final int MIN_SAMPLE_COUNT = 2;
	public static final int BROKEN_SAMPLES = 2;

	private int lastPosition = 0;
	private int brokenSampleCount = 0;
	private int sampleCount = 0;

	private boolean encoderOk = true;

	private int currentPosition = 0;
	private int currentVelocity = 0;
	private ControlMode mode = ControlMode.Disabled;
	private double motorCommandPercent = 0.0;

	public TalonEncoderStatus() {

	}

	public void updateEncoderStatus(WPI_TalonSRX talon) {
		updateEncoderStatus(talon.getSelectedSensorPosition(PID_SLOT), talon.getSelectedSensorVelocity(PID_SLOT),
				talon.getControlMode(), talon.getMotorOutputPercent());
	}

	public void updateEncoderStatus(int encoderPosition, int encoderVelocity, ControlMode mode,
			double motorCommandPercent) {
		this.mode = mode;
		this.motorCommandPercent = motorCommandPercent;
		this.currentPosition = encoderPosition;
		this.currentVelocity = encoderVelocity;
		updateEncoderState();
	}

	public int getPosition() {
		if (isEncoderOk()) {
			return currentPosition;
		} else {
			return NOT_READING;
		}
	}

	public int getVelocity() {
		if (isEncoderOk()) {
			return currentVelocity;
		} else {
			return NOT_READING;
		}
	}

	public void updateEncoderState() {
		if (!hasEnoughSamples())
			return;

		if (isCommandedToRun()) {
			boolean status = (countsMovedSinceLastUpdate() > MIN_ENCODER_MOVE_COUNTS);
			if (status) {
				brokenSampleCount = 0;
				encoderOk = true;
			} else {
				brokenSampleCount++;
			}
		}
		if (brokenSampleCount > BROKEN_SAMPLES) {
			encoderOk = false;
		}
		lastPosition = currentPosition;
	}

	protected boolean hasEnoughSamples() {
		if (sampleCount < MIN_SAMPLE_COUNT) {
			sampleCount++;
			return false;
		} else {
			return true;
		}
	}

	public boolean isEncoderOk() {
		return encoderOk;
	}

	private int countsMovedSinceLastUpdate() {
		return Math.abs(lastPosition - currentPosition);
	}

	public boolean isCommandedToRun() {
		if (mode == ControlMode.Follower || mode == ControlMode.Disabled) {
			return false;
		} else if (motorCommandPercent > MIN_MOTOR_PERCENT) {
			return true;
		} else {
			return true;
		}
	}

}
