/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class ToggleMegaPeg extends Command {
  public ToggleMegaPeg() {
    requires(Robot.m_megapeg);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    

    if(Robot.m_megapeg.getMegaPegState() == "up"){
      Robot.m_megapeg.lowerMegaPeg();
    }
    else if (Robot.m_megapeg.getMegaPegState() == "down"){
      Robot.m_megapeg.liftMegaPeg();;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (Robot.m_megapeg.getMegaPegState() == "down"){
      if(Robot.m_megapeg.getAngle() >= 45){
        Robot.m_megapeg.toggleMegaPeg();
        return true;
      }
    } else if(Robot.m_megapeg.getMegaPegState() == "up"){
      if(Robot.m_megapeg.getAngle() <= 0){
        Robot.m_megapeg.toggleMegaPeg();
        return true;
      }
    } 
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_megapeg.stopMegaPeg();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
