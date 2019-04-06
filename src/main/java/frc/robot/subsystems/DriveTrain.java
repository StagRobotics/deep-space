package frc.robot.subsystems;

// Import packages needed to run
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.tankDriveWithJoystick;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

public class DriveTrain extends Subsystem {

	// Initialize Static Variables
	public double DEADBAND = 0.05;

	// Initialize Motor Controllers
	public PWMVictorSPX leftMotor = new PWMVictorSPX(RobotMap.leftMotor);
	public PWMVictorSPX rightMotor = new PWMVictorSPX(RobotMap.rightMotor);

	// Initialize the type of drive -- DiffernetialDrive is used for Tank Drive
	public DifferentialDrive robotDrive = new DifferentialDrive(leftMotor, rightMotor);

	// Initialize Variables for NetworkTable/LimeLight
	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	NetworkTableEntry tx = table.getEntry("tx");
	NetworkTableEntry ty = table.getEntry("ty");
	NetworkTableEntry ta = table.getEntry("ta");
	NetworkTableEntry tv = table.getEntry("tv");
	public NetworkTableEntry pipeline = table.getEntry("pipeline");
	public NetworkTableEntry lightstate = table.getEntry("ledMode");
	
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
		double valid = tv.getDouble(0.0);
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
		double area = ta.getDouble(0.0);
		double ledState = lightstate.getDouble(0.0);
		double areaGuess = 8.2/24;
		SmartDashboard.putNumber("Led State", ledState);
		SmartDashboard.putNumber("LimeLight Targets", valid);
		SmartDashboard.putNumber("LimeLight X", x);
		SmartDashboard.putNumber("LimeLight Y", y);
		SmartDashboard.putNumber("LimeLight Area", area);
		SmartDashboard.putNumber("Left Motor", leftMotor.get());
		SmartDashboard.putNumber("Right Motor", rightMotor.get());
		SmartDashboard.putBoolean("Turtle Mode", Robot.m_drivetrain.driveState);
		SmartDashboard.putBoolean("Left Motor Invert", leftMotor.getInverted());
		SmartDashboard.putBoolean("Right Motor Invert", rightMotor.getInverted());
		SmartDashboard.putNumber("Distance", area*areaGuess);
		SmartDashboard.putNumber("Pipeline", pipeline.getDouble(0.0));
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

		// Flips the motors if they are both inverted
		if(leftMotor.getInverted() == true && rightMotor.getInverted() == true){
			double temp = leftY;
			leftY = rightY;
			rightY = temp;
		}

		// If turbo mode is on then allow motors to run at 100%
		if(driveState == true){
			robotDrive.tankDrive(leftY, rightY);
		}
		
		// If turbo mdoe is off then run motors at 65% scale
		if(driveState == false){
			leftY =0.65 * (leftY * Math.abs(leftY));
			rightY = 0.65 * (rightY * Math.abs(rightY));
			robotDrive.tankDrive(leftY, rightY);
		}
		
		// Puts the values the code is sending to the motors
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

	// Checks if the left motor is inverted
	public boolean checkInvertLeftMotor(){
		return leftMotor.getInverted();
	}

	// Checks if the right motor is inverted
	public boolean checkInvertRightMotor(){
		return rightMotor.getInverted();
	}

	// Used to toggle Motor Inversion
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

	// Used to set the local driveState variable from other commands
	public void setDriveState(boolean state){
		driveState = state;
	}
}