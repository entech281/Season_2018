package frc.team281.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team281.robot.*;
import frc.team281.robot.commands.DriveUsingJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.*;

public class DriveSubsystem extends Subsystem implements PIDOutput {

    private WPI_TalonSRX mFrontLeftMotor;
    private WPI_TalonSRX mFrontRightMotor;
    private WPI_TalonSRX mRearLeftMotor;
    private WPI_TalonSRX mRearRightMotor;

    private SensorCollection mFrontLeftEncoder;
    private SensorCollection mFrontRightEncoder;
    private SensorCollection mRearLeftEncoder;
    private SensorCollection mRearRightEncoder;

    private DifferentialDrive mDrive;

    private enum DriveState {
        kDriveJoystick,
        kDriveDistance
    }

    private DriveState mState;

    static final int cEncoderSignalOK = 200;
    static final int cEncoderSignalBad = 0;
    // Target distances are in encoder pulses
    private int   mTargetLeftDistance;
    private int   mTargetRightDistance;
    private double  mSpeed;
    private Timer   mTimer;

    static final double YAW_P = 0.05;
    static final double YAW_I = 0.0;
    static final double YAW_D = 0.0;
    static final double YAW_ToleranceDegrees = 2.0;

    private AHRS mNavX;
    private boolean mNavXok;
    private boolean mFieldAbsolute;
    private double  mYawHoldAngle;
    private double  mYawCorrection;
    private PIDController mYawController;
    private double mLastJSforw;
    private double mLastJSturn;

    public DriveSubsystem() {
        try {
            mNavX = new AHRS(SerialPort.Port.kMXP);
            mNavX.reset();
            DriverStation.reportWarning("NavX MXP found",false);
            mNavXok = true;
        }
        catch(Exception e) {
            mNavXok = false;
            DriverStation.reportError("Trouble with NavX MXP",false);
        }

        mFrontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftMotorCANid);
        mFrontRightMotor = new WPI_TalonSRX(RobotMap.frontRightMotorCANid);
        mRearLeftMotor = new WPI_TalonSRX(RobotMap.rearLeftMotorCANid);
        mRearRightMotor = new WPI_TalonSRX(RobotMap.rearRightMotorCANid);

        mFrontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
        mFrontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
        mRearLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
        mRearRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);

        mFrontLeftEncoder = new SensorCollection(mFrontLeftMotor);
        mFrontRightEncoder = new SensorCollection(mFrontRightMotor);
        mRearLeftEncoder = new SensorCollection(mRearLeftMotor);
        mRearRightEncoder = new SensorCollection(mRearRightMotor);

        mDrive = new DifferentialDrive(
            new SpeedControllerGroup(mFrontLeftMotor, mRearLeftMotor),
            new SpeedControllerGroup(mFrontRightMotor,mRearRightMotor) );

        // Setup the encoder settings
        mTimer = new Timer();

        // Setup the Yaw PID controller
        mState = DriveState.kDriveJoystick;
        mFieldAbsolute = false;
        mYawHoldAngle = 0.0;
        mYawCorrection = 0.0;
        mYawController = new PIDController(YAW_P, YAW_I, YAW_D, mNavX, this);
        mYawController.setAbsoluteTolerance(YAW_ToleranceDegrees);
        mYawController.setInputRange(-180.0,180.0);
        mYawController.setContinuous(true);
        mYawController.setOutputRange(-0.4,0.4);
        mYawController.disable();

        // Make sure NavX has finished calibrating
        if (mNavXok) {
            while (mNavX.isCalibrating()) {
            	try {
            		Thread.sleep(50);   // 50ms
            	}
            	catch (InterruptedException e) {
            	}
            }
            mNavX.zeroYaw();
        }
    }

	public void stop() {
		mDrive.tankDrive(0.,0.);
        mState = DriveState.kDriveJoystick;
	}

	public void arcadeDrive(double forw, double turn) {
		mLastJSforw = forw;
		mLastJSturn = turn;
        if (mFieldAbsolute) {
            double js_mag = Robot.oi.getJoystickMagnitude();
            if (Math.abs(js_mag) > 180.0) {
                mDrive.tankDrive(0.0,0.0);
            } else {
                double js_ang = Robot.oi.getJoystickDirection();
                double delta_ang = js_ang - mNavX.getYaw();
                while (delta_ang > 180.0)
                    delta_ang -= 360.0;
                while (delta_ang < -180.0)
                    delta_ang += 360.0;
                if (Math.abs(delta_ang) > 90.0) {
                    mDrive.tankDrive(mYawCorrection, -mYawCorrection);
                } else {
                    js_mag = js_mag * Math.cos(Math.PI*(delta_ang)/180.0);
                    mDrive.tankDrive(js_mag+mYawCorrection,js_mag-mYawCorrection);
                }
                setHoldYawAngle(js_ang);
            }
        } else {
            if (mYawController.isEnabled()) {
                turn += mYawCorrection;
            }
            mDrive.arcadeDrive(-forw, turn, true);
        }
	}

    public void tankDrive(double left, double right) {
        if (mYawController.isEnabled()) {
            left  += mYawCorrection;
            right -= mYawCorrection;
        }
	mDrive.tankDrive(left, right, true);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveUsingJoystick(this));
    }

    public void disableFieldAbsolute() {
        mFieldAbsolute = false;
        mYawController.disable();
    }

    public void driveDistance(double leftDistance, double rightDistance, double speed) {
        // Convert from inches to encoder clicks
        // 6in dia wheel
        // 80 clicks/motor revolution
        // 2 sets of 14:52 gear reductions
        mTargetLeftDistance  = (int)(( leftDistance/(6.0*3.14))*80*(52/14)*(52/14));
        mTargetRightDistance = (int)((rightDistance/(6.0*3.14))*80*(52/14)*(52/14));
        mSpeed = speed;

        // Reset encoders to zero
        mFrontLeftEncoder.setQuadraturePosition(0,0);
        mFrontRightEncoder.setQuadraturePosition(0,0);
        mRearLeftEncoder.setQuadraturePosition(0,0);
        mRearRightEncoder.setQuadraturePosition(0,0);

        // Start the timer
        mTimer.stop();
        mTimer.reset();
        mTimer.start();

        // Remember that we are driving by distance
        mState = DriveState.kDriveDistance;
    }

    public double getLeftEncoderDistance() {
        int front = mFrontLeftEncoder.getQuadraturePosition();
        int rear = mRearLeftEncoder.getQuadraturePosition();
        if ((Math.abs(front) > cEncoderSignalOK) && (Math.abs(rear) < cEncoderSignalBad)) {
            return front;
        }
        if ((Math.abs(rear) > cEncoderSignalOK) && (Math.abs(front) < cEncoderSignalBad)) {
            return rear;
        }
        if ((mTimer.get() > 0.5) && (Math.abs(front) < cEncoderSignalBad)  && (Math.abs(rear) < cEncoderSignalBad)) {
            return 0;
        }
        return (front + rear)/2;
    }

    public int getRightEncoderDistance() {
        int front = mFrontRightEncoder.getQuadraturePosition();
        int rear = mRearRightEncoder.getQuadraturePosition();
        if ((Math.abs(front) > cEncoderSignalOK) && (Math.abs(rear) < cEncoderSignalBad)) {
            return front;
        }
        if ((Math.abs(rear) > cEncoderSignalOK) && (Math.abs(front) < cEncoderSignalBad)) {
            return rear;
        }
        if ((mTimer.get() > 0.5) && (Math.abs(front) < cEncoderSignalBad)  && (Math.abs(rear) < cEncoderSignalBad)) {
            return 0;
        }
        return (front + rear)/2;
    }

    public boolean isAtDistance() {
        if (mState == DriveState.kDriveDistance)
            return false;
        return true;
    }

    public void enableFieldAbsolute() {
        mFieldAbsolute = true;
        mYawController.setSetpoint(mNavX.getYaw());
        mYawController.enable();
    }

    public void setHoldYawAngle(double angle) {
        mYawHoldAngle = angle;
        mYawController.setSetpoint(mYawHoldAngle);
        mYawController.enable();
    }

    public void disableHoldYaw() {
	mYawCorrection = 0.0;
        mYawController.disable();
    }

    public boolean isAtAngle() {
        if (mYawController.isEnabled()) {
           if (Math.abs(mYawHoldAngle-mNavX.getYaw()) < YAW_ToleranceDegrees) {
                return true;
           }
           return false;
        }
        return true;
    }

    public void pidWrite(double output) {
        mYawCorrection = output;
    }

    @Override
    public void periodic() {
        // function called by scheduler automatically
        // If driving by joystick -- then nothing to do
    	SmartDashboard.putNumber("Last JS forw: ",mLastJSforw);
    	SmartDashboard.putNumber("Last JS turn: ",mLastJSturn);
        SmartDashboard.putBoolean("Hold Yaw Active: ", mYawController.isEnabled());
        SmartDashboard.putNumber("Hold Yaw Angle: ", mYawHoldAngle);
    	SmartDashboard.putNumber("Yaw Correction: ",mYawCorrection);
    	SmartDashboard.putData("YawController:", mYawController);
    	SmartDashboard.putNumber("FrontLeftEncoder:", mFrontLeftEncoder.getQuadraturePosition());
    	SmartDashboard.putNumber("FrontRightEncoder:", mFrontRightEncoder.getQuadraturePosition());
    	SmartDashboard.putNumber("RearLeftEncoder:", mRearLeftEncoder.getQuadraturePosition());
    	SmartDashboard.putNumber("RearRightEncoder:", mRearRightEncoder.getQuadraturePosition());
        if (mNavXok) {
            SmartDashboard.putData("NavX: ", mNavX);
            SmartDashboard.putNumber("NavX Yaw Angle: ", mNavX.getYaw());
        }
        if (mState == DriveState.kDriveJoystick) {
            return;
        }

        // Do automatic drive here
        double left_speed = mSpeed;
        double right_speed = mSpeed;
        if (mTimer.get() > 0.2) {
            if (Math.abs(getLeftEncoderDistance()) < 0.85*Math.abs(mTargetLeftDistance)) {
                left_speed = mSpeed;
            } else if (Math.abs(getLeftEncoderDistance()) < Math.abs(mTargetLeftDistance)) {
                left_speed = 0.75*mSpeed;
            } else {
                left_speed = 0.0;
            }
            if (mTargetLeftDistance < 0.0){
                left_speed = -left_speed;
            }
            if (Math.abs(getRightEncoderDistance()) < 0.85*Math.abs(mTargetRightDistance)) {
                right_speed = mSpeed;
            } else if (Math.abs(getRightEncoderDistance()) < Math.abs(mTargetRightDistance)) {
                right_speed = 0.75*mSpeed;
            } else {
                right_speed = 0.0;
            }
            if (mTargetRightDistance < 0.0){
                right_speed = -right_speed;
            }

            if ((Math.abs(left_speed) > 0.05) || Math.abs(right_speed) > 0.05) {
                if (mYawController.isEnabled()) {
                    left_speed  += mYawCorrection;
                    right_speed -= mYawCorrection;
                }
                mDrive.tankDrive(left_speed, right_speed);
            } else {
                mDrive.tankDrive(0.0, 0.0);
                mState = DriveState.kDriveJoystick;
            }
        } else {
            if (mYawController.isEnabled()) {
                left_speed  += mYawCorrection;
                right_speed -= mYawCorrection;
            }
            mDrive.tankDrive(left_speed, right_speed);
        }
    }
}
