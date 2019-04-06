package frc.robot.subsystems;

// Import packages needed to run
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;;

public class WheelyScoop extends Subsystem {
 
  // Initialize Motor Controllers
  public SpeedController frontElevatorMotor = new Talon(RobotMap.frontElevatorMotor);

  // Initialize Spikes
  public Relay snowPlowMotor = new Relay(RobotMap.snowPlowMotor);
  
  // Initialize Limit Switches
  public DigitalInput frontElevatorTopLimitSwitch = new DigitalInput(RobotMap.frontElevatorTopLimitSwitch);
  public DigitalInput frontElevatorLowLimitSwitch = new DigitalInput(RobotMap.frontElevatorLowLimitSwitch);
  public DigitalInput frontElevatorBottomLimitSwitch = new DigitalInput(RobotMap.frontElevatorBottomLimitSwitch);

  // Initialize Static Variables
  double MOTORSPEED = 1.0;
  double MOTORSPEEDUP = 0.75;

  @Override
  public void initDefaultCommand() {
  }

  // Lifts the front elevator based on the MOTORSPEED variable
  public void liftFrontElevator(){
    frontElevatorMotor.set(MOTORSPEEDUP);
  }

  // Lowers the front elevator based on the MOTORSPEED variable
  public void lowerFrontElevator(){
    frontElevatorMotor.set(-MOTORSPEED);
  }

  // Turns off the motor for the front elevator
  public void stopFrontElevator(){
    frontElevatorMotor.set(0.0);
  }

  // Rolls the ball in and over the top
  public void rollSnowPlowIn(){
    snowPlowMotor.set(Relay.Value.kForward);
  }

  // Pushes the ball out
  public void rollSnowPlowOut(){
    snowPlowMotor.set(Relay.Value.kReverse);
  }

  // Turns off the Spike for the snow plow
  public void stopSnowPlow(){
    snowPlowMotor.set(Relay.Value.kOff);
  }

  // Checks if the limit switch at the top of the front elevator is pressed down
  public boolean checkFrontElevatorUp(){
    return frontElevatorTopLimitSwitch.get();
  }

  // Checks if the limit switch at the lower scoring position is pressed down
  public boolean checkFrontElevatorLow(){
    return frontElevatorLowLimitSwitch.get();
  }

  // Checks if the limit switch at the bottom of the elevator is pressed down
  public boolean checkFrontElevatorBottom(){
    return frontElevatorBottomLimitSwitch.get();
  }
}