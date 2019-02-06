package frc.robot.subsystems;

// Import packages needed to run
import frc.robot.commands.setElevatorEncoderValues;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;

public class BackClimber extends Subsystem {
  
  // Initialize Motor Controllers
  Talon backElevatorMotor = new Talon(RobotMap.backElevatorMotor);
  PWMTalonSRX backElevatorDriveMotor = new PWMTalonSRX(RobotMap.backElevatorDriveMotor);

  // Initialize Spikes
  Relay armRelease = new Relay(RobotMap.armReleaseMotor);

  // Initialize Limit Switches
  DigitalInput backElevatorTopLimitSwitch = new DigitalInput(RobotMap.backElevatorTopLimitSwitch);
  DigitalInput backElevatorBottomLimitSwitch = new DigitalInput(RobotMap.backElevatorBottomLimitSwitch);

  // Initialize Encoders
  public Encoder backElevatorDriveEncoder = new Encoder(RobotMap.backElevatorDriveEncoderPortOne, RobotMap.backElevatorDriveEncoderPortTwo, false, Encoder.EncodingType.k4X);

  // Initialize Static Variables 
  double MOTORSPEED = 0.7;
  double WHEELDIAMETER = 2.0;
  double WHEELRADIUS = WHEELDIAMETER/2;
  double REVOLUTIONSTODISTANCE = 3.14 * (WHEELRADIUS*WHEELRADIUS);

  @Override
  public void initDefaultCommand() {
    // Sets the Default Command to set the Encoder Values
    setDefaultCommand(new setElevatorEncoderValues());
  }

  // Pulls the pins out of the Fork Arms to allow them to fall
  public void pullArmPins(){
    armRelease.set(Relay.Value.kForward);
  }

  // Pushes the pins into the Fork Arms to lock them in position
  public void lockArmPins(){
    armRelease.set(Relay.Value.kReverse);
  }
  
  // Turns off the Spike for the Fork Arms
  public void stopArmRelease(){
    armRelease.set(Relay.Value.kOff);
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

  // Converts the encoder count, that is on the back elevator's deployable wheels, to revolutions
  public double getEncoderRevolutions(){
    return backElevatorDriveEncoder.get()/5.68/360;
  }

  // Converts the encoder revolutions, that is on the back elevator's deployable wheels, to distance in inches
  public double getEncoderDistance(){
    return getEncoderRevolutions()*REVOLUTIONSTODISTANCE;
  }

  // Resets the encoder, that is on the back elevator's deployable wheels, to zero
  public void resetEncoder() {
    backElevatorDriveEncoder.reset();
  }
}