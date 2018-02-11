package frc.team281.robot.subsystems;

public class MotionSettings {

    private int cruiseSpeed = 0;
    private int acceleration = 0;
    private boolean inverted = false;
    private double fGain = 0.0;
    private double pGain = 0.0;
    private double iGain = 0.0;
    private double dGain = 0.0;

    public MotionSettings getInvertedClone() {
        MotionSettings motionSettings = new MotionSettings();
        motionSettings.acceleration = this.acceleration;
        motionSettings.cruiseSpeed = this.cruiseSpeed;
        motionSettings.dGain = this.dGain;
        motionSettings.iGain = this.iGain;
        motionSettings.pGain = this.pGain;
        motionSettings.fGain = this.fGain;
        motionSettings.inverted = !this.inverted;
        return motionSettings;
    }

    public int getCruiseSpeed() {
        return cruiseSpeed;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public boolean isInverted() {
        return inverted;
    }

    public double getfGain() {
        return fGain;
    }

    public double getpGain() {
        return pGain;
    }

    public double getiGain() {
        return iGain;
    }

    public double getdGain() {
        return dGain;
    }

    public static Builder defaults() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {

        }

        private MotionSettings settings = new MotionSettings();

        public Builder withGains(double f, double p, double i, double d) {
            settings.dGain = d;
            settings.fGain = f;
            settings.iGain = i;
            settings.pGain = p;
            return this;
        }

        public Builder invert() {
            settings.inverted = !settings.inverted;
            return this;
        }

        public Builder motionProfile(int acceleration, int cruiseSpeed) {
            settings.acceleration = acceleration;
            settings.cruiseSpeed = cruiseSpeed;
            return this;
        }

        public MotionSettings build() {
            return settings;
        }

    }
}
