package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private MotionSettings motionSettings;
    private String name;

    public MotorPositionController(TalonSRX talon, MotionSettings motionSettings, String name) {
        this.name = name;
        this.motionSettings = motionSettings;
        this.talon = talon;
    }

    protected void setDefaultValues() {

        // TODO; factor these out into constants

        talon.selectProfileSlot(PROFILE_SLOT, PID_SLOT);
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT_MS);
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TIMEOUT_MS);

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

    protected void copyMotionSettings() {
        talon.config_kF(PID_SLOT, motionSettings.getfGain(), TIMEOUT_MS);
        talon.config_kP(PID_SLOT, motionSettings.getpGain(), TIMEOUT_MS);
        talon.config_kI(PID_SLOT, motionSettings.getiGain(), TIMEOUT_MS);
        talon.config_kD(PID_SLOT, motionSettings.getdGain(), TIMEOUT_MS);
        talon.setInverted(motionSettings.isInverted());
        talon.setSensorPhase(false);
        talon.configMotionCruiseVelocity(motionSettings.getCruiseSpeed(), TIMEOUT_MS);
        talon.configMotionAcceleration(motionSettings.getAcceleration(), TIMEOUT_MS);

    }

    public void setDesiredPosition(int desiredPosition) {
        this.desiredPosition = desiredPosition;
        setDefaultValues();
        copyMotionSettings();
        resetPosition();
        SmartDashboard.putNumber(this.name, 0);
        this.talon.set(ControlMode.MotionMagic, desiredPosition);
    }

    public int getDesiredPosition() {
        return this.desiredPosition;
    }

    public int getCurrentPosition() {
        int position = talon.getSelectedSensorPosition(PID_SLOT);
        SmartDashboard.putNumber(this.name, position);
        return position;
    }

    public void resetPosition() {
        talon.setSelectedSensorPosition(0, PID_SLOT, TIMEOUT_MS);
    }
}
