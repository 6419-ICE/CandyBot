package org.firstinspires.ftc.teamcode.command.builtin;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.command.Command;

public class WaitCommand extends Command {
    private ElapsedTime time;
    private double seconds;
    public WaitCommand(double seconds) {
        this.seconds = seconds;
    }
    @Override
    public void start() {
        time = new ElapsedTime();
        time.reset();
    }

    @Override
    public void loop() {}

    @Override
    public void finish(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return time.seconds() >= seconds;
    }
}
