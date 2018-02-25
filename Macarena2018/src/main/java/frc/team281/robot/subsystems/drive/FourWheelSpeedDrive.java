package frc.team281.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.team281.robot.subsystems.TalonSettings;
import frc.team281.robot.subsystems.TalonSettingsBuilder;
import frc.team281.robot.talons.FourTalonGroup;

public class FourWheelSpeedDrive implements DriveComponent {

	protected FourTalonGroup talons;
	protected TalonSettings leftSettings;
	protected TalonSettings rightSettings;
	protected boolean isActive = false;

	private FourWheelSpeedDrive(Builder builder) {
		this.talons = builder.talons;
		leftSettings = TalonSettingsBuilder.copy(builder.settings);
		rightSettings = TalonSettingsBuilder.inverted(builder.settings);
	}

	public void activate() {
		leftSettings.configureTalon(talons.frontLeft);
		leftSettings.configureTalon(talons.rearLeft);
		rightSettings.configureTalon(talons.frontRight);
		rightSettings.configureTalon(talons.rearRight);
		isActive = true;

	}

	public void deactivate() {
		isActive = false;
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		if (!isActive) {
			activate();
		}

		leftSpeed = limit(leftSpeed);
		rightSpeed = limit(rightSpeed);

		talons.frontLeft.set(limit(leftSpeed));
		talons.rearLeft.set(limit(leftSpeed));
		talons.frontRight.set(limit(rightSpeed));
		talons.rearRight.set(limit(rightSpeed));

	}

	public void arcadeDrive(double xSpeed, double zRotation) {
		if (!isActive) {
			activate();
		}

		// copied from FRC DifferentialDrive
		// i am quite sure there's a better way
		xSpeed = limit(xSpeed);
		zRotation = limit(zRotation);

		double leftMotorOutput;
		double rightMotorOutput;

		double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

		if (xSpeed >= 0.0) {
			// First quadrant, else second quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			} else {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (zRotation >= 0.0) {
				leftMotorOutput = xSpeed + zRotation;
				rightMotorOutput = maxInput;
			} else {
				leftMotorOutput = maxInput;
				rightMotorOutput = xSpeed - zRotation;
			}
		}

		talons.frontLeft.set(limit(leftMotorOutput));
		talons.rearLeft.set(limit(leftMotorOutput));
		talons.frontRight.set(limit(rightMotorOutput));
		talons.rearRight.set(limit(rightMotorOutput));

	}

	public static SafetySettings defaults(FourTalonGroup talons) {
		return new Builder(talons, new TalonSettings());
	}

	// things you need to do no matter what, for safety reasons
	public interface SafetySettings {
		public interface BrakeMode {
			public DirectionSettings brakeInNeutral();

			public DirectionSettings coastInNeutral();
		}

		public BrakeMode withPrettySafeCurrentLimits();

		public BrakeMode withCurrentLimits(int maxInstantaneousAmps, int maxSustainedAmps, int sustainedMilliseconds);
	}

	// things you have to do so the motor goes the right way
	public interface DirectionSettings {
		public MotorOutputLimits defaultDirectionSettings();

		public MotorOutputLimits withDirections(boolean sensorPhase, boolean inverted);

	}

	// things you can do to limit how fast the motor goes
	public interface MotorOutputLimits {
		public Finish noMotorOutputLimits();

		public Finish limitMotorOutputs(double peakPercent, double minimumPercent);
	}

	public interface Finish {
		public FourWheelSpeedDrive build();
	}

	public static class Builder
			implements SafetySettings.BrakeMode, SafetySettings, DirectionSettings, MotorOutputLimits, Finish {

		private TalonSettings settings = new TalonSettings();
		protected FourTalonGroup talons;

		private Builder(TalonSettings settings) {
			this.settings = settings;
			settings.controlMode = ControlMode.PercentOutput;
		}

		private Builder(FourTalonGroup talons, TalonSettings settings) {
			this(settings);
			this.talons = talons;
		}

		@Override
		public FourWheelSpeedDrive build() {
			FourWheelSpeedDrive f = new FourWheelSpeedDrive(this);
			return f;
		}

		@Override
		public Finish noMotorOutputLimits() {
			settings.outputLimits.maxMotorOutputBackward = -1;
			settings.outputLimits.maxMotorOutputForward = 1;
			settings.outputLimits.minMotorOutputForward = 0;
			settings.outputLimits.minMotorOutputBackward = 0;
			return this;
		}

		@Override
		public Finish limitMotorOutputs(double peakPercent, double minimumPercent) {
			settings.outputLimits.maxMotorOutputBackward = -peakPercent;
			settings.outputLimits.maxMotorOutputForward = peakPercent;
			settings.outputLimits.minMotorOutputForward = minimumPercent;
			settings.outputLimits.minMotorOutputBackward = -minimumPercent;
			return this;
		}

		@Override
		public MotorOutputLimits defaultDirectionSettings() {
			settings.motorDirections.inverted = false;
			settings.motorDirections.sensorPhase = false;
			return this;
		}

		@Override
		public MotorOutputLimits withDirections(boolean sensorPhase, boolean inverted) {
			settings.motorDirections.sensorPhase = sensorPhase;
			settings.motorDirections.inverted = inverted;
			return this;
		}

		@Override
		public BrakeMode withPrettySafeCurrentLimits() {
			settings.currentLimits.instantaneousPeak = 5;
			settings.currentLimits.continuousPeak = 3;
			settings.currentLimits.continuousPeakMilliseconds = 200;
			return this;
		}

		@Override
		public BrakeMode withCurrentLimits(int maxInstantaneousAmps, int maxSustainedAmps, int sustainedMilliseconds) {
			settings.currentLimits.instantaneousPeak = maxInstantaneousAmps;
			settings.currentLimits.continuousPeak = maxSustainedAmps;
			settings.currentLimits.continuousPeakMilliseconds = sustainedMilliseconds;
			return this;
		}

		@Override
		public DirectionSettings brakeInNeutral() {
			settings.brakeMode = NeutralMode.Brake;
			return this;
		}

		@Override
		public DirectionSettings coastInNeutral() {
			settings.brakeMode = NeutralMode.Coast;
			return this;
		}

	}

	/**
	 * Limit motor values to the -1.0 to +1.0 range.
	 */
	protected static double limit(double value) {
		if (value > 1.0) {
			return 1.0;
		}
		if (value < -1.0) {
			return -1.0;
		}
		return value;
	}

	@Override
	public String getName() {
		return "FourWheelSpeedDrive";
	}
}
