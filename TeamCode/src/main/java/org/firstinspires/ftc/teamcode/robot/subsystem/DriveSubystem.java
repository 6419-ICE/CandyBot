package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.command.Subsystem;
import org.firstinspires.ftc.teamcode.robot.Constants;

import org.firstinspires.ftc.teamcode.robot.Constants.DriveConstants;
public class DriveSubystem implements Subsystem {
    private static DriveSubystem instance;
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private IMU imu;
    private DriveSubystem(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class,"frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class,"backLeft");
        backRight = hardwareMap.get(DcMotorEx.class,"backRight");
        frontLeft.setVelocityPIDFCoefficients(
                DriveConstants.drivePID.p,
                DriveConstants.drivePID.i,
                DriveConstants.drivePID.d,
                DriveConstants.drivePID.f
        );
        frontRight.setVelocityPIDFCoefficients(
                DriveConstants.drivePID.p,
                DriveConstants.drivePID.i,
                DriveConstants.drivePID.d,
                DriveConstants.drivePID.f
        );
        backLeft.setVelocityPIDFCoefficients(
                DriveConstants.drivePID.p,
                DriveConstants.drivePID.i,
                DriveConstants.drivePID.d,
                DriveConstants.drivePID.f
        );
        backRight.setVelocityPIDFCoefficients(
                DriveConstants.drivePID.p,
                DriveConstants.drivePID.i,
                DriveConstants.drivePID.d,
                DriveConstants.drivePID.f
        );
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class,"imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        imu.initialize(parameters);
    }
    public void setPowers(double frontLeft, double frontRight, double backLeft, double backRight) {
        this.frontLeft.setPower(frontLeft);
        this.frontRight.setPower(frontRight);
        this.backLeft.setPower(backLeft);
        this.backRight.setPower(backRight);
    }
    public void setVelocities(double frontLeft, double frontRight, double backLeft, double backRight) {
        this.frontLeft.setVelocity(frontLeft/DriveConstants.wheelConversionFactor, AngleUnit.RADIANS);
        this.frontRight.setVelocity(frontRight/DriveConstants.wheelConversionFactor, AngleUnit.RADIANS);
        this.backLeft.setVelocity(backLeft/DriveConstants.wheelConversionFactor, AngleUnit.RADIANS);
        this.backRight.setVelocity(backRight/DriveConstants.wheelConversionFactor, AngleUnit.RADIANS);
    }
    public double getFrontLeftVelocity() {
        return frontLeft.getVelocity(AngleUnit.RADIANS)*DriveConstants.wheelConversionFactor;
    }
    public double getBackLeftVelocity() {
        return backLeft.getVelocity(AngleUnit.RADIANS)*DriveConstants.wheelConversionFactor;
    }
    public double getFrontRightVelocity() {
        return frontRight.getVelocity(AngleUnit.RADIANS)*DriveConstants.wheelConversionFactor;
    }
    public double getBackRightVelocity() {
        return backRight.getVelocity(AngleUnit.RADIANS)*DriveConstants.wheelConversionFactor;
    }
    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
    public void drive(double x, double y, double yaw, boolean fieldRelative) {
        if (fieldRelative) {
            double heading = getHeading();
            driveRelative(
                    y * Math.sin(-heading) + x * Math.cos(-heading),
                    y * Math.cos(-heading) - x * Math.sin(-heading),
                    yaw
            );
        } else {
            driveRelative(
                    x,
                    y,
                    yaw
            );
        }
    }
    public void driveRelative(double x, double y, double yaw) {
        double leftFrontPower  = x + y + yaw;
        double rightFrontPower = x - y - yaw;
        double leftBackPower   = x - y + yaw;
        double rightBackPower  = x + y - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));
        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }
        setVelocities(
                leftFrontPower*DriveConstants.maxVelocity,
                rightFrontPower*DriveConstants.maxVelocity,
                leftBackPower*DriveConstants.maxVelocity,
                rightBackPower*DriveConstants.maxVelocity
        );
    }
    public void driveRaw(double x, double y, double yaw) {
        double leftFrontPower  = x + y + yaw;
        double rightFrontPower = x - y - yaw;
        double leftBackPower   = x - y + yaw;
        double rightBackPower  = x + y - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));
        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }
        setPowers(
                leftFrontPower,
                rightFrontPower,
                leftBackPower,
                rightBackPower
        );
    }
    public void printVelocities(Telemetry telemetry) {
        telemetry.addData("Front Left Velo",this::getFrontLeftVelocity);
        telemetry.addData("Front Right Velo", this::getFrontRightVelocity);
        telemetry.addData("Back Left Velo",this::getBackLeftVelocity);
        telemetry.addData("Back Right Velo", this::getBackRightVelocity);
    }
    public static DriveSubystem getInstance(HardwareMap hardwareMap) {
        return instance == null ? instance = new DriveSubystem(hardwareMap) : instance;
    }
}
