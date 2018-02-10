package frc.team281.robot;

import org.junit.Test;

import frc.team281.robot.RobotMap;

import static org.junit.Assert.assertTrue;





public class TestRobotMap {

	public static void assertPositive(int value) {
		assertTrue( value >= 0 );
	}	
	
	@Test
	public void testRobotMapItems() {
		assertPositive(RobotMap.driveMotors.frontLeftMotorCANid );
		assertPositive(RobotMap.driveMotors.frontRightMotorCANid );
		assertPositive(RobotMap.driveMotors.rearLeftMotorCANid );
		assertPositive(RobotMap.driveMotors.rearRightMotorCANid );
	}
}
