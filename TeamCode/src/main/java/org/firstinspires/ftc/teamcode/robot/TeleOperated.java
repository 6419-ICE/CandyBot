package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command.executor.ParallelCommandExecutor;
import org.firstinspires.ftc.teamcode.command.executor.trigger.TriggerGamepad;
import org.firstinspires.ftc.teamcode.robot.command.DriveToAprilTagCommand;
import org.firstinspires.ftc.teamcode.robot.command.MoveElevatorCommand;
import org.firstinspires.ftc.teamcode.robot.subsystem.DriveSubystem;

@TeleOp(name="TeleOp")
public class TeleOperated extends OpMode {

    private DriveSubystem driveSubystem;
    private ParallelCommandExecutor driveToAprilTagExecutor;
    private TriggerGamepad triggerGamepad1;
    @Override
    public void init() {
        driveSubystem = DriveSubystem.getInstance(hardwareMap);
        driveSubystem.printVelocities(telemetry);
        driveToAprilTagExecutor = new ParallelCommandExecutor(hardwareMap,telemetry);
        driveToAprilTagExecutor.init();
        triggerGamepad1 = new TriggerGamepad(gamepad1,driveToAprilTagExecutor);
        triggerGamepad1.triangle.onTrue(new DriveToAprilTagCommand(5,11));
        triggerGamepad1.cross.onTrue(new MoveElevatorCommand(10));
        triggerGamepad1.cross.onFalse(new MoveElevatorCommand(0));
    }

    @Override
    public void loop() {
        driveSubystem.drive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                true
        );
        driveToAprilTagExecutor.execute();
    }
    @Override
    public void stop() {
        driveToAprilTagExecutor.stop();
        driveToAprilTagExecutor.dispose();
    }

}
