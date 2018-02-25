package frc.team281.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;
import frc.team281.robot.subsystems.Position;
import frc.team281.robot.subsystems.PositionSource;
import frc.team281.robot.subsystems.TalonSettings;
import frc.team281.robot.subsystems.TalonSettingsBuilder;
import frc.team281.robot.talons.DriveEncoderStatus;
import frc.team281.robot.talons.FourTalonGroup;

public class FourWheelPositionDrive implements DriveComponent {

	public static final double TOLERANCE_INCHES = 1.0;

	protected FourTalonGroup talons;
	protected TalonSettings leftSettings;
	protected TalonSettings rightSettings;
	protected boolean isActive = false;
	private Position desiredPosition;
	protected DataLogger dataLogger;

	private FourWheelPositionDrive(Builder builder) {
		this.talons = builder.talons;
		this.dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger(getClass().getSimpleName());
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

	protected void resetPosition() {
		talons.frontLeft.set(0.0);
		talons.rearLeft.set(0.0);
		talons.frontRight.set(0.0);
		talons.rearRight.set(0.0);
	}

	protected void setPosition(double leftPos, double rightPos, boolean isRelative) {

		if (!isRelative) {
			resetPosition();
		}
		talons.frontLeft.set(leftPos);
		talons.rearLeft.set(leftPos);
		talons.frontRight.set(rightPos);
		talons.rearRight.set(rightPos);
	}

	public void updatePosition(PositionSource positionSource, DriveEncoderStatus driveEncoderStatus) {
		if (hasCurrentCommand()) {
			Position current = driveEncoderStatus.getPositionIgnoringBrokenEncoders();
			Position command = getCurrentCommand();
			if (command.isCloseTo(current, TOLERANCE_INCHES)) {
				positionSource.next();
				setCurrentCommand(null);
			}
		} else {
			if (positionSource.hasNextPosition()) {
				EncoderInchesConverter converter = driveEncoderStatus.getConverter();
				Position p = positionSource.getCurrentPosition();
				setCurrentCommand(p);
				int encoderLeft = converter.toCounts(p.getLeftInches());
				int encoderRight = converter.toCounts(p.getRightInches());
				setPosition(encoderLeft, encoderRight, p.isRelative());
			} else {

			}
		}
		if (hasCurrentCommand()) {
			dataLogger.log("commandPosition", getCurrentCommand());
		} else {
			dataLogger.log("commandPosition", "<IDLE>");
		}

	}

	public boolean hasCurrentCommand() {
		return this.desiredPosition != null;
	}

	public void setCurrentCommand(Position position) {
		this.desiredPosition = position;
	}

	public Position getCurrentCommand() {
		return this.desiredPosition;
	}

	protected void processPositionCommand() {

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

	public interface DirectionSettings {
		public MotorOutputLimits defaultDirectionSettings();

		public MotorOutputLimits withDirections(boolean sensorPhase, boolean inverted);

	}

	// things you can do to limit how fast the motor goes
	public interface MotorOutputLimits {
		public MotorRamping noMotorOutputLimits();

		public MotorRamping limitMotorOutputs(double peakPercent, double minimumPercent);
	}

	// things you can do to control how the motor starts up
	public interface MotorRamping {
		public PositionControlSettings.GainSettings noMotorStartupRamping();

		public PositionControlSettings.GainSettings withMotorRampUpOnStart(double secondsToFullPower);
	}

	public interface PositionControlSettings {
		public interface GainSettings {
			ProfileSettings withGains(double f, double p, double i, double d);
		}

		public interface ProfileSettings {
			Finish withMotionProfile(int cruiseEncoderClicksPerSecond, int accelerationEncoderClicksPerSecond2,
					int allowableError);
		}
	}

	public interface Finish {
		public FourWheelPositionDrive build();
	}

	public static class Builder
			implements SafetySettings.BrakeMode, SafetySettings, DirectionSettings, MotorOutputLimits,
			PositionControlSettings.GainSettings, PositionControlSettings.ProfileSettings, Finish, MotorRamping {

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
		public FourWheelPositionDrive build() {
			settings.controlMode = ControlMode.MotionMagic;
			return new FourWheelPositionDrive(this);
		}

		@Override
		public MotorRamping noMotorOutputLimits() {
			settings.outputLimits.maxMotorOutputBackward = -1;
			settings.outputLimits.maxMotorOutputForward = 1;
			settings.outputLimits.minMotorOutputForward = 0;
			settings.outputLimits.minMotorOutputBackward = 0;
			return this;
		}

		@Override
		public MotorRamping limitMotorOutputs(double peakPercent, double minimumPercent) {
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

		@Override
		public Finish withMotionProfile(int cruiseEncoderClicksPerSecond, int accelerationEncoderClicksPerSecond2,
				int allowableError) {
			settings.profile.accelerationEncoderClicksPerSecond2 = accelerationEncoderClicksPerSecond2;
			settings.profile.cruiseVelocityEncoderClicksPerSecond = cruiseEncoderClicksPerSecond;
			settings.profile.allowableClosedLoopError = allowableError;
			return this;
		}

		@Override
		public PositionControlSettings.ProfileSettings withGains(double f, double p, double i, double d) {
			settings.gains.f = f;
			settings.gains.p = p;
			settings.gains.i = i;
			settings.gains.d = d;
			return this;
		}

		@Override
		public PositionControlSettings.GainSettings noMotorStartupRamping() {
			settings.rampUp.rampUpSecondsClosedLoop = 0;
			settings.rampUp.rampUpSecondsOpenLoop = 0;
			return this;
		}

		@Override
		public PositionControlSettings.GainSettings withMotorRampUpOnStart(double secondsToFullPower) {
			settings.rampUp.rampUpSecondsOpenLoop = secondsToFullPower;
			return this;
		}

	}

	@Override
	public String getName() {
		return "FourWheelDrivePosition";
	}

}
