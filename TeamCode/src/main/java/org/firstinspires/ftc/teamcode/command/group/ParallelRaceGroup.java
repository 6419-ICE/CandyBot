package org.firstinspires.ftc.teamcode.command.group;

import org.firstinspires.ftc.teamcode.command.Command;

import java.util.ArrayList;
import java.util.Arrays;

public class ParallelRaceGroup extends Command{
    private ArrayList<Command> commandList = new ArrayList<>();
    private boolean finished = true;
    public ParallelRaceGroup(Command... commands) {
        addCommands(commands);
    }
    public void addCommands(Command... commands) {
        if (!finished) {
            throw new IllegalStateException("Cannot add commands while group is running");
        }
        commandList.addAll(Arrays.asList(commands));
    }
    @Override
    public void init() {
        for (Command command : commandList) {
            Command.initCommand(command,hardwareMap,telemetry);
        }
    }
    @Override
    public void start() {
        if (!commandList.isEmpty()) {
            finished = false;
            for (Command command : commandList) command.start();
        }
    }

    @Override
    public void loop() {
        for (Command cmd : commandList) {
            cmd.loop();
            if (cmd.isFinished()) finished = true;
        }
    }

    @Override
    public void finish(boolean interrrupted) {
        for (Command c : commandList) c.finish(!c.isFinished());
    }
    @Override
    public void dispose() {
        for (Command command : commandList) command.dispose();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
