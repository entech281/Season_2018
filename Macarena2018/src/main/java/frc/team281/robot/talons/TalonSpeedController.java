package frc.team281.robot.talons;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.subsystems.TalonSettings;


public class TalonSpeedController {
	
	protected WPI_TalonSRX talon;
	protected TalonSettings settings;
	protected TalonEncoderStatus encoderStatus;
	
	public WPI_TalonSRX getTalon() {
		return talon;
	}
	
	public TalonEncoderStatus getEncoderStatus() {
		encoderStatus.updateEncoderStatus(talon);
		return encoderStatus;
	}
	
	public void reconfigure() {
		settings.configureTalon(talon);
	}
	
	public void setSpeed(double speed) {
		talon.set(speed);
	}
	
	public void stop() {
		talon.set(0.0);
	}
	
	private TalonSpeedController(Builder builder) {
		talon = builder.talon;
		settings = builder.settings;
	}
	
	public static SafetySettings defaults( WPI_TalonSRX talon) {
		return new Builder(talon, new TalonSettings());
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

	public interface Finish{
		public TalonSpeedController build();
	}


	public static class Builder implements   SafetySettings.BrakeMode,SafetySettings,
	     DirectionSettings, MotorOutputLimits, Finish{

		private TalonSettings settings = new TalonSettings();
		protected WPI_TalonSRX talon;
		
		private Builder( WPI_TalonSRX talon, TalonSettings settings) {
			this.talon = talon;
			this.settings = settings;
			settings.controlMode = ControlMode.PercentOutput;
		}

		@Override
		public TalonSpeedController build() {
			TalonSpeedController s = new TalonSpeedController(this);
			s.reconfigure();
			return s;
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
	
}
