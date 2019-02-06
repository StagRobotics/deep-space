package frc.robot.subsystems;

// Import packages needed to run
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.commands.*;

public class MegaPeg extends Subsystem {
  
  // Initialize Static Variables
  public double DEADBAND = 0.20;

  // Initialize Motor Controllers
  public SpeedController megaPegMotor = new Spark(RobotMap.megaPegMotor);

  @Override
  public void initDefaultCommand() {
    // Sets the default command to move MegaPeg using Joystick values
    setDefaultCommand(new moveMegaPegWithJoystick());
  }

  // Turns off the MegaPeg Motor
  public void stopMegaPeg(){
    megaPegMotor.set(0);
  }

  // Controls the MegaPeg Motor with a given speed
  public void moveMegaPegWithJoystick(double speed){
    // Prevents motor movement if the input is between the DEADBAND and the negative of the DEADBAND
    if (speed < DEADBAND && speed > -DEADBAND){
			speed = 0.0;
		}

    // Sets the MegaPeg Motor to the given speed
    megaPegMotor.set(speed);
  }
}