package frc.robot;

import javax.swing.text.StyleContext.SmallAttributeSet;

// Import packages needed to run
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GripPipeline;
import frc.robot.subsystems.MegaPeg;
import frc.robot.subsystems.WheelyScoop;
import frc.robot.subsystems.BackClimber;

public class Robot extends TimedRobot {
  // Initialize Subsystems
  public static BackClimber m_backclimber;
  public static MegaPeg m_megapeg;
  public static GripPipeline m_grippipeline;
  public static DriveTrain m_drivetrain;
  public static WheelyScoop m_wheelyscoop;
  public static OI m_oi;

  @Override
  public void robotInit() {
    // Assigns each variable to a subsystem
    m_backclimber = new BackClimber();
    m_megapeg = new MegaPeg();
    m_grippipeline = new GripPipeline();
    m_drivetrain = new DriveTrain();
    m_wheelyscoop = new WheelyScoop();
    m_oi =new OI();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putData("Command", Scheduler.getInstance());
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}