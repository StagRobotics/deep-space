package frc.robot;

// Import packages needed to run

import frc.robot.subsystems.GripPipeline;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;

public class OI {

	// Initialize GripPipeline for vision code
	public GripPipeline gripPipeline = new GripPipeline();

	// Initialize Joysticks
	private Joystick leftJoystick = new Joystick(0);
	private Joystick rightJoystick = new Joystick(1);
	private Joystick auxJoystick = new Joystick(2);

	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

	public OI() {

		// Set Joystick Buttons for the Right Joystick
		JoystickButton toggleCamera = new JoystickButton(rightJoystick, 1);
		JoystickButton limelightOff = new JoystickButton(leftJoystick, 5);

		// Set Joystick Buttons for the Left Joystick
		JoystickButton toggleLight = new JoystickButton(leftJoystick, 1);
		JoystickButton toggleTurtle = new JoystickButton(leftJoystick, 2);
		JoystickButton autoLineUpLimeLight = new JoystickButton(leftJoystick, 3);
		JoystickButton limelightOn = new JoystickButton(leftJoystick, 4);

		// Set Joystick Buttons for the Aux Joystick
		JoystickButton rollSnowPlowIn = new JoystickButton(auxJoystick, 1);
		JoystickButton driveBackElevator = new JoystickButton(auxJoystick, 2);
		//JoystickButton liftBackElevator = new JoystickButton(auxJoystick, 3);
		//JoystickButton lowerBackElevator = new JoystickButton(auxJoystick, 4);
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

		// Toggles Turbo and Turtle mode on button 2
		toggleTurtle.whenPressed(new toggleDriveState(true));
		toggleTurtle.whenReleased(new toggleDriveState(false));

		// Turns on autolineup and then stops when button 3 is released
		autoLineUpLimeLight.whenPressed(new lineupWithLight());
		autoLineUpLimeLight.whenReleased(new lineupTurnOff());
		
		// Sets commands for Mega Peg to work using limit Switches on the hat
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
		//liftBackElevator.whenPressed(new raiseBackElevator());
		manuallyLiftBackElevator.whenPressed(new manuallyLiftBackElevator());
		manuallyLiftBackElevator.whenReleased(new stopBackElevator());

		// When the Aux Joystick's Button 4 is held, lower the back elevator. When the Aux Joystick's button 4 is released, stop the back elevator motors.
		//lowerBackElevator.whenPressed(new lowerBackElevator());
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
		SmartDashboard.putData("Roll", new rollSnowPlowOut());
		SmartDashboard.putData("Drive to platform", new driveBackElevator(1.0, 7.0));
		SmartDashboard.putData("Invert Drive", new invertDrive());
		SmartDashboard.putData("light", new turnLightOn());
		SmartDashboard.putData("pipeline", new lineupTurnOff());		
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
