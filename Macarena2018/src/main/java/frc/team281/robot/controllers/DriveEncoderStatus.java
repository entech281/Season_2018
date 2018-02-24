package frc.team281.robot.controllers;

import frc.team281.robot.subsystems.Position;
import frc.team281.robot.subsystems.drive.EncoderInchesConverter;
import frc.team281.robot.subsystems.drive.FourTalonsWithSettings;

/**
 * Various methods to read the encoders on the talons.
 * @author dcowden
 *
 */
public class DriveEncoderStatus {    
    
    private TalonEncoderStatus leftFrontStatus = new TalonEncoderStatus();
    private TalonEncoderStatus leftRearStatus = new TalonEncoderStatus();
    private TalonEncoderStatus rightFrontStatus = new TalonEncoderStatus();
    private TalonEncoderStatus rightRearStatus = new TalonEncoderStatus();    
    
    private EncoderInchesConverter converter;
    
    public DriveEncoderStatus (EncoderInchesConverter converter ){        
        this.converter = converter;
    }
    
    public void updateStatus ( FourTalonsWithSettings talons){
         leftFrontStatus.updateEncoderStatus(talons.getFrontLeft());
         leftRearStatus.updateEncoderStatus(talons.getFrontLeft());
         rightFrontStatus.updateEncoderStatus(talons.getFrontLeft());
         rightRearStatus.updateEncoderStatus(talons.getFrontLeft());
    }

    public Position getPositionIgnoringBrokenEncoders(){
        return new Position ( getLeftInchesIgnoringBrokenEncoders(),
                getRightInchesIgnoringBrokenEncoders() );
    }
    
    public double getLeftInchesIgnoringBrokenEncoders(){
        return converter.toInches(getLeftEncoderCountsIgnoringBrokenEncoders());
    }
    public double getRightInchesIgnoringBrokenEncoders(){
        return converter.toInches(getRightEncoderCountsIgnoringBrokenEncoders());
    }  
    public int getLeftEncoderCountsIgnoringBrokenEncoders() {
        return computeEncoderCountForPairIngoringBroken(leftFrontStatus,leftRearStatus);    
    }    
    public int getRightEncoderCountsIgnoringBrokenEncoders() {
        return computeEncoderCountForPairIngoringBroken(rightFrontStatus,rightRearStatus);
    }    
    
    private int computeEncoderCountForPairIngoringBroken(TalonEncoderStatus first, TalonEncoderStatus second){
        if ( first.isEncoderOk() && second.isEncoderOk() ){
            return (first.getPosition() + second.getPosition()) / 2;
        }
        else if ( ! first.isEncoderOk() && ! second.isEncoderOk() ){
            return 0;
        }        
        else{
            if ( first.isEncoderOk() ){
                return first.getPosition();
            }
            else{
                return second.getPosition();
            }
        }          
    }
    
    public String friendlyStatus(){
        StringBuffer sb = new StringBuffer();
        if ( allOk() ){
            sb.append("OK::");
        }
        else {
            if ( ! canDrive()  ){
                sb.append("CANT DRIVE:: ");
            }
            else{
                sb.append("IMPAIRED:: ");
            }
        }
        sb.append(String.format("LF: %s LR: %s RF: %s RR: %s", 
                convertBooleanToMotorStatus(isLeftFrontOk() ),
                convertBooleanToMotorStatus(isLeftRearOk()),
                convertBooleanToMotorStatus(isRightFrontOk()),
                convertBooleanToMotorStatus(isRightRearOk())
          ));        
        return sb.toString();
    }
    
    protected String convertBooleanToMotorStatus(boolean status){
        if ( status ){
            return "[-]";
        }
        else{
            return "[+]";
        }
    }    
    
    public boolean isLeftRearOk() {
        return leftRearStatus.isEncoderOk();
    }

    public boolean isLeftFrontOk() {
        return leftFrontStatus.isEncoderOk();
    }

    public boolean isRightRearOk() {
        return rightRearStatus.isEncoderOk();
    }

    public boolean isRightFrontOk() {
        return rightFrontStatus.isEncoderOk();
    }

    public boolean isLeftOk() {
        return isLeftFrontOk() || isLeftRearOk();
    }

    public boolean isRightOk() {
        return isRightFrontOk() || isRightRearOk();
    }

    public boolean canDrive() {
        return isLeftOk() && isRightOk();
    }

    public boolean hasLeftProblems() {
        return !isLeftOk();
    }

    public boolean hasRightProblems() {
        return !isRightOk();
    }

    public boolean hasProblems() {
        return !allOk();
    }
    
    public boolean shouldLeftFrontFollowLeftRear() {
        return hasProblems() && canDrive() && isLeftRearOk() && (!isLeftFrontOk());
    }

    public boolean shouldLeftRearFollowLeftFront() {
        return hasProblems() && canDrive() && isLeftFrontOk() && (!isLeftRearOk());
    }

    public boolean shouldRightRearFollowRightFront() {
        return hasProblems() && canDrive() && isRightFrontOk() && (!isRightRearOk());
    }

    public boolean shouldRightFrontFollowRightRear() {
        return hasProblems() && canDrive() && isRightRearOk() && (!isRightFrontOk());
    }

    public boolean shouldDisableAll() {
        return this.hasProblems() && !this.canDrive();
    }    

    public boolean allOk() {
        return isLeftRearOk() && isLeftFrontOk() && isRightRearOk() && isRightFrontOk();
    }    
    
}
