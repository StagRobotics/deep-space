/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;


/**
 * Add your docs here.
 */
public class MegaPeg extends Subsystem {
  
  ADXL345_I2C accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Range.k2G);
  private SpeedController megaPegMotor = new Spark(2);

  private String megaPegState = "up";

  private double angle = accelerometer.getY()*90*-1;
  
  @Override
  public void initDefaultCommand() {
  }

  public double getY(){
    return accelerometer.getY();
  }

  public double getAngle(){
    return angle;
  }
  public double getSpeed(){
    return megaPegMotor.get();
  }
  public void toggleMegaPeg(){
    if(megaPegState == "inside"){
      megaPegState = "up";
    }
    else if (megaPegState == "up"){
      megaPegState = "down";
    }
    else if (megaPegState == "down"){
      megaPegState = "up";
    }
  }

  public String getMegaPegState(){
    return megaPegState;
  }

  public void liftMegaPeg(){
    if ( accelerometer.getY()*90*-1 < 30){
      megaPegMotor.set(0.75);
    }
    else if( accelerometer.getY()*90*-1 < 45 ){
      megaPegMotor.set(0.75);
    }
    else if ( accelerometer.getY()*90*-1 >= 45){
      megaPegMotor.set(0);
    }
  }
  
  public void lowerMegaPeg(){
    if ( accelerometer.getY()*90*-1 > 5){
      megaPegMotor.set(-0.4);
    }
    else if( accelerometer.getY()*90*-1 >= 0){
      megaPegMotor.set(-0.3);
    }
    else if ( accelerometer.getY()*90*-1 <= 0){
      megaPegMotor.set(0);
    }
  }

  public void scoringMegaPeg(){
    if ( accelerometer.getY()*90*-1 > 20){
      megaPegMotor.set(-0.4);
    }
    else if( accelerometer.getY()*90*-1 >= 15){
      megaPegMotor.set(-0.4);
    }
    else if ( accelerometer.getY()*90*-1 <= 15 ) {
      megaPegMotor.set(0);
    }
  }

  public void stopMegaPeg(){
    megaPegMotor.set(0);
  }
  
}
