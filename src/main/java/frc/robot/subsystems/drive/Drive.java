package frc.robot.subsystems.drive;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Constants;
import frc.robot.subsystems.Subsystem;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

public class Drive extends Subsystem<DriveStates> {
    SwerveDrive drive;
    // TODO: Tune PID values
    // Other than optional logging,
    // THIS IS ALL YOU SHOULD MODIFY
    PIDController angleController = new PIDController(0, 0, 0);

    public Drive() {
        super("Drive", DriveStates.OFF);
        
        try {
            drive = new SwerveParser(new File(Filesystem.getDeployDirectory(), "neokraken"))
                .createSwerveDrive(
                    Units.feetToMeters(14.5), 
                    Constants.Drive.ANGLE_CONVERSION_FACTOR, 
                    Constants.Drive.DRIVE_CONVERSION_FACTOR);
        } catch (IOException e) {
            System.err.println("Swerve module data could not be processed!");
            System.err.println(e);
        }

        /// Configure triggers for state transitions in testing
        addTrigger(DriveStates.OFF, DriveStates.STANDARD, () -> true);
        // Drive -> Evaluate/Reject
        addTrigger(DriveStates.STANDARD, DriveStates.EVALUATE, () -> {
            return nearSetpoint() && getStateTime() < Constants.Drive.MAX_TIME_TO_SETPOINT;
        });
        addTrigger(DriveStates.STANDARD, DriveStates.REJECT, () -> {
            if (getStateTime() > Constants.Drive.MAX_TIME_TO_SETPOINT) {
                System.out.println("Drive reject: took too long to reach setpoint.");
                return true;
            };

            return false;
        });

        // Evalute -> Accept/Reject
        addTrigger(DriveStates.EVALUATE, DriveStates.ACCEPT, () -> getStateTime() > Constants.EVALUATION_TIME);
        addTrigger(DriveStates.EVALUATE, DriveStates.REJECT, () -> {
            if (!nearSetpoint()) {
                System.out.println("Drive reject: Left setpoint during evaluation.");
                return true;
            }
            return false;
        });
    }

    public boolean nearSetpoint() {
        return Math.abs(Constants.Drive.TARGET_ANGLE - drive.getYaw().getDegrees()) < Constants.TEST_DELTA;
    }

    @Override
    protected void runState() {
        drive.drive(
            new Translation2d(0, 0), 
            angleController.calculate(
                drive.getYaw().getRadians(), 
                Units.degreesToRadians(Constants.Drive.TARGET_ANGLE)), 
            false,
            false);
    }

    @Override
    protected void runSimulation() {
        // Handled by YAGSL, nothing to do
        return;
    }
    
}
