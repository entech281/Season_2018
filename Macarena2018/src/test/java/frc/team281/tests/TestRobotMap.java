package frc.team281.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import frc.team281.robot.Robot;

/**
 * some really simple tests to prove that robot map works ok.
 * 
 * @author dcowden
 *
 */
public class TestRobotMap {

	public static void assertPositive(int value) {
		assertTrue(value >= 0);
	}

	@Test
	public void testRobotMapItems() {
		assertPositive(Robot.robotMap.CAN.FRONT_LEFT_MOTOR);
		assertPositive(Robot.robotMap.CAN.FRONT_RIGHT_MOTOR);
		assertPositive(Robot.robotMap.CAN.REAR_LEFT_MOTOR);
		assertPositive(Robot.robotMap.CAN.REAR_RIGHT_MOTOR);
	}
}
