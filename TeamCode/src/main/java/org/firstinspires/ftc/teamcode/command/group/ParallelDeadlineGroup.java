package org.firstinspires.ftc.teamcode.command.group;

import org.firstinspires.ftc.teamcode.command.Command;

public class ParallelDeadlineGroup extends ParallelCommandGroup {
    private Command deadline;
    public ParallelDeadlineGroup(Command deadline, Command... commands) {
        super(commands);
        addCommands(deadline);
        this.deadline = deadline;
    }

    @Override
    public boolean isFinished() {
        return deadline.isFinished();
    }
}
