package org.firstinspires.ftc.teamcode.command.builtin;

import org.firstinspires.ftc.teamcode.command.Command;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class RunCommandIf extends ConditionalCommand {
    private RunCommandIf tail = null;
    public RunCommandIf(Command onTrue, BooleanSupplier condition) {
        this(onTrue, new EmptyCommand(), condition);
    }
    private RunCommandIf(Command onTrue, Command onFalse, BooleanSupplier condition) {
        super(onTrue,onFalse,condition);
    }
    public RunCommandIf elseIf(Command other, BooleanSupplier condition) {
        if (tail == null) {
            tail = new RunCommandIf(other,condition);
            onFalse = tail;
        } else {
            tail.onFalse = new RunCommandIf(other,condition);
            tail = (RunCommandIf) tail.onFalse;
        }
        return this;
    }
    public Command elseDo(Command other) {
        return elseIf(other,()->true);
    }

}
