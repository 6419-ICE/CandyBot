package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static final class DriveConstants {
        public static final PIDFCoefficients drivePID = new PIDFCoefficients(
                16,
                0,
                0,
                0
        );
        public static final double wheelConversionFactor = DistanceUnit.INCH.fromMm(96)/2.0;
        public static final double maxVelocity = ((312 * Math.PI * 2.0)/wheelConversionFactor)/60.0;
    }
    public static final class LimelightConstants {
        public static final double kzP = 0.1;
        public static final double kxP = 0.1;
        public static final double kYawP = 0.1;
        public static final double zTolerance = 1;
        public static final double xTolerance = 0.5;
        public static final double yawTolerance = 1;
    }
    public static final class ElevatorConstants {
        public static final double elevatorConversionFactor = DistanceUnit.INCH.fromMm(36.2) * (Math.PI*2.0) / 384.5;
        public static final double kP = 0.1;
        public static final double tolerance = 1.0;
    }
}
