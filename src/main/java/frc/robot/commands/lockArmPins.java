package frc.robot.commands;

// Import packages needed to run
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class lockArmPins extends Command {

  // Initialize lockArmPins command
  public lockArmPins() {

    // These are subsystems this command requires
    requires(Robot.m_backclimber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // Locks the arm pins in place
    Robot.m_backclimber.lockArmPins();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    // End this command immediantely 
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
