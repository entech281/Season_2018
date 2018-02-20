package frc.team281.robot.trajectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;

public class TrajectoryLoggerFactory {

    public static final String ROBORIO_FLASH_DRIVE = "/media/sda1";
    
    private static DataLogger dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger("TrajectoryLoggerFactory");
    
    
    public static TrajectoryLogger roboRioFlashDriveLogger(){
            try{
                return new WriterTrajectoryLogger( new BufferedWriter(new FileWriter( new File(ROBORIO_FLASH_DRIVE))));
            }
            catch ( IOException ioe){
                dataLogger.warn("Cannot Create Logger for RoboRio Flash Drive");
                return new DoNothingTrajectoryLogger();
            }        
    }
}
