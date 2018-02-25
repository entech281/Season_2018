package frc.team281.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team281.robot.subsystems.drive.EncoderInchesConverter;
import frc.team281.robot.talons.DriveEncoderStatus;
import frc.team281.robot.talons.TalonEncoderStatus;

public class TestDriveEncoderStatus {

	protected EncoderInchesConverter converter = new EncoderInchesConverter(10);
	
	protected void clickEncoders(TalonEncoderStatus tes , int ... encoderClicks ) {
		for ( int e: encoderClicks) {
			tes.updateEncoderStatus(e, 10, ControlMode.PercentOutput, 0.1);	
		}
		
	}
	
	@Test
	public void testInitialState() {
		
		DriveEncoderStatus status = new DriveEncoderStatus(converter);
		
		assertTrue(status.allOk());
		assertTrue(status.canDrive());
		
	}
	
	@Test
	public void testAllEncodersOk() {
		TalonEncoderStatus goodStatus = new TalonEncoderStatus();
		clickEncoders(goodStatus,100,200,300,400,500);
		
		DriveEncoderStatus status = new DriveEncoderStatus(converter);
		status.updateTalonStatus(goodStatus, goodStatus, goodStatus, goodStatus);
		assertTrue(status.allOk());
		
		assertTrue(goodStatus == status.getLeftFrontStatus());
		assertTrue(goodStatus == status.getLeftRearStatus());
		assertTrue(goodStatus == status.getRightFrontStatus());
		assertTrue(goodStatus == status.getRightRearStatus());
		
		assertEquals(500,status.getLeftEncoderCountsIgnoringBrokenEncoders());
		assertEquals(500,status.getRightEncoderCountsIgnoringBrokenEncoders());
		assertEquals(50,status.getLeftInchesIgnoringBrokenEncoders(),0.001);
		assertEquals(50,status.getRightInchesIgnoringBrokenEncoders(),0.001);
		assertTrue(status.isLeftOk() && status.isRightOk());
		assertTrue(status.isLeftRearOk() && status.isLeftFrontOk() && status.isRightFrontOk() && status.isRightRearOk());		
		assertTrue(status.allOk() && status.canDrive() && !status.hasProblems());
		assertFalse(status.shouldLeftFrontFollowLeftRear());		
		assertFalse(status.shouldLeftRearFollowLeftFront());	
		assertFalse(status.shouldRightFrontFollowRightRear());	
		assertFalse(status.shouldRightRearFollowRightFront());	
		assertTrue(status.friendlyStatus().indexOf("OK")> -1 );		
	}
	
	@Test
	public void testOneSideBroken() {
		TalonEncoderStatus goodStatus = new TalonEncoderStatus();
		clickEncoders(goodStatus,100,200,300,400,500);

		TalonEncoderStatus badStatus = new TalonEncoderStatus();
		clickEncoders(badStatus,100,200,200,200,200, 200, 200, 200);
		
		DriveEncoderStatus status = new DriveEncoderStatus(converter);
		
		//first one is the leftFront
		status.updateTalonStatus(badStatus, goodStatus, goodStatus, goodStatus);
		assertFalse(status.allOk());
		
		assertEquals(500,status.getLeftEncoderCountsIgnoringBrokenEncoders());
		assertEquals(500,status.getRightEncoderCountsIgnoringBrokenEncoders());
		assertEquals(50,status.getLeftInchesIgnoringBrokenEncoders(),0.001);
		assertEquals(50,status.getRightInchesIgnoringBrokenEncoders(),0.001);
		assertTrue( status.isRightOk());
		assertFalse(status.isLeftFrontOk());
		assertTrue(status.isLeftRearOk() &&  status.isRightFrontOk() && status.isRightRearOk());		
		assertTrue( status.canDrive() );
		assertTrue(status.hasProblems());
		assertTrue(status.shouldLeftFrontFollowLeftRear());
		assertFalse(status.shouldLeftRearFollowLeftFront());	
		assertFalse(status.shouldRightFrontFollowRightRear());	
		assertFalse(status.shouldRightRearFollowRightFront());		
		
		assertTrue(status.friendlyStatus().indexOf("IMPAIRED")> -1 );
		assertTrue(status.friendlyStatus().indexOf("LF: [-]")> -1 );
	}
	
	@Test
	public void testBothOnOneSideBroken() {
		TalonEncoderStatus goodStatus = new TalonEncoderStatus();
		clickEncoders(goodStatus,100,200,300,400,500);

		TalonEncoderStatus badStatus = new TalonEncoderStatus();
		clickEncoders(badStatus,100,200,200,200,200, 200, 200, 200);
		
		DriveEncoderStatus status = new DriveEncoderStatus(converter);
		
		//first one is the leftFront
		status.updateTalonStatus(badStatus, badStatus, goodStatus, goodStatus);
		assertFalse(status.allOk());
		
		assertEquals(0,status.getLeftEncoderCountsIgnoringBrokenEncoders());
		assertEquals(500,status.getRightEncoderCountsIgnoringBrokenEncoders());
		assertEquals(0,status.getLeftInchesIgnoringBrokenEncoders(),0.001);
		assertEquals(50,status.getRightInchesIgnoringBrokenEncoders(),0.001);
		assertTrue( status.isRightOk());
		assertFalse(status.isLeftFrontOk());
		assertFalse(status.isLeftRearOk());
		assertFalse(status.isLeftOk());
		assertTrue(  status.isRightFrontOk() && status.isRightRearOk());		
		assertFalse( status.canDrive() );
		assertTrue(status.hasProblems());
		assertFalse(status.shouldLeftFrontFollowLeftRear());
		assertFalse(status.shouldLeftRearFollowLeftFront());	
		assertFalse(status.shouldRightFrontFollowRightRear());	
		assertFalse(status.shouldRightRearFollowRightFront());		
		
		assertTrue(status.friendlyStatus().indexOf("CANT")> -1 );
		assertTrue(status.shouldDisableAll());
	}	
	
}
