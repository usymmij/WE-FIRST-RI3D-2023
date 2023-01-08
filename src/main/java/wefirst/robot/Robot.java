// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package wefirst.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import wefirst.robot.components.DriveBase;
import edu.wpi.first.wpilibj.GenericHID;

public class Robot extends TimedRobot {

  private static final boolean mouseControl = true;

  private Command m_autonomousCommand;
  private GenericHID controller = new GenericHID(0);
  private RobotContainer m_robotContainer;

  private double turning;
  private double accel;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    DriveBase.init(Constants.IDConstants.DRIVEBASE_IDS);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    if(mouseControl) {
      turning = controller.getRawAxis(0);
      accel = controller.getRawButton(6) ? 1 : controller.getRawButton(8) ? -1 : 0; // W, S
      DriveBase.arcade(accel, turning);
      if(accel == 0) {
        DriveBase.rotate((controller.getRawButton(7) ? -1 :0) + (controller.getRawButton(9) ? 1 : 0));
      }
   }

  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
