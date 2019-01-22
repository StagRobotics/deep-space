/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class autoLineup extends Command {
  public autoLineup() {
    requires(Robot.m_drivetrain);
    
  }

  Rect left;
	Rect right;
	int leftHeight = 0;
	int leftWidth = 0;
	int leftX = 0;
	int leftY = 0;
	int leftArea = 0;
	int leftCenterX = 0;
	int leftCenterY = 0;
	int rightHeight = 0;
	int rightWidth = 0;
	int rightX = 0;
	int rightY = 0;
	int rightArea = 0;
	int rightCenterX = 0;
  int rightCenterY = 0;
  int midPoint = 320;
  int finalTargetCenterX = 0;

  Timer timer = new Timer();
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.start();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int numberOfContours = Robot.m_oi.gripPipeline.filterContoursOutput().size();
				int count = 0;
				if(numberOfContours == 0){
					leftHeight = 0;
					leftWidth = 0;
					leftX = 0;
					leftY = 0;
					leftArea = 0;
					leftCenterX = 0;
					leftCenterY = 0;
					rightHeight = 0;
					rightWidth = 0;
					rightX = 0;
					rightY = 0;
					rightArea = 0;
					rightCenterX = 0;
					rightCenterY = 0;
				}else{
					for(MatOfPoint countour : Robot.m_oi.gripPipeline.filterContoursOutput()){
						Point[] countourArray = countour.toArray();
						Rect target = Imgproc.boundingRect(countour);
						count++;
						int targetHeight = target.height;
						int targetWidth = target.width;
						int targetX = target.x;
						int targetY = target.y;
						int targetArea = targetHeight*targetWidth;
						int targetCenterX = (targetX + (targetX +targetWidth))/2;
						int targetCenterY = (targetY + (targetY + targetHeight))/2;

						if(count == 1){
							rightHeight = targetHeight;
							rightWidth = targetWidth;
							rightX = targetX;
							rightY = targetY;
							rightArea = targetArea;
							rightCenterX = targetCenterX;
							rightCenterY = targetCenterY;
						}
						else if(count==2){
							leftHeight = targetHeight;
							leftWidth = targetWidth;
							leftX = targetX;
							leftY = targetY;
							leftArea = targetArea;
							leftCenterX = targetCenterX;
							leftCenterY = targetCenterY;
						}
						else break;
					}
				}
        
        finalTargetCenterX = (leftCenterX + rightCenterX)/2;
        SmartDashboard.putNumber("Final Target Center X", finalTargetCenterX);
        if(finalTargetCenterX < 220){
          new Drive(0.3,0.4).execute();
          SmartDashboard.putString("AutoLine Up", "Too far right");
        } else if(finalTargetCenterX > 260){
          new Drive(0.4,0.3).execute();
          SmartDashboard.putString("AutoLine Up", "Too far left");
        } else {
          new Drive(0.3,0.3).execute();
          SmartDashboard.putString("AutoLine Up", "Just Right");
        }


				SmartDashboard.putNumber("Left Height", leftHeight);
				SmartDashboard.putNumber("Left Width", leftWidth);
				SmartDashboard.putNumber("Left X", leftX);
				SmartDashboard.putNumber("Left Y", leftY);
				SmartDashboard.putNumber("Left Area", leftArea);
				SmartDashboard.putNumber("Left Center X", leftCenterX);
				SmartDashboard.putNumber("Left Center Y", leftCenterY);
				SmartDashboard.putNumber("Right Height", rightHeight);
				SmartDashboard.putNumber("Right Width", rightWidth);
				SmartDashboard.putNumber("Right X", rightX);
				SmartDashboard.putNumber("Right Y", rightY);
				SmartDashboard.putNumber("Right Area", rightArea);
				SmartDashboard.putNumber("Right Center X", rightCenterX);
        SmartDashboard.putNumber("Right Center Y", rightCenterY);
        
    

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(timer.get()>1){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    new Drive(0.0,0.0).execute();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
