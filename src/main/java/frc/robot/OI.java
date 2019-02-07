/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Rect;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import frc.robot.subsystems.GripPipeline;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.*;
import org.opencv.core.Mat;



import org.opencv.core.*;
import frc.robot.commands.*;


public class OI {

	public GripPipeline gripPipeline = new GripPipeline();

	private Joystick leftJoystick = new Joystick(0);
	private Joystick rightJoystick = new Joystick(1);
	private Joystick auxJoystick = new Joystick(2);

	public double speed = 0.25;

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

	public double getSpeed() {
		return speed;
	}

	public OI() {

		JoystickButton rollSnowPlowIn = new JoystickButton(auxJoystick, 1);

		POVButton liftElevatorHigh = new POVButton(auxJoystick, 0);
		POVButton liftElevatorLow = new POVButton(auxJoystick, 180);
		JoystickButton lowerElevator = new JoystickButton(auxJoystick, 6);
	
		SmartDashboard.putData("AutoLineup", new autoLineup());
		SmartDashboard.putData("Reset Encoder", new resetElevatorEncoder());
		SmartDashboard.putData("Roll", new rollSnowPlowIn());

		rollSnowPlowIn.whileHeld(new rollSnowPlowIn());
		rollSnowPlowIn.whenReleased(new stopSnowPlowMotor());

		//liftElevatorHigh.whenPressed(new raiseFrontElevatorHigh());
		//liftElevatorLow.whenPressed(new raiseFrontElevatorLow());
		//lowerElevator.whenPressed(new lowerFrontElevator());
		liftElevatorHigh.whenPressed(new raiseFrontElevatorHighLimitSwitch());
		liftElevatorLow.whenPressed(new raiseFrontElevatorLowLimitSwitch());
		lowerElevator.whenPressed(new lowerFrontElevatorLimitSwitch());

		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(640, 480);
			
			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("Processed Vision Camera", 640, 480);
			
			Mat source = new Mat();
			Mat output = new Mat();
			
			while(!Thread.interrupted()) {
        		cvSink.grabFrame(source, 100);
       			SmartDashboard.putString("Error Message", cvSink.getError());
				//Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
				gripPipeline.process(source);
				output = gripPipeline.cvDilateOutput();
				outputStream.putFrame(output);
				/*int numberOfContours = gripPipeline.filterContoursOutput().size();
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
					for(MatOfPoint countour : gripPipeline.filterContoursOutput()){
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
				}*/
				
				/*SmartDashboard.putNumber("Left Height", leftHeight);
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
				*/
				SmartDashboard.putNumber("Size of Mat Array Points", gripPipeline.filterContoursOutput().size());
			}
		}).start();
		
	}

	public Joystick getLeftJoystick() {
		return leftJoystick;
	}

	public Joystick getRightJoystick() {
		return rightJoystick;
	}

	public Joystick getAuxJoystick() {
		return auxJoystick;
	}
}