/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.commands.*;


/**
 * Add your docs here.
 */
public class MegaPeg extends Subsystem {
  
  public double DEADBAND = 0.20;

  Gyro gyro = new AnalogGyro(RobotMap.analogGyro);
  ADXL345_I2C accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Range.k2G);
  public SpeedController megaPegMotor = new Spark(RobotMap.megaPegMotor);

  private String megaPegState = "up";

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new moveMegaPegWithJoystick());
  }

  public double getAngle(){
    return gyro.getAngle();
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
    if( gyro.getAngle() < 50){
      megaPegMotor.set(-0.35);
    }
  }
  
  public void lowerMegaPeg(){
    if ( gyro.getAngle() > 0){
      megaPegMotor.set(0.35);
    }
  }

  public void stopMegaPeg(){
    megaPegMotor.set(0);
  }
  
  public void forceLowerMegaPeg(){
    megaPegMotor.set(0.35);
  }

  public void scoringMegaPeg(){
    if ( gyro.getAngle() > 15 && megaPegState == "up"){
      megaPegMotor.set(0.35);
    }
    else if ( gyro.getAngle() < 15 && megaPegState == "downs"){
      megaPegMotor.set(-0.35);
    }
  }

  public void resetGyro(){
    gyro.reset();
  }
  
  public void moveMegaPegWithJoystick(double speed){
    if (speed < DEADBAND && speed > -DEADBAND){
			speed = 0.0;
		}

    megaPegMotor.set(speed);
  }
}
