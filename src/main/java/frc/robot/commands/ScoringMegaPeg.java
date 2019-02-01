/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class scoringMegaPeg extends Command {
  public scoringMegaPeg() {
    requires(Robot.m_megapeg);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_megapeg.scoringMegaPeg();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.m_megapeg.getMegaPegState() == "up" && Robot.m_megapeg.getAngle() <= 15){
      return true;
    } else if(Robot.m_megapeg.getMegaPegState() == "down" && Robot.m_megapeg.getAngle() >=15) {
      return true;
    } else{
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_megapeg.toggleMegaPeg();
    Robot.m_megapeg.stopMegaPeg();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.m_megapeg.stopMegaPeg();
  }
}
