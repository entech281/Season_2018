package frc.team281.robot.controllers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.team281.robot.subsystems.TalonSettings;

public class TalonWithSettings {


	private WPI_TalonSRX talon = null;
	private TalonSettings settings = null;
	
	public TalonWithSettings(WPI_TalonSRX talon, TalonSettings settings) {
		this.talon = talon;
		this.settings = settings;
	}
	public WPI_TalonSRX getTalon() {
		return talon;
	}
	
	public void configure() {
		settings.configureTalon(talon);
	}
	
}
