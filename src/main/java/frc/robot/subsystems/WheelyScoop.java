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
import edu.wpi.first.wpilibj.DigitalInput;

public class WheelyScoop extends Subsystem {
 
  public SpeedController frontElevatorMotor = new Spark(RobotMap.frontElevatorMotor);
  public SpeedController snowPlowMotor = new Spark(RobotMap.snowPlowMotor);

  public Encoder elevatorEncoder = new Encoder(RobotMap.frontElevatorEncoderPortOne, RobotMap.frontElevatorEncoderPortTwo, false, Encoder.EncodingType.k4X);
  
  DigitalInput frontElevatorTopLimitSwitch = new DigitalInput(RobotMap.frontElevatorTopLimitSwitch);
  DigitalInput frontElevatorLowLimitSwitch = new DigitalInput(RobotMap.frontElevatorLowLimitSwitch);
  DigitalInput frontElevatorBottomLimitSwitch = new DigitalInput(RobotMap.frontElevatorBottomLimitSwitch);


  double MOTORSPEED = 0.7;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new setElevatorEncoderValues());
  }

  public void resetEncoder() {
    elevatorEncoder.reset();
  }

  public double getEncoderRevolutions(){
    // This converts the Encoder count to revolutions
    return elevatorEncoder.get()/5.68/360;
  }

  public void liftFrontElevator(){
    frontElevatorMotor.set(MOTORSPEED);
  }

  public void lowerFrontElevator(){
    frontElevatorMotor.set(-MOTORSPEED);
  }

  public void stopFrontElevator(){
    frontElevatorMotor.set(0.0);
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

  public boolean checkFrontElevatorUp(){
    return frontElevatorTopLimitSwitch.get();
  }

  public boolean checkFrontElevatorLow(){
    return frontElevatorLowLimitSwitch.get();
  }

  public boolean checkFrontElevatorBottom(){
    return frontElevatorBottomLimitSwitch.get();
  }
}
