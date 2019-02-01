package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {

	double leftSpeedLocal = 0;
	double rightSpeedLocal = 0;

	public Drive(double leftSpeed, double rightSpeed) {
		requires(Robot.m_drivetrain);
		requires(Robot.m_megapeg);
		leftSpeedLocal = leftSpeed;
		rightSpeedLocal = rightSpeed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
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
