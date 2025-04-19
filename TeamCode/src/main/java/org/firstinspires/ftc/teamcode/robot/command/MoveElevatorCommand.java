package org.firstinspires.ftc.teamcode.robot.command;

import org.firstinspires.ftc.teamcode.command.Command;
import org.firstinspires.ftc.teamcode.robot.subsystem.ElevatorSubsystem;

public class MoveElevatorCommand extends Command {
    private double position;
    private ElevatorSubsystem elevatorSubsystem;
    public MoveElevatorCommand(double position) {
        this.position = position;
    }

    @Override
    public void init() {
        elevatorSubsystem = ElevatorSubsystem.getInstance(hardwareMap);
    }
    @Override
    public void start() {
        elevatorSubsystem.setPosition(position);
    }

    @Override
    public void loop() {

    }

    @Override
    public void finish(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return elevatorSubsystem.atGoal();
    }
}
