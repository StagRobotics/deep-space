package frc.robot.commands;

// Import packages needed to run
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class climb extends CommandGroup {
  
  public climb() {
    // These are the subsystems that the CommandGroup uses
    requires(Robot.m_backclimber);

    // Step one - Drop arms onto the platform
    addSequential(new releaseArms());

    // Step two - Lower back and front elevators to raise the robot
    addParallel(new lowerBackElevator());
    addSequential(new lowerFrontElevatorLimitSwitch());

    // Step three - Drive the robot onto the platform
    addParallel(new driveBackElevator(1.0, 31));
    addSequential(new drive(0.4, 0.4));

    // Step Four - Stop all motors once on top of the platform
    addSequential(new stopAllMotors());

    // Step Five - Raise the back elevator wheels off of the ground
    addSequential(new raiseBackElevator());
  }
}
