package frc.team281.commands;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

import edu.wpi.first.wpilibj.Timer;
import frc.team281.robot.commands.ProngsDown;
import frc.team281.robot.commands.ProngsUp;
import frc.team281.robot.logger.ConsoleDataLogger;
import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.subsystems.ProngsSubsystem;
import frc.team281.robot.subsystems.TestProngsSubsystem;


public class TestLifterUpCommand extends BaseTest{
	
	ProngsSubsystem testProngsSubsystem;
	
	
	@Before
	public void setup() {
	  testProngsSubsystem = PowerMockito.spy( new TestProngsSubsystem(new ConsoleDataLogger()));
    //mockProngsSubsystem = PowerMockito.spy( new TestProngsSubsystem(new ConsoleDataLogger()) );
    
//    doAnswer(new Answer<Object>() {
//        @Override
//        public Object answer(InvocationOnMock invocation) throws Throwable {
//          printMessage("Raise!");         
//            return null;
//        }
//    }).when(mockProngsSubsystem).raise(); 
//  
//    doAnswer(new Answer<Object>() {
//        @Override
//        public Object answer(InvocationOnMock invocation) throws Throwable {
//          printMessage("Lower!");
//            return null;
//        }
//    }).when(mockProngsSubsystem).lower();   	  
	}
	
	@Test
	public void executesSingleSimpleCommand() {	

		ProngsUp p = new ProngsUp(testProngsSubsystem);
		scheduler.add(p);
		runForSeconds(1.0);
		verify(testProngsSubsystem,atLeastOnce()).raise();
	}
	
	@Test
	public void executeTwoCommandsOneSubsystem() {
	    
		scheduler.add(new ProngsUp(testProngsSubsystem));
		dataLogger.logMessage("executeTwoCommandsOneSubsystem", "Running for 0.3 sec");
		runForSeconds(0.3);		
		dataLogger.logMessage("executeTwoCommandsOneSubsystem", "Creating Command");
		scheduler.add(new ProngsDown(testProngsSubsystem));
		dataLogger.logMessage("executeTwoCommandsOneSubsystem", "Running for 0.4 sec");
		runForSeconds(0.4);
		
		//now up should have been called, followed by down
		verify(testProngsSubsystem,atLeastOnce()).raise();
		verify(testProngsSubsystem,atLeastOnce()).lower();
			
		
	}
}
