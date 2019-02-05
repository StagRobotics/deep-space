/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class raiseFrontElevatorLowLimitSwitch extends Command {
  
  String elevatorPosition;

  public raiseFrontElevatorLowLimitSwitch() {
    requires(Robot.m_wheelyscoop);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Robot.m_wheelyscoop.checkFrontElevatorBottom()){
      elevatorPosition = "Up";
    } else if (Robot.m_wheelyscoop.checkFrontElevatorUp()){
      elevatorPosition = "Down";
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(elevatorPosition == "Up"){
      Robot.m_wheelyscoop.lowerFrontElevator();
    } else if(elevatorPosition == "Down"){
      Robot.m_wheelyscoop.liftFrontElevator();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.m_wheelyscoop.checkFrontElevatorLow()){
      return false;
    } else {
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_wheelyscoop.stopFrontElevator();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
