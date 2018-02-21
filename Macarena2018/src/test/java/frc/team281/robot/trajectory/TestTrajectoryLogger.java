package frc.team281.robot.trajectory;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.junit.Test;

public class TestTrajectoryLogger {
    
    public static final double TOL = 0.0001;
    @Test
    public void testTrajectoryLogger(){
        StringWriter sw = new StringWriter();
     
        WriterTrajectoryLogger log = new WriterTrajectoryLogger(sw);
        log.init();
        log.logTrajectoryPoint(100, 50, 200, 60, 20);
        log.logTrajectoryPoint(300, 70, 400, 80, 10);
        log.close();        
    }
    
    @Test
    public void testRoundTrip(){
        
        StringWriter sw = new StringWriter();
        
        TrajectoryLogger tw = new WriterTrajectoryLogger(sw);
        
        tw.logTrajectoryPoint(200, 100, 300, 400, 10);
        tw.logTrajectoryPoint(500, 600, 700, 800, 20);
        tw.close();
        
        TrajectoryReader tr = new ReaderTrajectoryReader(
                new BufferedReader( 
                        new StringReader(sw.toString())
                )
         );
        
        List<TwoWheelTrajectory> r = tr.asList();
        assertEquals(2,r.size());
        
        TwoWheelTrajectory first = r.get(0);
        assertEquals(first.left.isLastPoint, false);
        assertEquals(first.left.position, 200, TOL );
        assertEquals(first.left.velocity, 100, TOL );
        assertEquals(first.right.position, 300, TOL );
        assertEquals(first.right.velocity, 400, TOL );        
        
    }

}
