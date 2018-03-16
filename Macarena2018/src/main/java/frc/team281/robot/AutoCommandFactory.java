package frc.team281.robot;

import java.util.List;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team281.robot.commands.BaseCommand;
import frc.team281.robot.commands.FollowPositionPathCommand;
import frc.team281.robot.commands.GrabberShootCommand;
import frc.team281.robot.commands.WristPivotDownCommand;
import frc.team281.robot.commands.LifterRaiseCommand;
import frc.team281.robot.commands.LifterRaiseSeconds;
import frc.team281.robot.subsystems.GrabberSubsystem;
import frc.team281.robot.subsystems.WristSubsystem;
import frc.team281.robot.subsystems.LifterSubsystem;
import frc.team281.robot.subsystems.Position;
import frc.team281.robot.subsystems.PositionCalculator;
import frc.team281.robot.subsystems.drive.RealDriveSubsystem;

public class AutoCommandFactory {

    private LifterSubsystem lifterSubsystem;
    private GrabberSubsystem grabberSubsystem;
    private WristSubsystem wristSubsystem;
    private RealDriveSubsystem driveSubsystem;
    
    public AutoCommandFactory(LifterSubsystem lifterSubsystem, GrabberSubsystem grabberSubsystem,
                              WristSubsystem wristSubsystem, RealDriveSubsystem driveSubsystem) {
        this.lifterSubsystem = lifterSubsystem;
        this.grabberSubsystem = grabberSubsystem;
        this.wristSubsystem = wristSubsystem;
        this.driveSubsystem = driveSubsystem;
    }
    
    public CommandGroup makeAutoCommand(WhichAutoCodeToRun whatAutoToRun) {
        //ABCD
        switch (whatAutoToRun) {
        case A: 
            return makeAutoProcedure(autoPathA(false));
        
        case A_MIRRORED:
        	return makeAutoProcedure(autoPathA(true));
            
        case B: 
            return makeAutoProcedure(autoPathB(false));
             
        case B_MIRRORED:
        	return makeAutoProcedure(autoPathB(true));
        	
        case C:
            return makeAutoProcedure(autoPathC(false));
            
        case C_MIRRORED:
        	return makeAutoProcedure(autoPathC(true));
        	
        case D:
            return makeAutoProcedure(autoPathD(false));
            
        case D_MIRRORED:
        	return makeAutoProcedure(autoPathD(true));
        	
        case E:
            return makeAutoProcedure(autoPathE(false));
            
        case E_MIRRORED:
        	return makeAutoProcedure(autoPathE(true));
        	
        case F:
            return makeAutoProcedure(autoPathF(false));
            
        case F_MIRRORED:
        	return makeAutoProcedure(autoPathF(true));
        	
        default:
            break;
        }
        return null;
    }
    
    protected CommandGroup makeAutoProcedure(BaseCommand followPath) {
       CommandGroup auto = new CommandGroup();
           auto.addParallel(new LifterRaiseSeconds(lifterSubsystem,1.5));
           auto.addSequential(followPath);           
           auto.addSequential(new WristPivotDownCommand(wristSubsystem));
           auto.addSequential(new GrabberShootCommand(grabberSubsystem, 2));
           
       return auto;
    }
    
    public BaseCommand autoPathA(boolean mirrored) {
       List<Position> lp = PositionCalculator.builder()
    		   .forward(14*12)
    		   .right(90)
    		   .build();
       if ( mirrored ) {
    	   	lp = PositionCalculator.mirror(lp);
       }
        return new FollowPositionPathCommand( driveSubsystem, lp);
    }
    
    public BaseCommand autoPathB(boolean mirrored) {
    	List<Position> lp = PositionCalculator.builder() 
                .forward(25*12)
                .right(90)
                .build();
        if ( mirrored ) {
        	lp = PositionCalculator.mirror(lp);
        }
        return new FollowPositionPathCommand( driveSubsystem, lp);
    }
    
    public BaseCommand autoPathC(boolean mirrored) {
    	List<Position> lp = PositionCalculator.builder()
                .forward(235)
                .right(90)
                .forward(190)
                .right(90)
                .forward(10)
                .build();
    	if ( mirrored ) {
    		lp = PositionCalculator.mirror(lp);
    	}
    	return new FollowPositionPathCommand( driveSubsystem, lp);
    }
    
    public BaseCommand autoPathD(boolean mirrored) {
    	List<Position> lp = PositionCalculator.builder()
                .forward(24)
                .left(45)
                .forward(78)
                .right(45)
                .forward(24)
                .build();
    	if ( mirrored ) {
    		lp = PositionCalculator.mirror(lp);
    	}
        return new FollowPositionPathCommand( driveSubsystem, lp);
    }
    
    public BaseCommand autoPathE(boolean mirrored) {
    	List<Position> lp = PositionCalculator.builder()
                .forward(228)
                .right(90)
                .forward(111)
                .build();
        if ( mirrored ) {
        	lp = PositionCalculator.mirror(lp);
        }
        return new FollowPositionPathCommand( driveSubsystem, lp);
    }
    
    public BaseCommand autoPathF(boolean mirrored) {
    	List<Position> lp = PositionCalculator.builder()
                .forward(220)
                .right(90)     
                .forward(190)     
                .left(45)
                .forward(52)
                .left(65)
                .forward(36)
                .build();
        if ( mirrored ) {
        	lp = PositionCalculator.mirror(lp);
        }
        return new FollowPositionPathCommand( driveSubsystem, lp);
    }
}
