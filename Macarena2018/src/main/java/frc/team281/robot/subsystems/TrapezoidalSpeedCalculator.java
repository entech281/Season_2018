package frc.team281.robot.subsystems;

public class TrapezoidalSpeedCalculator {

    
    private int nominalLengthCounts = 0;
    private int positionTolerance = 0;
    private int desiredPositionCounts = 0;
    private int currentPositionCounts = 0;




    private double cruiseSpeedPercent = 0.0;
    private double minSpeedPercent = 0.0;
    private double rampCounts = 0.0;
    
    public static final double STOP = 0.0;
    
    public TrapezoidalSpeedCalculator(int nominalLengthCounts, double cruiseSpeedPercent, double minSpeedPercent, int rampCounts, int positionTolerance){
        this.nominalLengthCounts = nominalLengthCounts;
        this.cruiseSpeedPercent = cruiseSpeedPercent;
        this.positionTolerance = positionTolerance;
        this.minSpeedPercent = minSpeedPercent;
        this.rampCounts = rampCounts;
    }
    

    public int getDesiredPositionCounts() {
        return desiredPositionCounts;
    }

    public void setPositionToStart(){
        setDesiredPositionCounts(0);
    }
    public void setPositionToEnd(){
        setDesiredPositionCounts(nominalLengthCounts);
    }
    
    public void setDesiredPositionCounts(int desiredPositionCounts) {
        int tmpPositionCounts = desiredPositionCounts;
        if ( desiredPositionCounts > nominalLengthCounts){
            tmpPositionCounts = nominalLengthCounts;
        }
        if ( desiredPositionCounts< 0){
            tmpPositionCounts = 0;
        }
        this.desiredPositionCounts = tmpPositionCounts;
    }
    
    public boolean isMoveBasicallyTop(){
        return Math.abs(desiredPositionCounts - nominalLengthCounts) <= this.positionTolerance;
    }
    
    public boolean isMoveBasicallyBottom(){
        return Math.abs(desiredPositionCounts) <= this.positionTolerance;
    } 
    
    public boolean isAtUpperLimit(){
        return currentPositionCounts >= ( nominalLengthCounts - positionTolerance );
    }
    
    public boolean isMovingUp(){
        return currentPositionCounts < desiredPositionCounts;
    }
    
    public boolean isMovingDown(){
        return currentPositionCounts >= desiredPositionCounts;
    }
    
    public boolean isAtLowerLimit(){
        return currentPositionCounts <= ( positionTolerance );
    }
    public double calculateSpeed(){
        //this is a basic trapezoidal profile
        //if we're more than rampdown counts, go full speed.
        //other wise, taper down to min speed over ramp counts
        //if the command move is either end of the axis move at minimum speed till you get there
        double calcSpeed = STOP;
        int distanceToMove =  desiredPositionCounts - currentPositionCounts;
        int absDistanceToMove = Math.abs(distanceToMove);
        if ( absDistanceToMove <= positionTolerance){
            calcSpeed = STOP;
        }
        else if ( isAtUpperLimit() &&  isMovingUp()){
            calcSpeed = 0;
        }
        else if ( isAtLowerLimit() && isMovingDown() ){
            calcSpeed = 0;
        }        
        else if ( currentPositionCounts > nominalLengthCounts){
            calcSpeed = 0;
        }
        else if ( absDistanceToMove >= rampCounts){
            calcSpeed  = cruiseSpeedPercent;
        }
        else if ( isMoveBasicallyBottom() ){
            calcSpeed = minSpeedPercent;
        }
        else if ( isMoveBasicallyTop()){            
            calcSpeed = minSpeedPercent;
        }
        else{
            //start ramping down
            double rampPercentage = (double)absDistanceToMove / (double)rampCounts;
            calcSpeed = minSpeedPercent + (( cruiseSpeedPercent - minSpeedPercent) * rampPercentage );
        }
        
        //now apply sign
        if ( distanceToMove < 0 ){
            calcSpeed = -calcSpeed;
        }
        return calcSpeed;
    }
    
    public int getNominalLengthCounts() {
        return nominalLengthCounts;
    }


    public int getPositionTolerance() {
        return positionTolerance;
    }


    public double getCruiseSpeedPercent() {
        return cruiseSpeedPercent;
    }


    public double getMinSpeedPercent() {
        return minSpeedPercent;
    }


    public double getRampCounts() {
        return rampCounts;
    }    
    
    public int getCurrentPositionCounts() {
        return currentPositionCounts;
    }


    public void setCurrentPositionCounts(int currentPositionCounts) {
        this.currentPositionCounts = currentPositionCounts;
    }    
}
