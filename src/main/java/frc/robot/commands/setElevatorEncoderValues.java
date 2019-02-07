/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class setElevatorEncoderValues extends Command {
  public setElevatorEncoderValues() {
    requires(Robot.m_wheelyscoop);
    requires(Robot.m_backclimber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_wheelyscoop.elevatorEncoder.setMaxPeriod(1);
    Robot.m_wheelyscoop.elevatorEncoder.setMinRate(10);
    Robot.m_wheelyscoop.elevatorEncoder.setDistancePerPulse(0.0005);
    Robot.m_wheelyscoop.elevatorEncoder.setReverseDirection(false);
    Robot.m_wheelyscoop.elevatorEncoder.setSamplesToAverage(7);
    Robot.m_backclimber.backElevatorDriveEncoder.setMaxPeriod(1);
    Robot.m_backclimber.backElevatorDriveEncoder.setMinRate(10);
    Robot.m_backclimber.backElevatorDriveEncoder.setDistancePerPulse(0.0005);
    Robot.m_backclimber.backElevatorDriveEncoder.setReverseDirection(false);
    Robot.m_backclimber.backElevatorDriveEncoder.setSamplesToAverage(7);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
