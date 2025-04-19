package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.command.Subsystem;

import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;

import java.util.Arrays;

public class LimelightSubsystem implements Subsystem {
    private static LimelightSubsystem instance;
    private Limelight3A limelight;
    private FiducialResult[] aprilTags = new FiducialResult[4];
    private LimelightSubsystem(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class,"limelight");
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
    }

    public FiducialResult getAprilTagLocation(int id) {
        updateAprilTags();
        return aprilTags[id-11];
    }
    private void updateAprilTags() {
        Arrays.fill(aprilTags,null);
        LLResult result = limelight.getLatestResult();
        for (FiducialResult aprilTag : result.getFiducialResults()) {
            aprilTags[aprilTag.getFiducialId()-11] = aprilTag;
        }
    }
    public void close() {
        limelight.close();
    }
    public static LimelightSubsystem getInstance(HardwareMap hardwareMap) {
        return instance == null ? instance = new LimelightSubsystem(hardwareMap) : instance;
    }
}
