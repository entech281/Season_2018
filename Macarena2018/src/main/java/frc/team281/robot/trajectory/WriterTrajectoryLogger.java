package frc.team281.robot.trajectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriterTrajectoryLogger {

    
    public static final String ROBORIO_FLASH_DRIVE = "/media/sda1";
    public static final String HEADER="********TRAJECTORY_DATA*************";
    public static final String HEADER2="leftClicks, leftCLicksPer100ms, rightClicks, rightClicksPer100ms, millis";
    public static final String FOOTER="************************************"; 
    private Writer writer;
    
    public static WriterTrajectoryLogger roboRioFlashDriveLogger(){
        try{
            return new WriterTrajectoryLogger( new BufferedWriter(new FileWriter( new File(ROBORIO_FLASH_DRIVE))));
        }
        catch ( IOException ioe){
            throw new RuntimeException(ioe);
        }        
    }
    
    public WriterTrajectoryLogger(Writer writer){
        this.writer = new BufferedWriter(writer);
    }
    
    public void init(){
        writeLine(HEADER);
        writeLine(HEADER2);
    }
    
    public void writeLine(String line){
        try {
            writer.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logTrajectoryPoint (int leftEncoderClicks, int leftEncoderClicksPer100ms, 
            int rightEncoderClicks, int rightEncoderClicksPer100ms,int millisAtThisPoint ){
        writeLine ( String.format("%d,%d,%d,%d,%d", 
                leftEncoderClicks,leftEncoderClicksPer100ms,rightEncoderClicks ,rightEncoderClicksPer100ms,millisAtThisPoint));        
    }
    
    public void stopAndClose(){
        writeLine(FOOTER);
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
