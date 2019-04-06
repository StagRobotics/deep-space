package frc.robot.subsystems;

// Import packages needed to run
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.commands.*;

public class MegaPeg extends Subsystem {
  
  // Initialize Static Variables
  public double DEADBAND = 0.20;

  // Initialize Motor Controllers
  public SpeedController megaPegMotor = new Spark(RobotMap.megaPegMotor);

  DigitalInput topLimitSwitch = new DigitalInput(RobotMap.megaPegTopLimitSwitch);
  DigitalInput bottomLimitSwitch = new DigitalInput(RobotMap.megaPegBottomLimitSwitch);

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

    // Prevents manually Megapeg controls from overriding the bottom limit switch
    if(bottomLimitSwitch.get() == false && speed > 0.0){
      speed = 0.0;
    }

    // Sets MegaPeg Motor to the given Speeds
    megaPegMotor.set(speed);
  }

  // Moves Mega Peg Up at 75% Speed
  public void moveMegaPegUpLimitSwitch(){
    megaPegMotor.set(-0.75);
  }

  // Move Mega Peg Down at 30% Speed
  public void moveMegaPegDownLimitSwitch(){
    megaPegMotor.set(0.30);
  }

  // Returns the value of the top limit switch
  public boolean getTopLimitSwitch(){
    return topLimitSwitch.get();
  }

  // Returns the value of the bottom limit switch
  public boolean getBottomLimitSwitch(){
    return bottomLimitSwitch.get();
  }
}