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

	UsbCamera megaPegCamera = CameraServer.getInstance().startAutomaticCapture(0);
		
	UsbCamera frontElevatorCamera = CameraServer.getInstance().startAutomaticCapture(1);
	
	VideoSink server = CameraServer.getInstance().getServer();

	public String cameraState = "megaPeg";
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

		megaPegCamera.setResolution(640, 480);
		frontElevatorCamera.setResolution(640, 480);

		JoystickButton rollSnowPlowIn = new JoystickButton(auxJoystick, 1);
		
		JoystickButton liftFrontElevatorHigh = new JoystickButton(auxJoystick, 5);
		JoystickButton lowerFrontElevator = new JoystickButton(auxJoystick, 6);
		JoystickButton liftBackElevator = new JoystickButton(auxJoystick, 3);
		JoystickButton lowerBackElevator = new JoystickButton(auxJoystick, 4);
		JoystickButton driveBackElevator = new JoystickButton(auxJoystick, 2);
		JoystickButton manuallyLiftFrontElevator = new JoystickButton(auxJoystick, 7);
		JoystickButton manuallyLowerFrontElevator = new JoystickButton(auxJoystick, 8);
		JoystickButton manuallyLiftBackElevator = new JoystickButton(auxJoystick, 9);
		JoystickButton manuallyLowerBackElevator = new JoystickButton(auxJoystick, 10);
		JoystickButton stopAllMotors = new JoystickButton(auxJoystick, 12);

		//JoystickButton releaseArms = new JoystickButton(auxJoystick, 5);
	
		stopAllMotors.whenPressed(new stopAllMotors());

		SmartDashboard.putData("AutoLineup", new autoLineup());
		SmartDashboard.putData("Reset Encoder", new resetElevatorEncoder());
		SmartDashboard.putData("Roll", new rollSnowPlowIn());

		rollSnowPlowIn.whileHeld(new rollSnowPlowIn());
		rollSnowPlowIn.whenReleased(new stopSnowPlowMotor());

		liftFrontElevatorHigh.whenPressed(new raiseFrontElevatorHighLimitSwitch());
		manuallyLiftFrontElevator.whileHeld(new raiseFrontElevatorHighLimitSwitch());
		manuallyLiftFrontElevatorHigh.whenReleased(new stopFrontElevator());
		lowerFrontElevator.whenPressed(new lowerFrontElevatorLimitSwitch());
		manuallyLowerFrontElevator.whileHeld(new lowerFrontELevatorLimitSwitch());
		manuallyLowerFrontElevator.whenReleased(new stopFrontElevator());
		liftBackElevator.whenPressed(new raiseBackElevator());
		manuallyLiftBackElevator.whileHeld(new raiseBackElevator());
		manuallyLiftBackElevator.whenReleased(new stopBackElevator());
		lowerBackElevator.whenPressed(new lowerBackElevator());
		manuallyLowerBackElevator.whileHeld(new lowerBackElevator());
		manuallyLowerBackElevator.whenReleased(new stopBackElevator());
		driveBackElevator.whileHeld(new driveBackElevator(1.0, 5));
		driveBackElevator.whenReleased(new stopDriveBackElevator());

		new Thread(() -> {

			if(cameraState == "megaPeg"){
				server.setSource(megaPegCamera);
			} else if(cameraState == "frontElevator"){
				server.setSource(frontElevatorCamera);
			}
			
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
