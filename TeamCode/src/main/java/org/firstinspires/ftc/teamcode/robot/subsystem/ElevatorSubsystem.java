package org.firstinspires.ftc.teamcode.robot.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.command.Subsystem;
import org.firstinspires.ftc.teamcode.robot.Constants;

public class ElevatorSubsystem implements Subsystem {
    private static ElevatorSubsystem instance;
    private DcMotorEx motor;
    private ElevatorSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class,"elevatorMotor");
        motor.setPositionPIDFCoefficients(Constants.ElevatorConstants.kP);
        motor.setTargetPositionTolerance((int) (Constants.ElevatorConstants.tolerance / Constants.ElevatorConstants.elevatorConversionFactor));
    }
    public void setPosition(double position) {
        motor.setTargetPosition((int) (position / Constants.ElevatorConstants.elevatorConversionFactor));
    }
    public double getPosition() {
        return motor.getCurrentPosition() * Constants.ElevatorConstants.elevatorConversionFactor;
    }
    public double getTargetPosition() {
        return motor.getTargetPosition();
    }
    public boolean atGoal() {
        return !motor.isBusy();
    }
    public static ElevatorSubsystem getInstance(HardwareMap hardwareMap) {
        return instance == null ? instance = new ElevatorSubsystem(hardwareMap) : instance;
    }
}
