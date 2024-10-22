package org.firstinspires.ftc.teamcode.command.builtin;

import org.firstinspires.ftc.teamcode.command.Command;

import java.util.function.BooleanSupplier;

public class ConditionalCommand extends Command {
    protected Command onTrue, onFalse, selected;
    protected BooleanSupplier condition;
    public ConditionalCommand(Command onTrue, Command onFalse, BooleanSupplier condition) {
        this.onTrue = onTrue;
        this.onFalse = onFalse;
        this.condition = condition;
    }
    @Override
    public void init() {
        onTrue.init();
        onFalse.init();
    }
    @Override
    public void start() {
        if (condition.getAsBoolean()) selected = onTrue;
        else selected = onFalse;
        selected.start();
    }

    @Override
    public void loop() {
        selected.loop();
    }

    @Override
    public void finish(boolean interrupted) {
        selected.finish(interrupted);
    }
    @Override
    public void dispose() {
        onTrue.dispose();
        onFalse.dispose();
    }
    @Override
    public boolean isFinished() {
        return selected.isFinished();
    }
}
