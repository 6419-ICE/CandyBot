package org.firstinspires.ftc.teamcode.command.builtin;

import org.firstinspires.ftc.teamcode.command.Command;

import java.util.function.BooleanSupplier;

public class WaitUntilCommand extends Command {
    private BooleanSupplier condition;
    public WaitUntilCommand(BooleanSupplier condition) {
        this.condition = condition;
    }
    @Override
    public void start() {}
    @Override
    public void loop() {}
    @Override
    public void finish(boolean interrupted) {}
    @Override
    public boolean isFinished() {
        return condition.getAsBoolean();
    }
}
