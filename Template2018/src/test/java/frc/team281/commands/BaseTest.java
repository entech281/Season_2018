package frc.team281.commands;

import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.wpi.first.wpilibj.HLUsageReporting;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotTestSetup;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team281.robot.logger.ConsoleDataLogger;
import frc.team281.robot.logger.DataLogger;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Scheduler.class, HLUsageReporting.class, SendableBase.class })
public class BaseTest {

    @Test
    public void testBaseLoadsOk() {

    }

    public static final long DELAY_MILLIS = 20;
    protected Scheduler scheduler;
    // protected double startTime = 0.0;
    protected DataLogger dataLogger;

    public void delayMillis(long milliSeconds) {
	try {
	    Thread.sleep(milliSeconds);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    throw new RuntimeException(e);
	}
    }

    protected void runForSeconds(double seconds) {
	long currentTime = System.currentTimeMillis();
	long endTime = System.currentTimeMillis() + (long) (seconds * 1000);
	while (currentTime < endTime) {
	    dataLogger.logMessage("Clock", "Tick");
	    scheduler.run();
	    delayMillis(DELAY_MILLIS);
	    currentTime = System.currentTimeMillis();
	}
    }

    @Before
    public void deleteEvilCode() {
	suppress(constructor(SendableBase.class, Boolean.class));
	suppress(method(HLUsageReporting.class, "reportScheduler"));
	// suppress(constructor(Scheduler.class));
	this.scheduler = Scheduler.getInstance();
	RobotTestSetup.setupMockBase();
	dataLogger = new ConsoleDataLogger();
    }

}
