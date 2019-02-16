package frc.robot.commands;

// Import packages needed to run
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class driveBackElevator extends Command {

  // Initialize local variables
  double localDriveSpeed;
  double localDistance;

  // Initialize driveBackElevator command that requires a speed and distance in inches
  public driveBackElevator(double driveSpeed, double distance) {

    // These are subsystems this command requires
    requires(Robot.m_backclimber);

    // Sets the local varibles equal to the varibles that were passed in
    localDriveSpeed = driveSpeed;
    localDistance = distance;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Set the backElevatorDrive to the local speed
    Robot.m_backclimber.backElevatorDrive(localDriveSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.m_backclimber.getDistanceFromPlatform() <= localDistance){
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // Stop the backElevatorDrive motor
    Robot.m_backclimber.backElevatorDrive(0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}