package frc.team281.robot.controllers;


public class TalonGroup {

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
	
	public WPI_Talon getLeftFront() {
		return leftFront.getTalon();
	}
	public WPI_Talon getRightFront() {
		return leftFront.getTalon();
	}
	public WPI_Talon getLeftRear() {
		return leftFront.getTalon();
	}
	public WPI_Talon getLeftRear() {
		return leftFront.getTalon();
	}	
}
