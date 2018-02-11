package frc.team281.robot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestRobotMap {

    public static void assertPositive(int value) {
        assertTrue(value >= 0);
    }

    @Test
    public void testRobotMapItems() {
        assertPositive(RobotMap.frontLeftMotorCANid);
        assertPositive(RobotMap.frontRightMotorCANid);
        assertPositive(RobotMap.rearLeftMotorCANid);
        assertPositive(RobotMap.rearRightMotorCANid);
    }
}
