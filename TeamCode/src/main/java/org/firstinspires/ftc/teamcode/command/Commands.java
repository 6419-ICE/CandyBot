package org.firstinspires.ftc.teamcode.command;

import org.firstinspires.ftc.teamcode.command.group.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.command.group.ParallelDeadlineGroup;
import org.firstinspires.ftc.teamcode.command.group.ParallelRaceGroup;
import org.firstinspires.ftc.teamcode.command.group.SequentialCommandGroup;

public final class Commands {
    private Commands(){}

    public static SequentialCommandGroup sequence(Command... commands) {
        return new SequentialCommandGroup(commands);
    }
    public static ParallelCommandGroup parallel(Command... commands) {
        return new ParallelCommandGroup(commands);
    }
    public static ParallelDeadlineGroup deadline(Command deadline, Command... commands) {
        return new ParallelDeadlineGroup(deadline,commands);
    }
    public static ParallelRaceGroup race(Command... commands) {
        return new ParallelRaceGroup(commands);
    }

}
