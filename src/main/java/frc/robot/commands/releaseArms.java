/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class releaseArms extends CommandGroup {
  
  public releaseArms() {

    requires(Robot.m_backclimber);
    
    // These two commands will run together
    addParallel(new pullArmPins());
    addSequential(new wait(1));
    // These two commands will run together
    addParallel(new lockArmPins());
    addSequential(new wait(1));
    addSequential(new stopArmMotor());
  }
}
