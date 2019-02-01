/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.setElevatorEncoderValues;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

public class WheelyScoop extends Subsystem {
 
  public SpeedController elevatorMotor = new Spark(RobotMap.elevatorMotor);
  public SpeedController snowPlowMotor = new Spark(RobotMap.snowPlowMotor);

  public Encoder elevatorEncoder = new Encoder(RobotMap.elevatorEncoderPortOne, RobotMap.elevatorEncoderPortTwo, false, Encoder.EncodingType.k4X);
  
  double MOTORSPEED = 0.7;
  float COUNTTOREVOLUTION = 2048/360/360;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new setElevatorEncoderValues());
  }

  public void resetEncoder() {
    elevatorEncoder.reset();
  }

  public double getEncoderRevolutions(){
    // This converts the Encoder count to revolutions
    return elevatorEncoder.get()/COUNTTOREVOLUTION;
  }

  public void liftElevator(){
    elevatorMotor.set(MOTORSPEED);
  }

  public void lowerElevator(){
    elevatorMotor.set(-MOTORSPEED);
  }

  public void stopElevator(){
    elevatorMotor.set(0.0);
  }

  public void rollSnowPlowIn(){
    snowPlowMotor.set(MOTORSPEED);
  }

  public void rollSnowPlowOut(){
    snowPlowMotor.set(-MOTORSPEED);
  }

  public void stopSnowPlow(){
    snowPlowMotor.set(0.0);
  }
}
