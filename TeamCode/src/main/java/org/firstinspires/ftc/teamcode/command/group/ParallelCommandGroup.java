package org.firstinspires.ftc.teamcode.command.group;

import org.firstinspires.ftc.teamcode.command.Command;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParallelCommandGroup extends Command {
    private Map<Command, Boolean> commandMap = new LinkedHashMap<>();
    public ParallelCommandGroup(Command... commands) {
        addCommands(commands);
    }
    public void addCommands(Command... commands) {
        for (Command c : commands) {
            commandMap.put(c,false);
        }
    }
    @Override
    public void init() {
        for (Map.Entry<Command, Boolean> entry : commandMap.entrySet()) {
            Command.initCommand(entry.getKey(),hardwareMap,telemetry);
        }
    }
    @Override
    public void start() {
        for (Map.Entry<Command, Boolean> entry : commandMap.entrySet()) {
            entry.getKey().start();
            entry.setValue(true);
        }
    }

    @Override
    public void loop() {
        for (Map.Entry<Command, Boolean> entry : commandMap.entrySet()) {
            Command command = entry.getKey();
            if (!entry.getValue()) continue;
            command.loop();
            if (command.isFinished()) {
                entry.setValue(false);
                command.finish(false);
            }
        }
    }

    @Override
    public void finish(boolean interrupted) {
        for (Map.Entry<Command, Boolean> entry : commandMap.entrySet()) {
            if (entry.getValue()) entry.getKey().finish(true);
        }
    }
    @Override
    public void dispose() {
        for (Map.Entry<Command,Boolean> entry : commandMap.entrySet()) {
            entry.getKey().dispose();
        }
    }
    @Override
    public boolean isFinished() {
        return !commandMap.containsValue(true);
    }
}
