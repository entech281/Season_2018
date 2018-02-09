package frc.team281.tests.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import edu.wpi.first.wpilibj.RobotTestUtils;
import frc.team281.robot.commands.LifterLowerCommand;
import frc.team281.robot.commands.LifterRaiseCommand;
import frc.team281.subsystems.FakeLifterSubsystem;
import frc.team281.tests.BaseTest;

/**
 * Tests that the LifterUpcommand works like we expect. Note that it extends
 * BestTEst, which sets up wpilib for testingoutside the robot
 * 
 * When this test runs, the real commands will run using the real scheduler, but
 * things will print out in the console
 * 
 * @author dcowden
 *
 */
public class TestLifterUpCommand extends BaseTest {

	private FakeLifterSubsystem testLifterSubsystem;

	@Test
	public void testCommandFinishesWithinTimeout() {

		// set up a spy. that way, we can not only test
		// our system, but also ensure that methods were called as we expect.
		// this is called behavior based testing.

		testLifterSubsystem = PowerMockito.spy(new FakeLifterSubsystem(10));
		LifterRaiseCommand ld = new LifterRaiseCommand(testLifterSubsystem);
		RobotTestUtils.schedule(ld);
		RobotTestUtils.runForSeconds(1.0);
		verify(testLifterSubsystem, atLeastOnce()).raise();
		assertTrue(ld.isFinished());
	}

	@Test
	public void testThatOneCommandCancelsAnother() {
		testLifterSubsystem = PowerMockito.spy(new FakeLifterSubsystem(100));
		LifterRaiseCommand raiseCommand = new LifterRaiseCommand(testLifterSubsystem, 0.5);
		LifterLowerCommand lowerCommand = new LifterLowerCommand(testLifterSubsystem, 0.5);
		RobotTestUtils.schedule(raiseCommand);
		RobotTestUtils.runForSeconds(0.3);
		RobotTestUtils.schedule(lowerCommand);

		RobotTestUtils.runForSeconds(0.4);

		assertFalse(raiseCommand.isRunning());
		// now up should have been called, followed by down
		verify(testLifterSubsystem, atLeastOnce()).raise();
		verify(testLifterSubsystem, atLeastOnce()).lower();

	}
}
