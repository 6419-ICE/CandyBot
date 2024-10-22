package org.firstinspires.ftc.teamcode.command.builtin;

import org.firstinspires.ftc.teamcode.command.Command;

public class RepeatCommand extends Command {
    private Command cmd;
    private boolean repeat = false;
    public RepeatCommand(Command command) {
        cmd = command;
    }
    @Override
    public void init() {
        repeat = false;
        Command.initCommand(cmd,hardwareMap,telemetry);
    }
    @Override
    public void start() {
        repeat = false;
        cmd.start();
    }

    @Override
    public void loop() {
        if (repeat) {
            cmd.start();
            repeat = false;
        }
        cmd.loop();
        if (cmd.isFinished()) {
            cmd.finish(false);
            repeat = true;
        }
    }

    @Override
    public void finish(boolean interrupted) {
        if (!repeat) {
            cmd.finish(interrupted);
            repeat = true;
        }
    }
    @Override
    public void dispose() {
        cmd.dispose();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
