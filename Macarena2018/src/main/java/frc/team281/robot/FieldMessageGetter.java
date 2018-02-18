package frc.team281.robot;

import frc.team281.robot.FieldMessage;

public class FieldMessageGetter {
	
	public FieldMessageGetter() {
		
	}
	public FieldMessage convertGameMessageToFieldMessage(String gameMessage) {
		FieldMessage message = new FieldMessage();
		if(gameMessage.charAt(0) == 'L') {
			message.setOurSwitchOnTheLeft(true); 
		}
		else {
			message.setOurSwitchOnTheLeft(false);
		}
		if(gameMessage.charAt(1) == 'L') {
			message.setOurScaleOnTheLeft(true); 
			
		}
		else {
			message.setOurScaleOnTheLeft(false);
		}
		if(gameMessage.charAt(2) == 'L') {
			message.setTheirSwitchOnTheLeft(true); 
		}
		else {
			message.setTheirSwitchOnTheLeft(false);
		}
		return message;
		
	}
	
	
	
}
