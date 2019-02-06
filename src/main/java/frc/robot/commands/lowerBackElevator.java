package frc.robot.commands;

// Import packages needed to run
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class lowerBackElevator extends Command {

  // Initialize lowerBackElevator command
  public lowerBackElevator() {

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

    // Lowers the back elevator
    Robot.m_backclimber.lowerBackElevator();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    // The front elevator limit switch returns true until pressed this allows to stop the motor when the elevator hits the limit switch
    if(Robot.m_backclimber.checkBackElevatorBottom()){
      return false;
    } else {
      return true;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    // Turn off the back elevator motor
    Robot.m_backclimber.stopBackElevator();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}