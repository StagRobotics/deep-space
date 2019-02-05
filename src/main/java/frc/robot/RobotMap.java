/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.

  // PWM
  public static int leftMotor = 0;
  public static int rightMotor = 1;
  public static int megaPegMotor = 2;
  public static int frontElevatorMotor = 3;
  public static int snowPlowMotor = 4;

  // Digital
  public static int frontElevatorEncoderPortOne = 0;
  public static int frontElevatorEncoderPortTwo = 1;
  public static int frontElevatorTopLimitSwitch = 2;
  public static int frontElevatorLowLimitSwitch = 3;
  public static int frontElevatorBottomLimitSwitch = 4;
  // Analog Ports
  public static int analogGyro = 0;
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
