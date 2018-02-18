package frc.team281.robot.trajectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TrajectoryLogger {

    
    public static final String ROBORIO_FLASH_DRIVE = "/media/sda1";
    public static final String HEADER="********TRAJECTORY_DATA*************\n";
    public static final String FOOTER="************************************";
    private String filePath;    
    private BufferedWriter writer;
    
    public TrajectoryLogger(String filePath){
        this.filePath = filePath;
    }
    
    public TrajectoryLogger(){
        this(ROBORIO_FLASH_DRIVE);
    }
    
    public void init(){
        try {
            writer = new BufferedWriter ( new FileWriter ( new File(filePath)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writeLine(HEADER);
    }
    
    public void writeLine(String line){
        try {
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logTrajectoryPoint (double leftEncoderClicks, double leftEncoderClicksPer100ms, 
                                    double rightEncoderClicks, double rightEncoderClicksPer100ms,int millisAtThisPoint ){
        writeLine ( String.format("%d,%d,%d,%d,%d", 
                leftEncoderClicks,leftEncoderClicksPer100ms,rightEncoderClicks ,rightEncoderClicksPer100ms,millisAtThisPoint));        
    }
    
    public void stop(){
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
