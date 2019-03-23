package frc.robot.subsystems;

// Import packages needed to run
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.tankDriveWithJoystick;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	// Initialize Static Variables
	public double DEADBAND = 0.05;

	// Initialize Motor Controllers
	public PWMVictorSPX leftMotor = new PWMVictorSPX(RobotMap.leftMotor);
	public PWMVictorSPX rightMotor = new PWMVictorSPX(RobotMap.rightMotor);

	// Initialize the type of drive -- DiffernetialDrive is used for Tank Drive
	public DifferentialDrive robotDrive = new DifferentialDrive(leftMotor, rightMotor);

	// Initialize the light used for the camera LED ring
	public Relay light = new Relay(RobotMap.cameraLEDPort);
	
	// Initialize the starting state of the camera LED ring
	public String lightState = "off";

	// Initialize the starting state of the DriveState
	public boolean driveState = false;
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
		SmartDashboard.putNumber("Distance to platform", Robot.m_backclimber.getDistanceFromPlatform());
		SmartDashboard.putNumber("Auto Climb", Robot.m_backclimber.getAutoClimbStep());
		SmartDashboard.putString("Light State", lightState);
		SmartDashboard.putBoolean("Turtle Mode", Robot.m_drivetrain.driveState);
		SmartDashboard.putBoolean("Left Motor Invert", leftMotor.getInverted());
		SmartDashboard.putBoolean("Right Motor Invert", rightMotor.getInverted());
		SmartDashboard.putBoolean("Mega Peg bottom", Robot.m_megapeg.getBottomLimitSwitch());
		SmartDashboard.putBoolean("Mega Peg Up", Robot.m_megapeg.getTopLimitSwitch());
		SmartDashboard.putBoolean("Front Middle Limit Switch", Robot.m_wheelyscoop.frontElevatorLowLimitSwitch.get());
		SmartDashboard.putBoolean("back Bottom Limit Switch", Robot.m_backclimber.backElevatorBottomLimitSwitch.get());
		
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
		if(leftMotor.getInverted() == true && rightMotor.getInverted() == true){
			double temp = leftY;
			leftY = rightY;
			rightY = temp;
		}
		if(driveState == true){
			robotDrive.tankDrive(leftY, rightY);
		}
		if(driveState == false){
			leftY =0.65 * (leftY * Math.abs(leftY));
			rightY = 0.65 * (rightY * Math.abs(rightY));
			robotDrive.tankDrive(leftY, rightY);
		}
		
		SmartDashboard.putNumber("LeftY", leftY);
		SmartDashboard.putNumber("RightY", rightY);
	}

	// Inverts the left motor to go in the opposite direction
	public void invertLeftMotor(boolean invertLeftMotor) {
		leftMotor.setInverted(invertLeftMotor);
	}

	// Inverts the right motor to go in the opposite direction
	public void invertRightMotor(boolean invertRightMotor) {
		rightMotor.setInverted(invertRightMotor);
	}

	public boolean checkInvertLeftMotor(){
		return leftMotor.getInverted();
	}

	public boolean checkInvertRightMotor(){
		return rightMotor.getInverted();
	}

	public void toggleMotorInversion(){
		if(leftMotor.getInverted() == true){
			leftMotor.setInverted(false);
		} else if(leftMotor.getInverted() == false){
			leftMotor.setInverted(true);
		}
		if(rightMotor.getInverted() == true){
			rightMotor.setInverted(false);
		} else if (rightMotor.getInverted() == false){
			rightMotor.setInverted(true);
		}
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

	public void toggleCamera(){
		if(Robot.m_oi.cameraState == "megaPeg"){
			Robot.m_oi.cameraState = "frontElevator";
		} else if(Robot.m_oi.cameraState == "frontElevator"){
			Robot.m_oi.cameraState = "megaPeg";
		}
	}

	public void setDriveState(boolean state){
		driveState = state;
	}
}