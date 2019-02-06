/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class RobotMap {
  // PWM
  public static int leftMotor = 0;
  public static int rightMotor = 1;
  public static int megaPegMotor = 2;
  public static int frontElevatorMotor = 3;
  public static int backElevatorMotor = 4;
  public static int backElevatorDriveMotor = 5;
  
  // Relay
  public static int armReleaseMotor = 0;
  public static int snowPlowMotor = 1;
  public static int cameraLEDPort = 3;

  // Digital
  public static int frontElevatorEncoderPortOne = 0;
  public static int frontElevatorEncoderPortTwo = 1;
  public static int frontElevatorTopLimitSwitch = 2;
  public static int frontElevatorLowLimitSwitch = 3;
  public static int frontElevatorBottomLimitSwitch = 4;
  public static int backElevatorBottomLimitSwitch = 5;
  public static int backElevatorTopLimitSwitch = 6;
  public static int backElevatorDriveEncoderPortOne = 7;
  public static int backElevatorDriveEncoderPortTwo = 8;

  // Analog Ports
  public static int analogGyro = 0;
}
