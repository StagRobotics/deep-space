/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class raiseElevatorLow extends Command {

  // Height in Inches
  double HEIGHT = 9;
  String elevatorState;

  public raiseElevatorLow() {
    requires(Robot.m_wheelyscoop);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Robot.m_wheelyscoop.getEncoderRevolutions() > HEIGHT*8){
      elevatorState = "up";
    } else if(Robot.m_wheelyscoop.getEncoderRevolutions() < HEIGHT*8){
      elevatorState = "down";
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(elevatorState == "down"){
      Robot.m_wheelyscoop.liftElevator();
    } else if(elevatorState == "up"){
      Robot.m_wheelyscoop.lowerElevator();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // Checks if the Encoder Count is greater than the Height times 8. (The Lead screw rotates 8 times per inch)
    if(elevatorState == "down"){
      if(Robot.m_wheelyscoop.getEncoderRevolutions() >= HEIGHT*8){
        return true;
      } else {
        return false;
      }
    } else if (elevatorState == "up"){
      if(Robot.m_wheelyscoop.getEncoderRevolutions() <= HEIGHT*8){
        return true;
      } else {
        return false;
      }
    } else {
        return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_wheelyscoop.stopElevator();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
