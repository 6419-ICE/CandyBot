package org.firstinspires.ftc.teamcode.command;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.command.group.SequentialCommandGroup;

public class SequentialAuto extends OpMode {
    private SequentialCommandGroup group;
    public SequentialAuto(Command... commands) {
        group = new SequentialCommandGroup(commands);
    }
    public void addCommands(Command... commands) {
        group.addCommands(commands);
    }
    @Override
    public void init() {
        group.hardwareMap = hardwareMap;
        group.telemetry = telemetry;
        group.init();
    }
    @Override
    public void start() {
        group.start();
    }
    @Override
    public void loop() {
        if (group.isFinished()) {
            group.finish(false);
            requestOpModeStop();
        } else {
            group.loop();
        }
    }
    @Override
    public void stop() {
        if (!group.isFinished()) group.finish(true);
        group.dispose();
    }
}
