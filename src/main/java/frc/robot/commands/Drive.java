package frc.robot.commands;

// Import packages needed to run
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class drive extends Command {

	// Initialize local speed variables
	double leftSpeedLocal = 0;
	double rightSpeedLocal = 0;

	// Initialize drive command that requires a left and right speed
	public drive(double leftSpeed, double rightSpeed) {
		
		// These are subsystems this command requires
		requires(Robot.m_drivetrain);

		// Sets the local speeds to the speeds passed in from calling the command
		leftSpeedLocal = leftSpeed;
		rightSpeedLocal = rightSpeed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		// Sets the motors to the local speed
		Robot.m_drivetrain.leftMotor.set(leftSpeedLocal);
		Robot.m_drivetrain.rightMotor.set(-rightSpeedLocal);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
