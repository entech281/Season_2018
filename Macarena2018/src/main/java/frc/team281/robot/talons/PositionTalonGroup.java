package frc.team281.robot.talons;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team281.robot.subsystems.TalonSettings;
import frc.team281.robot.subsystems.TalonSettingsBuilder.MotorRamping;
import frc.team281.robot.subsystems.TalonSettingsBuilder.PositionControlSettings;
import frc.team281.robot.subsystems.TalonSettingsBuilder.TalonControlMode;
import frc.team281.robot.subsystems.TalonSettingsBuilder.PositionControlSettings.Finish;
import frc.team281.robot.subsystems.TalonSettingsBuilder.PositionControlSettings.ProfileSettings;


public class PositionTalonGroup extends FourWheelSpeedDrive {
		
	
	// things you set when you are doing position control
	public interface PositionControlSettings {
		public interface GainSettings {
			ProfileSettings withGains(double f, double p, double i, double d);
		}
		public interface ProfileSettings {
			Object withMotionProfile(int cruiseEncoderClicksPerSecond, int accelerationEncoderClicksPerSecond2, int allowableError);
		}
	}

	
    public static class Builder extends FourWheelSpeedDrive.Builder<Builder> implements PositionControlSettings.GainSettings,
	PositionControlSettings.ProfileSettings{

        public Builder() {}


        public PositionTalonGroup build() { return new PositionTalonGroup(this); }
        
		
		@Override
		public frc.team281.robot.talons.PositionTalonGroup.PositionControlSettings.ProfileSettings withGains(double f, double p, double i, double d) {
			baseSettings.gains.f = f;
			baseSettings.gains.p = p;
			baseSettings.gains.i = i;
			baseSettings.gains.d = d;
			return this;
		}


		@Override
		public Object withMotionProfile(int cruiseEncoderClicksPerSecond, int accelerationEncoderClicksPerSecond2,
				int allowableError) {
			baseSettings.profile.accelerationEncoderClicksPerSecond2 = accelerationEncoderClicksPerSecond2;
			baseSettings.profile.cruiseVelocityEncoderClicksPerSecond = cruiseEncoderClicksPerSecond;
			baseSettings.profile.allowableClosedLoopError = allowableError;
			return this;
		}		

   
        
    }	
	
	protected PositionTalonGroup(Builder builder) {
		super(builder);
		this.baseSettings.controlMode = ControlMode.MotionMagic;
	}
}

