package frc.team281.robot.trajectory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class WriterTrajectoryLogger implements TrajectoryLogger{

    
    public static final String ROBORIO_FLASH_DRIVE = "/media/sda1";
    public static final String HEADER="********TRAJECTORY_DATA*************";
    public static final String HEADER2="leftClicks, leftCLicksPer100ms, rightClicks, rightClicksPer100ms, millis";
    public static final String FOOTER="************************************"; 
    private Writer writer;
    private boolean shouldWrite = true;
    
    public WriterTrajectoryLogger(Writer writer) {
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
        if ( shouldWrite ){
                writeLine ( String.format("%d,%d,%d,%d,%d", 
                        leftEncoderClicks,leftEncoderClicksPer100ms,rightEncoderClicks ,rightEncoderClicksPer100ms,millisAtThisPoint)); 
        }
    }
    
    public void close(){
        writeLine(FOOTER);
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        this.shouldWrite = false;        
    }

    @Override
    public void unpause() {
        this.shouldWrite = false;         
    }
    
}
