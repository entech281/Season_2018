package frc.team281.robot.trajectory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import frc.team281.robot.logger.DataLogger;
import frc.team281.robot.logger.DataLoggerFactory;

public class TrajectoryPlanner {

    public static final String ROBORIO_FLASH_DRIVE = "/media/sda1";
    public static final String ROBORIO_BASE_PATH="/blah/blah";
    private static DataLogger dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger("TrajectoryLoggerFactory");
    private String fileName;
    
    protected File makeFullFilePath(String basePath, String fileName){
        return new File(basePath, fileName);
    }
    
    public TrajectoryLogger startLogging(String fileName){
    	this.fileName = fileName;
        return makeLoggerFromPath(ROBORIO_BASE_PATH,fileName);
    }
    
    public TrajectoryReader readLoggedPoints() {
    	return makeReaderFromPath(ROBORIO_BASE_PATH,this.fileName);
    }    
    
    protected TrajectoryLogger makeLoggerFromPath(String basePath, String fileName){
            try{
                File fullPath = makeFullFilePath(basePath,fileName);
                return new WriterTrajectoryLogger( new BufferedWriter(new FileWriter( fullPath)));
            }
            catch ( IOException ioe){
                dataLogger.warn("Cannot Create Logger for RoboRio Flash Drive");
                return new DoNothingTrajectoryLogger();
            }        
    }
    

    
    protected TrajectoryReader makeReaderFromPath(String basePath, String fileName){
        try{
            File fullPath = makeFullFilePath(basePath,fileName);
            return new ReaderTrajectoryReader( new BufferedReader(new FileReader( fullPath)));
        }
        catch ( IOException ioe){
            dataLogger.warn("Cannot Create Logger for RoboRio Flash Drive");
            return new DoNothingTrajectoryReader();
        }        
}    
}
