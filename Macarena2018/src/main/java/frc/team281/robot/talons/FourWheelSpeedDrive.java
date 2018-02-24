package frc.team281.robot.talons;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.subsystems.TalonSettings;
import frc.team281.robot.subsystems.TalonSettingsBuilder;
import frc.team281.robot.talons.FourWheelSpeedDrive.Talons.LeftRear;
import frc.team281.robot.talons.FourWheelSpeedDrive.Talons.RightFront;
import frc.team281.robot.talons.FourWheelSpeedDrive.Talons.RightRear;



public class FourWheelSpeedDrive {
	protected WPI_TalonSRX leftFrontTalon;
	protected WPI_TalonSRX leftRearTalon;
	protected WPI_TalonSRX rightFrontTalon;
	protected WPI_TalonSRX rightRearTalon;
	protected TalonSettings leftSettings;
	protected TalonSettings rightSettings;
	
	protected boolean isActive = false;
	private FourWheelSpeedDrive ( Builder builder ) {
		leftFrontTalon = builder.leftFrontTalon;
		leftRearTalon = builder.leftRearTalon;
		rightFrontTalon = builder.rightFrontTalon;
		rightRearTalon = builder.rightRearTalon;
		
		rightSettings = TalonSettingsBuilder.copy(builder.settings);
		rightSettings = TalonSettingsBuilder.inverted(builder.settings);
				
		
	}
	
	public void activate() {
		leftSettings.configureTalon(leftFrontTalon);
		leftSettings.configureTalon(leftRearTalon);
		rightSettings.configureTalon(rightFrontTalon);
		rightSettings.configureTalon(rightRearTalon);			
		isActive = true;
		
	}
	public void deactivate() {
		isActive = false;
	}
	
  public void tankDrive(double leftSpeed, double rightSpeed) {
	if ( ! isActive ) {
		activate();
	}
	
    leftSpeed = limit(leftSpeed);
    rightSpeed = limit(rightSpeed);

    leftFrontTalon.set(limit(leftSpeed));
    leftRearTalon.set(limit(leftSpeed));
    rightFrontTalon.set(limit(rightSpeed));
    rightRearTalon.set(limit(rightSpeed));    

  }	
	
	public void arcadeDrive(double xSpeed, double zRotation) {
		if ( ! isActive ) {
			activate();
		}
		
		//copied from FRC DifferentialDrive 
		//i am quite sure there's a better way
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

	    leftFrontTalon.set(limit(leftMotorOutput));
	    leftRearTalon.set(limit(leftMotorOutput));
	    rightFrontTalon.set(-limit(rightMotorOutput));
	    rightRearTalon.set(-limit(rightMotorOutput));  	    
	
	}
	
	public static SafetySettings defaults() {
		return new Builder(new TalonSettings());
	}

	public interface Talons{
		public interface LeftFront {
			public RightFront leftFront(WPI_TalonSRX leftFrontTalon);
		};
		public interface RightFront {
			public LeftRear rightFront(WPI_TalonSRX rightFrontTalon);
		};
		public interface LeftRear {
			public RightRear leftRear(WPI_TalonSRX leftRearTalon);
		};
		public interface RightRear {
			public SafetySettings rightRear(WPI_TalonSRX rightRearTalon);
		};		

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
		public FourWheelSpeedDrive build();
	}


	public static class Builder implements  Talons.LeftFront,Talons.LeftRear,Talons.RightFront,Talons.RightRear, SafetySettings.BrakeMode,SafetySettings,
	     DirectionSettings, MotorOutputLimits, Finish{

		private TalonSettings settings = new TalonSettings();
		protected WPI_TalonSRX leftFrontTalon;
		protected WPI_TalonSRX leftRearTalon;
		protected WPI_TalonSRX rightFrontTalon;
		protected WPI_TalonSRX rightRearTalon;
		
		private Builder(TalonSettings settings) {
			this.settings = settings;
			settings.controlMode = ControlMode.PercentOutput;
		}

		@Override
		public FourWheelSpeedDrive build() {
			return new FourWheelSpeedDrive(this);
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

		@Override
		public SafetySettings rightRear(WPI_TalonSRX rightRearTalon) {
			this.rightRearTalon = rightRearTalon;
			return this;
		}

		@Override
		public LeftRear rightFront(WPI_TalonSRX rightFrontTalon) {
			this.rightFrontTalon = rightFrontTalon;
			return this;
		}

		@Override
		public RightRear leftRear(WPI_TalonSRX leftRearTalon) {
			this.leftRearTalon = leftRearTalon;
			return this;
		}

		@Override
		public RightFront leftFront(WPI_TalonSRX leftFrontTalon) {
			this.leftFrontTalon = leftFrontTalon;
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
}
