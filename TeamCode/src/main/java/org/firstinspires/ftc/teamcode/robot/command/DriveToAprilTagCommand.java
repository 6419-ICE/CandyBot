package org.firstinspires.ftc.teamcode.robot.command;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.command.Command;
import org.firstinspires.ftc.teamcode.robot.Constants;
import org.firstinspires.ftc.teamcode.robot.subsystem.DriveSubystem;
import org.firstinspires.ftc.teamcode.robot.subsystem.LimelightSubsystem;

import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;

public class DriveToAprilTagCommand extends Command {
    private double distance;
    private int id;
    private DriveSubystem driveSubystem;
    private LimelightSubsystem limelightSubsystem;
    private double zError, xError, yawError;
    @SuppressWarnings("unchecked")
    public DriveToAprilTagCommand(double distance, int id) {
        this.distance = distance;
        this.id = id;
        addRequirements(
                DriveSubystem.class,
                LimelightSubsystem.class
        );
    }

    @Override
    public void init() {
        driveSubystem = DriveSubystem.getInstance(hardwareMap);
        limelightSubsystem = LimelightSubsystem.getInstance(hardwareMap);
    }

    @Override
    public void start() {
        xError = Double.MAX_VALUE;
        zError = Double.MAX_VALUE;
        yawError = Double.MAX_VALUE;
    }

    @Override
    public void loop() {
        FiducialResult aprilTag = limelightSubsystem.getAprilTagLocation(id);
        if (aprilTag == null) {
            telemetry.addData("Sees Target",false);
            driveSubystem.driveRelative(0,0,0);
            xError = Double.MAX_VALUE;
            zError = Double.MAX_VALUE;
            yawError = Double.MAX_VALUE;
        } else {
            telemetry.addData("Sees Target",true);
            Pose3D pose = aprilTag.getTargetPoseCameraSpace();
            driveSubystem.driveRelative(
                    (zError = (pose.getPosition().z-distance)) * Constants.LimelightConstants.kzP,
                    (xError = pose.getPosition().x) * Constants.LimelightConstants.kxP,
                    (yawError = pose.getOrientation().getYaw()) * Constants.LimelightConstants.kYawP
            );
        }
    }

    @Override
    public void finish(boolean interrupted) {
        driveSubystem.driveRelative(0,0,0);
    }

    @Override
    public boolean isFinished() {
        return zError <= Constants.LimelightConstants.zTolerance &&
                xError <= Constants.LimelightConstants.xTolerance &&
                yawError <= Constants.LimelightConstants.yawTolerance;
    }
}
