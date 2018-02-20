package frc.team281.robot.trajectory;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.Test;

public class TestTrajectoryLogger {
    
    @Test
    public void testTrajectoryLogger(){
        StringWriter sw = new StringWriter();
     
        WriterTrajectoryLogger log = new WriterTrajectoryLogger(sw);
        log.init();
        log.logTrajectoryPoint(100, 50, 200, 60, 20);
        log.logTrajectoryPoint(300, 70, 400, 80, 10);
        log.stopAndClose();
        
        assertEquals("",sw.toString());
    }

}
