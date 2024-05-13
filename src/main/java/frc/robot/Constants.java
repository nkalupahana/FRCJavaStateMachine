package frc.robot;

import edu.wpi.first.math.util.Units;
import swervelib.math.SwerveMath;

public final class Constants {
    public static final double TEST_DELTA = 0.1;
    public static final double EVALUATION_TIME = 3;
    public static final class Intake {
        public static final double IN_ANGLE = 0;
        public static final double OUT_ANGLE = 160;
        public static final double INTAKE_SPEED = 0.5;
        public static final double GEARING = 67.5;
        public static final double MAX_TIME_TO_SETPOINT = 0.44;
    }

    public static final class Drive {
        public static final int WHEEL_DIAMETER = 4;
        public static final double DRIVE_GEAR_RATIO = 5.357;
        public static final double DRIVE_CONVERSION_FACTOR =
                SwerveMath.calculateMetersPerRotation(
                        Units.inchesToMeters(WHEEL_DIAMETER),
                        DRIVE_GEAR_RATIO,
                        1);

        public static final double ANGLE_GEAR_RATIO = 21.4286;
        public static final double ANGLE_CONVERSION_FACTOR =
                SwerveMath.calculateDegreesPerSteeringRotation(
                    ANGLE_GEAR_RATIO, 
                    1);
        public static final double TARGET_ANGLE = 90d;
        public static final double MAX_TIME_TO_SETPOINT = 0.36;
    }
}
