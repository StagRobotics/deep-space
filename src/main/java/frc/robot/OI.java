package frc.robot;

// Import packages needed to run
import org.opencv.core.Rect;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import frc.robot.subsystems.GripPipeline;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.*;
import org.opencv.core.Mat;
import frc.robot.commands.*;

public class OI {

	// Initialize GripPipeline for vision code
	public GripPipeline gripPipeline = new GripPipeline();

	// Initialize Joysticks
	private Joystick leftJoystick = new Joystick(0);
	private Joystick rightJoystick = new Joystick(1);
	private Joystick auxJoystick = new Joystick(2);

	// Initialize Cameras
	public UsbCamera megaPegCamera = CameraServer.getInstance().startAutomaticCapture(RobotMap.megaPegCameraPort);
	public UsbCamera frontElevatorCamera = CameraServer.getInstance().startAutomaticCapture(RobotMap.frontElevatorCameraPort);
	
	// Initialize VideoSink 
	VideoSink server = CameraServer.getInstance().getServer();
	
	// Initialize varibles used in vision code
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

	Mat source = new Mat();
	Mat output = new Mat();
	public OI() {

		// Set Camera Resolution
		//megaPegCamera.setResolution(640, 320);
		megaPegCamera.setResolution(320, 240);
		frontElevatorCamera.setResolution(160, 120);
		
		//megaPegCamera.setExposureManual(0);
		megaPegCamera.setExposureAuto();
		server.setSource(megaPegCamera);
		megaPegCamera.setFPS(20);
		frontElevatorCamera.setFPS(20);
		
		// Set Joystick Buttons for the Right Joystick
		JoystickButton toggleCamera = new JoystickButton(rightJoystick, 1);

		// Set Joystick Buttons for the Left Joystick
		JoystickButton toggleLight = new JoystickButton(leftJoystick, 1);
		JoystickButton toggleTurtle = new JoystickButton(leftJoystick, 2);
		JoystickButton autoLineUpLeft = new JoystickButton(leftJoystick, 3);
		JoystickButton autoLineUpRight = new JoystickButton(leftJoystick, 4);

		// Set Joystick Buttons for the Aux Joystick
		JoystickButton rollSnowPlowIn = new JoystickButton(auxJoystick, 1);
		JoystickButton driveBackElevator = new JoystickButton(auxJoystick, 2);
		JoystickButton liftBackElevator = new JoystickButton(auxJoystick, 3);
		JoystickButton lowerBackElevator = new JoystickButton(auxJoystick, 4);
		JoystickButton liftFrontElevatorLow = new JoystickButton(auxJoystick, 5);
		JoystickButton lowerFrontElevator = new JoystickButton(auxJoystick, 6);
		JoystickButton manuallyLowerFrontElevator = new JoystickButton(auxJoystick, 7);
		JoystickButton manuallyLiftFrontElevator = new JoystickButton(auxJoystick, 8);
		JoystickButton stopAllMotors = new JoystickButton(auxJoystick, 9);
		JoystickButton liftFrontElevatorHigh = new JoystickButton(auxJoystick, 10);
		JoystickButton manuallyLowerBackElevator = new JoystickButton(auxJoystick, 11);
		JoystickButton manuallyLiftBackElevator = new JoystickButton(auxJoystick,12);
		
		POVButton megaPegButtonUp = new POVButton(auxJoystick, 0);
		POVButton megaPegButtonDown = new POVButton(auxJoystick, 180);
		POVButton megaPegButtonScoringRight = new POVButton(auxJoystick, 90);
		POVButton megaPegButtonScoringLeft = new POVButton(auxJoystick, 270);

		// When the Left Joystick's Button 1 is pressed, toggle the light 
		toggleLight.whenPressed(new light());

		// When the Right Joystick's Button 1 is pressed, change the camera view.
		toggleCamera.whenPressed(new changeCameras());

		toggleTurtle.whenPressed(new toggleDriveState(true));
		toggleTurtle.whenReleased(new toggleDriveState(false));

		autoLineUpLeft.whenPressed(new autoLineupLeft());
		autoLineUpLeft.whenReleased(new drive(0.0, 0.0));
		autoLineUpLeft.whenReleased(new changeCameraExposure());

		autoLineUpRight.whenPressed(new autoLineupRight());
		autoLineUpRight.whenReleased(new drive(0.0, 0.0));
		autoLineUpRight.whenReleased(new changeCameraExposure());

		megaPegButtonUp.whenPressed(new moveMegaPegUpLimitSwitch());
		megaPegButtonDown.whenPressed(new moveMegaPegDownLimitSwitch());
		megaPegButtonScoringLeft.whenPressed(new megaPegScoringCommandGroup());
		megaPegButtonScoringRight.whenPressed(new megaPegScoringCommandGroup());
		// When the Aux Joystick's Button 1 is held, roll the wheely arm in. When the Aux Joystick's button 1 is released, stop the wheely arm.
		rollSnowPlowIn.whileHeld(new rollSnowPlowOut());
		rollSnowPlowIn.whenReleased(new stopSnowPlowMotor());

		// When the Aux Joystick's Button 2 is held, drive the back elevator at 100% for 5 inches
		driveBackElevator.whileHeld(new driveBackElevator(1.0, 5));
		driveBackElevator.whenReleased(new stopDriveBackElevator());

		// When the Aux Joystick's Button 3 is held, lift the back elevator. When the Aux Joystick's button 3 is released, stop the back elevator motors.
		liftBackElevator.whenPressed(new raiseBackElevator());
		manuallyLiftBackElevator.whenPressed(new manuallyLiftBackElevator());
		manuallyLiftBackElevator.whenReleased(new stopBackElevator());

		// When the Aux Joystick's Button 4 is held, lower the back elevator. When the Aux Joystick's button 4 is released, stop the back elevator motors.
		lowerBackElevator.whenPressed(new lowerBackElevator());
		manuallyLowerBackElevator.whenPressed(new manuallyLowerBackElevator());
		manuallyLowerBackElevator.whenReleased(new stopBackElevator());

		liftFrontElevatorLow.whenPressed(new raiseFrontElevatorLowLimitSwitch());

		// When the Aux Joystick's Button 5 is held, lift the front elevator. When the Aux Joystick's button 5 is released, stop the front elevator motors.
		liftFrontElevatorHigh.whenPressed(new raiseFrontElevatorHighLimitSwitch());
		manuallyLiftFrontElevator.whenPressed(new manuallyLiftFrontElevator());
		manuallyLiftFrontElevator.whenReleased(new stopFrontElevator());

		// When the Aux Joystick's Button 6 is held, lower the front elevator. When the Aux Joystick's button 6 is released, stop the front elevator motors.
		lowerFrontElevator.whenPressed(new lowerFrontElevatorLimitSwitch());
		manuallyLowerFrontElevator.whenPressed(new manuallyLowerFrontElevator());
		manuallyLowerFrontElevator.whenReleased(new stopFrontElevator());
		
		// When the Aux Joystick's Button 12 is pressed, stop all of the motors.
		stopAllMotors.whenPressed(new stopAllMotors());

		// Puts Commands on the SmartDashboard
		SmartDashboard.putData("AutoLineup", new autoLineupLeft());
		SmartDashboard.putData("Roll", new rollSnowPlowOut());
		SmartDashboard.putData("Drive to platform", new driveBackElevator(1.0, 7.0));
		SmartDashboard.putData("Climb", new climb());
		SmartDashboard.putData("Invert Drive", new invertDrive());
		SmartDashboard.putData("Change Camera Normal", new changeCameraExposure());
		SmartDashboard.putData("Change Camera Black", new changeCameraExposureBlack());
		SmartDashboard.putData("camera", new changeCameras());
		SmartDashboard.putData("light", new light());
		// Starts a new Vision Thread that runs 
		new Thread(() -> {
			CvSink cvSink = CameraServer.getInstance().getVideo();
			
			//CvSource outputStream = CameraServer.getInstance().putVideo("Processed Vision Camera", 640, 480);
			CvSource outputStream = CameraServer.getInstance().putVideo("Processed Vision Camera", 320, 240);
			source = new Mat();
			output = new Mat();
			
			while(!Thread.interrupted()) {
				if(cameraState == "megaPeg"){
					server.setSource(megaPegCamera);
					cvSink = CameraServer.getInstance().getVideo(megaPegCamera);
				} else if(cameraState == "frontElevator"){
					server.setSource(frontElevatorCamera);
					cvSink = CameraServer.getInstance().getVideo(frontElevatorCamera);
				}
        		cvSink.grabFrame(source, 100);
       			SmartDashboard.putString("Error Message", cvSink.getError());
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
	public Mat getOutputMat(){
		return source;
	}
}
