/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.TankDriveWithJoystick;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	public int WHEEL_DIAMETER = 5; // In Inches
	public double DEADBAND = 0.20;
	
	public PWMVictorSPX leftMotor = new PWMVictorSPX(RobotMap.leftMotor);
	public PWMVictorSPX rightMotor = new PWMVictorSPX(RobotMap.rightMotor);

	public ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

	public DifferentialDrive robotDrive = new DifferentialDrive(leftMotor, rightMotor);

	public Relay light = new Relay(3);

	public String lightState = "off";

	public Boolean inverse = false;

	public DriveTrain() {
		super();
		System.out.println("Drive");
		
		gyro.reset();

	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveWithJoystick());
	}

	public void log() {
		SmartDashboard.putNumber("POV Aux", Robot.m_oi.getAuxJoystick().getPOV());
		SmartDashboard.putNumber("Left Motor", leftMotor.get());
		SmartDashboard.putNumber("Right Motor", rightMotor.get());
		SmartDashboard.putNumber("Gyro Angle", Robot.m_megapeg.getAngle());
		SmartDashboard.putString("MegaPeg State", Robot.m_megapeg.getMegaPegState());
		SmartDashboard.putNumber("MegaPeg Motor Speed", Robot.m_megapeg.getSpeed());
		SmartDashboard.putNumber("Encoder Count", Robot.m_wheelyscoop.getEncoderRevolutions());
	}

	public void drive(double leftY, double rightY) {

		if (rightY < DEADBAND && rightY > -DEADBAND){
			rightY = 0.0;
		}

		if (leftY < DEADBAND && leftY > -DEADBAND){
			leftY = 0.0;
		}

		if(inverse == true){
			leftY = -leftY;
			rightY = -rightY;
		}

		robotDrive.tankDrive(leftY, rightY);
		
	}

	public void inverseDrive(){
		if(inverse == true){
			inverse = false;
		}
		else if(inverse == false){
			inverse = true;
		}
	}
	
	public void reset() {
		gyro.reset();
	}


	public double getAngle() {
		return gyro.getAngle();
	}

	public double getRate() {
		return gyro.getRate();
	}

	public void invertLeftMotor(boolean invertLeftMotor) {
		leftMotor.setInverted(invertLeftMotor);
	}

	public void invertRightMotor(boolean invertRightMotor) {
		rightMotor.setInverted(invertRightMotor);
	}

	public String getLightState(){
		return lightState;
	}

	public void toggleLight(){
		if(lightState == "off"){
			lightState = "on";
			light.set(Relay.Value.kForward);
		}
		else if(lightState == "on"){
			lightState = "off";
			light.set(Relay.Value.kOff);
		}
	}
}
