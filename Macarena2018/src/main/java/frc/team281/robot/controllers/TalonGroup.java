package frc.team281.robot.controllers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonGroup {

	public TalonGroup(TalonWithSettings leftFront, TalonWithSettings leftRear, TalonWithSettings rightFront, TalonWithSettings rightRear) {
		this.leftFront = leftFront;
		this.leftRear = leftRear;
		this.rightFront = rightFront;
		this.rightRear = rightRear;
	}
	public TalonWithSettings leftFront;
	public TalonWithSettings leftRear;
	public TalonWithSettings rightFront;
	public TalonWithSettings rightRear;
	
	public void configureAll() {
		leftFront.configure();
		leftRear.configure();
		rightFront.configure();
		rightRear.configure();
	}
	
	public WPI_TalonSRX getLeftFront() {
		return leftFront.getTalon();
	}
	public WPI_TalonSRX getRightFront() {
		return leftFront.getTalon();
	}
	public WPI_TalonSRX getLeftRear() {
		return leftFront.getTalon();
	}
	public WPI_TalonSRX getRightRear() {
		return leftFront.getTalon();
	}	
}
