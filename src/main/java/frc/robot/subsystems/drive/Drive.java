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
    PIDController angleController = new PIDController(37, 0, 0.1);

    public Drive() {
        super("Drive", DriveStates.STANDARD);
        
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
    }

    @Override
    protected void runState() {
        drive.drive(
            new Translation2d(0, 0), 
            angleController.calculate(drive.getYaw().getRadians(), Units.degreesToRadians(90)), 
            false,
            false);
    }

    @Override
    protected void runSimulation() {
        // Handled by YAGSL, nothing to do
        return;
    }
    
}
