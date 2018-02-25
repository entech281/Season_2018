package frc.team281.robot.trajectory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

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
                Writer os = new OutputStreamWriter(new FileOutputStream(fullPath), StandardCharsets.UTF_8);
                return new WriterTrajectoryLogger( new BufferedWriter(os));
            }
            catch ( IOException ioe){
                dataLogger.warn("Cannot Create Logger for RoboRio Flash Drive");
                return new DoNothingTrajectoryLogger();
            }        
    }
    

    
    protected TrajectoryReader makeReaderFromPath(String basePath, String fileName){
        try{
            File fullPath = makeFullFilePath(basePath,fileName);
            Reader r = new InputStreamReader ( new FileInputStream ( fullPath), StandardCharsets.UTF_8);
            return new ReaderTrajectoryReader( new BufferedReader(r));
        }
        catch ( IOException ioe){
            dataLogger.warn("Cannot Create Logger for RoboRio Flash Drive");
            return new DoNothingTrajectoryReader();
        }        
}    
}
