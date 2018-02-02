package frc.team281.robot;

import edu.wpi.first.wpilibj.*;

import com.kauailabs.navx.frc.*;

public class YawPIDInterface implements PIDSource, PIDOutput {
    public double mOutput;
    private AHRS mNavX;
    public YawPIDInterface(AHRS navx) {
        mNavX = navx;
    }
    public double getYawCorrection() {
        return mOutput;
    }
    @Override
    public double pidGet() {
        return mNavX.getYaw();
    }
    @Override
    public void pidWrite(double output) {
        mOutput = output;
    }
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
	}
	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}
}
