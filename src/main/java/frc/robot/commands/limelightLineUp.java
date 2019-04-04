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
import edu.wpi.first.networktables.*;


public class limelightLineUp extends Command {
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");
  double areaGuess = 24/8.2;
  public limelightLineUp() {
    requires(Robot.m_drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
   
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double valid = tv.getDouble(0.0);
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    x = x/100;
    if(area > 3.94){
      if(x > 0.0){
        new drive(0.3+x, 0.3).execute();
        SmartDashboard.putString("LimeLight", "Moving Right");
      } else if(x < 0.0){
        new drive(0.3, 0.3-x).execute();
        SmartDashboard.putString("LimeLight", "Moving Left");
      } else {
        new drive(0.3,0.3).execute();
        SmartDashboard.putString("LimeLight", "Moving Straight");
        SmartDashboard.putNumber("Distance", areaGuess*area);
      }
    } else if(area > 8.4){
      if(x > 0.0){
        new drive(0.2+x, 0.2).execute();
        SmartDashboard.putString("LimeLight", "Moving Right");
      } else if(x < 0.0){
        new drive(0.2, 0.2-x).execute();
        SmartDashboard.putString("LimeLight", "Moving Left");
      } else {
        new drive(0.2,0.2).execute();
        SmartDashboard.putString("LimeLight", "Moving Straight");
        SmartDashboard.putNumber("Distance", areaGuess*area);
      }
    } else if(area > 15){
        if(x > 0.0){
          new drive(0.1+x, 0.1).execute();
          SmartDashboard.putString("LimeLight", "Moving Right");
        } else if(x < 0.0){
          new drive(0.1, 0.1-x).execute();
          SmartDashboard.putString("LimeLight", "Moving Left");
        } else {
          new drive(0.1,0.1).execute();
          SmartDashboard.putString("LimeLight", "Moving Straight");
          SmartDashboard.putNumber("Distance", areaGuess*area);
        }
    }else {
      if(x > 0.0){
        new drive(0.4+x, 0.4).execute();
        SmartDashboard.putString("LimeLight", "Moving Right");
      } else if(x < 0.0){
        new drive(0.4, 0.4-x).execute();
        SmartDashboard.putString("LimeLight", "Moving Left");
      } else {
        new drive(0.4,0.4).execute();
        SmartDashboard.putString("LimeLight", "Moving Straight");
        SmartDashboard.putNumber("Distance", areaGuess*area);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(ta.getDouble(0.0) > 45){
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    new drive(0.0, 0.0).execute();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
