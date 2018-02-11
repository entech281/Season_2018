package frc.team281.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public abstract class BaseTalonController {

	private TalonSRX talon = null;
	private TalonSettings settings = null;
	
	public BaseTalonController(TalonSRX talon, TalonSettings settings) {
		this.talon = talon;
		this.settings = settings;
	}
	
	public void configure() {
		settings.configureTalon(talon);
	}
	
	public void resetMode() {
		settings.setMode(talon);
	}
	public void resetMode(double settingValue) {
		settings.setMode(talon, settingValue);
	}
	
	public TalonSRX getTalon() {
		return talon;
	}

	public TalonSettings getSettings() {
		return settings;
	}	
}
