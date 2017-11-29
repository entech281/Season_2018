package org.usfirst.frc.team281.command;

import org.strongback.command.Command;
import org.strongback.drive.TankDrive;

public class TimedDriveCommand extends Command {


    private final TankDrive drive;
    private final double driveSpeed;
    private final double turnSpeed;
    
    
    /**
     * Create a new autonomous command.
     * @param drive the chassis
     * @param driveSpeed the speed at which to drive forward; should be [-1.0, 1.0]
     * @param turnSpeed the speed at which to turn; should be [-1.0, 1.0]
     * @param duration the duration of this command; should be positive
     */
    public TimedDriveCommand( TankDrive drive, double driveSpeed, double turnSpeed,  double duration ) {
        super(duration, drive);
        this.drive = drive;
        this.driveSpeed = driveSpeed;
        this.turnSpeed = turnSpeed;

    }
    
    @Override
    public boolean execute() {
    	
        drive.arcade(driveSpeed, turnSpeed);
        return false; 
        
    }
    
    @Override
    public void interrupted() {
        drive.stop();
    }
    
    @Override
    public void end() {
        drive.stop();
    }

}
