package frc.robot.subsystems;

// Import packages needed to run
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Relay;

import java.awt.dnd.Autoscroll;

import com.sun.jdi.request.StepRequest;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class BackClimber extends Subsystem {
  
  // Initialize Motor Controllers
  Talon backElevatorMotor = new Talon(RobotMap.backElevatorMotor);
  PWMTalonSRX backElevatorDriveMotor = new PWMTalonSRX(RobotMap.backElevatorDriveMotor);

  // Initialize Spikes
  Relay armRelease = new Relay(RobotMap.armReleaseMotor);

  // Initialize Limit Switches
  DigitalInput backElevatorTopLimitSwitch = new DigitalInput(RobotMap.backElevatorTopLimitSwitch);
  DigitalInput backElevatorBottomLimitSwitch = new DigitalInput(RobotMap.backElevatorBottomLimitSwitch);

  // Initialize Sonar
  AnalogInput sonar = new AnalogInput(1);

  // Initialize Static Variables 
  double MOTORSPEED = 1.0;
  double WHEELDIAMETER = 2.0;
  double WHEELRADIUS = WHEELDIAMETER/2;
  double REVOLUTIONSTODISTANCE = 3.14 * (WHEELRADIUS*WHEELRADIUS);
  int autoClimbStep = 0;


  @Override
  public void initDefaultCommand() {
  }

  // Lowers the back elevator based on the MOTORSPEED Variable
  public void lowerBackElevator(){
    backElevatorMotor.set(-MOTORSPEED);
  }

  // Raises the back elevator based on the MOTORSPEED Variable
  public void liftBackElevator(){
    backElevatorMotor.set(MOTORSPEED);
  }

  // Turns off the Motor Controller for the back elevator
  public void stopBackElevator(){
    backElevatorMotor.set(0.0);
  }

  // Checks if the limit switch at the top of the back elevator is pressed down
  public boolean checkBackElevatorUp(){
    return backElevatorTopLimitSwitch.get();
  }

  // Checks if the limit switch at the bottom of the back elevator is pressed down
  public boolean checkBackElevatorBottom(){
    return backElevatorBottomLimitSwitch.get();
  }

  // Drives the deployable wheels at a given speed
  public void backElevatorDrive(double speed){
    backElevatorDriveMotor.set(speed);
  }

  public void stopBackElevatorDrive(){
    backElevatorDriveMotor.set(0.0);
  }

  public double getDistanceFromPlatform(){
    return sonar.getAverageVoltage()*(1000/9.8);
  }

  public void changeAutoStep(int step){
    autoClimbStep = step;
  }

  public int getAutoClimbStep(){
    return autoClimbStep;
  }
}