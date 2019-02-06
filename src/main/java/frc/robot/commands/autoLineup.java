package frc.robot.commands;

// Import packages needed to run
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class autoLineup extends Command {
  public autoLineup() {
    requires(Robot.m_drivetrain);
    
  }

	// Initialize variables used for calculations
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

	// Initialize Timers
	Timer timer = new Timer();
	
	@Override
	// Called once at the beginning of the command
  protected void initialize() {
		// Starts the timer
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

		// Counts the number of contours the robot sees
    int numberOfContours = Robot.m_oi.gripPipeline.filterContoursOutput().size();
				int count = 0;
				// If there are no contours then set all values to zero
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
					// If there are countours than drop into this for loop that iterates through each contour
					for(MatOfPoint countour : Robot.m_oi.gripPipeline.filterContoursOutput()){
						Point[] countourArray = countour.toArray();
						Rect target = Imgproc.boundingRect(countour);
						count++;
						// Pulls information about the countour that is being iterated
						int targetHeight = target.height;
						int targetWidth = target.width;
						int targetX = target.x;
						int targetY = target.y;
						int targetArea = targetHeight*targetWidth;
						int targetCenterX = (targetX + (targetX +targetWidth))/2;
						int targetCenterY = (targetY + (targetY + targetHeight))/2;
						// Take the first countour and assign it to the right target
						if(count == 1){
							rightHeight = targetHeight;
							rightWidth = targetWidth;
							rightX = targetX;
							rightY = targetY;
							rightArea = targetArea;
							rightCenterX = targetCenterX;
							rightCenterY = targetCenterY;
						}
						// Take the second countour and assign it to the left target
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
        // Calculates where the center of the target is
        finalTargetCenterX = (leftCenterX + rightCenterX)/2;
				
				// This is the Logic that is used to move the robot based on the target's position
        if(finalTargetCenterX < 220){
          new drive(0.3,0.4).execute();
          SmartDashboard.putString("AutoLine Up", "Too far right");
        } else if(finalTargetCenterX > 260){
          new drive(0.4,0.3).execute();
          SmartDashboard.putString("AutoLine Up", "Too far left");
        } else {
          new drive(0.3,0.3).execute();
          SmartDashboard.putString("AutoLine Up", "Just Right");
        }
				/*SmartDashboard.putNumber("Final Target Center X", finalTargetCenterX);
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
        SmartDashboard.putNumber("Right Center Y", rightCenterY);*/
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
		// Runs the command for 1 second and then stops
    if(timer.get()>1){
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    new drive(0.0,0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}