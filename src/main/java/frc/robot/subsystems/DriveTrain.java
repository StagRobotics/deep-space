package frc.robot.subsystems;

// Import packages needed to run
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.tankDriveWithJoystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	// Initialize Static Variables
	public double DEADBAND = 0.20;

	// Initialize Motor Controllers
	public PWMVictorSPX leftMotor = new PWMVictorSPX(RobotMap.leftMotor);
	public PWMVictorSPX rightMotor = new PWMVictorSPX(RobotMap.rightMotor);

	// Initialize the type of drive -- DiffernetialDrive is used for Tank Drive
	public DifferentialDrive robotDrive = new DifferentialDrive(leftMotor, rightMotor);

	// Initialize the light used for the camera LED ring
	public Relay light = new Relay(RobotMap.cameraLEDPort);
	
	// Initialize the starting state of the camera LED ring
	public String lightState = "off";

	// Creates the DriveTrain Subsystem
	public DriveTrain() {
		super();
		System.out.println("Drive");
	}

	@Override
	public void initDefaultCommand() {
		// Sets the default command to control the robot with tank drive
		setDefaultCommand(new tankDriveWithJoystick());
	}

	// This is called once every millisecond and is used to print information to the SmartDashboard
	public void log() {
		SmartDashboard.putNumber("Left Motor", leftMotor.get());
		SmartDashboard.putNumber("Right Motor", rightMotor.get());
	}

	// Passes speeds for the motors to the tankDrive part of DifferentialDrive
	public void drive(double leftY, double rightY) {

		// Prevents motor movement if the input is between the DEADBAND and the negative of the DEADBAND
		if (rightY < DEADBAND && rightY > -DEADBAND) {
			rightY = 0.0;
		}

		// Prevents motor movement if the input is between the DEADBAND and the negative of the DEADBAND
		if (leftY < DEADBAND && leftY > -DEADBAND) {
			leftY = 0.0;
		}

		// Drives the robot in a tank drive style
		robotDrive.tankDrive(leftY, rightY);
	}

	// Inverts the left motor to go in the opposite direction
	public void invertLeftMotor(boolean invertLeftMotor) {
		leftMotor.setInverted(invertLeftMotor);
	}

	// Inverts the right motor to go in the opposite direction
	public void invertRightMotor(boolean invertRightMotor) {
		rightMotor.setInverted(invertRightMotor);
	}

	// Returns the current state of the camera LED light
	public String getLightState(){
		return lightState;
	}

	// Turns the light on or off based on its previous state
	public void toggleLight(){
		// Changes the light to on if called when the light is off
		if(lightState == "off"){
			lightState = "on";
			light.set(Relay.Value.kForward);
		}
		// Changes the light to off if called when the light is on
		else if(lightState == "on"){
			lightState = "off";
			light.set(Relay.Value.kOff);
		}
	}
}