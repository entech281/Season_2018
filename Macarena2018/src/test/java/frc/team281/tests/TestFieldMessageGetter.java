package frc.team281.tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import frc.team281.robot.FieldMessage;
import frc.team281.robot.FieldMessageGetter;


public class TestFieldMessageGetter {
	
	
	private static final String LLL = "LLL";
	private static final String RRR = "RRR";
	
	@Test
	public void testThatLLLResultsInTrues() {
	FieldMessageGetter test = new FieldMessageGetter();
	FieldMessage result = test.convertGameMessageToFieldMessage(LLL);
	
		assertTrue(result.isOurScaleOnTheLeft());
		assertTrue(result.isOurSwitchOnTheLeft());
		assertTrue(result.isTheirSwitchOnTheLeft());
	}
	
	@Test
	public void testThatRRRResultsInFalses() {
		
	FieldMessageGetter test = new FieldMessageGetter();
	FieldMessage result = test.convertGameMessageToFieldMessage(RRR);
	
		assertFalse(result.isOurScaleOnTheLeft());
		assertFalse(result.isOurSwitchOnTheLeft());
		assertFalse(result.isTheirSwitchOnTheLeft());
	}
}
