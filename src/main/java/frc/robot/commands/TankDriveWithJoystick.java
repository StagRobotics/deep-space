package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class tankDriveWithJoystick extends Command {

	public tankDriveWithJoystick() {
		requires(Robot.m_drivetrain);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.m_drivetrain.drive(Robot.m_oi.getLeftJoystick().getY(), Robot.m_oi.getRightJoystick().getY());
		Robot.m_drivetrain.log();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_drivetrain.drive(0.0, 0.0);
	}
}
