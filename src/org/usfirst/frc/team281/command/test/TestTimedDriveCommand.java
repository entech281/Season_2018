package org.usfirst.frc.team281.command.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;
import org.strongback.components.Motor;
import org.strongback.drive.TankDrive;
import org.strongback.mock.Mock;
import org.usfirst.frc.team281.command.TimedDriveCommand;
import org.usfirst.frc.team281.robot.DriveSubsystem;

public class TestTimedDriveCommand {

	protected TankDrive testDrive;
	protected Motor LF;
	protected Motor LR;
	protected Motor RF;
	protected Motor RR;			
	
	@Before
	public void setupDrive() {
		LF = Mock.stoppedMotor();
		LR = Mock.stoppedMotor();
		RF = Mock.stoppedMotor();
		RR = Mock.stoppedMotor();		
		testDrive = DriveSubsystem.createDriveFromMotors(LF, LR, RF, RR);				
	}
	
	@Test
	public void testPrintingOutAllSpeedsGoingStraight() {
		
		double SPEED = 0.5;
		double TURN_SPEED = 0.0;
		double DURATION_MILLIS = 5000;		
		
		TimedDriveCommand tcd = new TimedDriveCommand(testDrive,SPEED,TURN_SPEED,2.0);
		CommandTester ct = new CommandTester(tcd);
		int interval = 100;
		for ( int systemTime=0;systemTime<DURATION_MILLIS;systemTime+=interval) {
			ct.step(systemTime);
			System.out.println(printSpeedAtTime(systemTime, LF,LR,RF,RR));
		}
	}

	@Test
	public void testPrintingOutAllSpeedsTurningALittle() {
		
		double SPEED = 0.5;
		double TURN_SPEED = -0.5;
		double DURATION_MILLIS = 5000;		
		
		TimedDriveCommand tcd = new TimedDriveCommand(testDrive,SPEED,TURN_SPEED,2.0);
		CommandTester ct = new CommandTester(tcd);
		int interval = 100;
		for ( int systemTime=0;systemTime<DURATION_MILLIS;systemTime+=interval) {
			ct.step(systemTime);
			System.out.println(printSpeedAtTime(systemTime, LF,LR,RF,RR));
		}
	}
	
	
	
	private String printSpeedAtTime(int i, Motor lf, Motor lr, Motor rf, Motor rr ) {
		return String.format("i= %4d LF: %2.2f, LR: %2.2f, RF: %2.2f, RR: %2.2f", i, lf.getSpeed(),lr.getSpeed(), rf.getSpeed(), rr.getSpeed());
	}	

	@Test
	public void testTurning() {
		double SPEED = 0.5;
		double TURN_SPEED = 0.0;
			
		
		TimedDriveCommand tcd = new TimedDriveCommand(testDrive,SPEED,TURN_SPEED,2.0);
		CommandTester ct = new CommandTester(tcd);
		double TOLERANCE = 0.00001;
		ct.step(100);
		assertEquals(LF.getSpeed(),SPEED/2.0,TOLERANCE);
		assertEquals(LR.getSpeed(),SPEED/2.0,TOLERANCE);
		assertEquals(RF.getSpeed(),-1.0*SPEED/2.0,TOLERANCE);
		assertEquals(RR.getSpeed(),-1.0*SPEED/2.0,TOLERANCE);
		
		ct.step(2100);
		assertEquals(LF.getSpeed(),0.0,TOLERANCE);
		assertEquals(LR.getSpeed(),0.0,TOLERANCE);
		assertEquals(RF.getSpeed(),0.0,TOLERANCE);
		assertEquals(RR.getSpeed(),0.0,TOLERANCE);
	}
	
	
	@Test
	public void testStoppedAndStarted() {
		double SPEED = 0.5;
		double TURN_SPEED = 0.0;
			
		
		TimedDriveCommand tcd = new TimedDriveCommand(testDrive,SPEED,TURN_SPEED,2.0);
		CommandTester ct = new CommandTester(tcd);
		double TOLERANCE = 0.00001;
		ct.step(100);
		assertEquals(LF.getSpeed(),SPEED/2.0,TOLERANCE);
		assertEquals(LR.getSpeed(),SPEED/2.0,TOLERANCE);
		assertEquals(RF.getSpeed(),-1.0*SPEED/2.0,TOLERANCE);
		assertEquals(RR.getSpeed(),-1.0*SPEED/2.0,TOLERANCE);
		
		ct.step(2100);
		assertEquals(LF.getSpeed(),0.0,TOLERANCE);
		assertEquals(LR.getSpeed(),0.0,TOLERANCE);
		assertEquals(RF.getSpeed(),0.0,TOLERANCE);
		assertEquals(RR.getSpeed(),0.0,TOLERANCE);
	}
	

}
