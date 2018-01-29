package frc.team281.robot;

import edu.wpi.first.wpilibj.*;

import com.kauailabs.navx.frc.*;

public class YawPIDInterface implements PIDSource, PIDOutput {
    public double m_output;
    private AHRS m_ahrs;
    public YawPIDInterface(AHRS navx) {
        m_ahrs = navx;
    }
    public double getYawCorrection() {
        return m_output;
    }
    @Override
    public double pidGet() {
        return m_ahrs.getYaw();
    }
    @Override
    public void pidWrite(double output) {
        m_output = output;
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
