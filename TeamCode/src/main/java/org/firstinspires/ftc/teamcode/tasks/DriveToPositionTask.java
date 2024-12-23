package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class DriveToPositionTask extends Task
{

    private int distance;
    private double power;
    boolean firstRun = true;

    public DriveToPositionTask(int distance, double power)
    {
        this.distance = -distance;
        this.power = power;
    }

    @Override
    public void run(State state)
    {
        if (firstRun)
        {
            state.hardware.driveDistance(distance, power);
            firstRun = false;
        }
        state.telemetry.addData("Front left", (state.hardware.isFrontLeftAtTargetPosition()));
        if (state.hardware.isFrontLeftAtTargetPosition() && state.hardware.isFrontRightAtTargetPosition() && state.hardware.isBackLeftAtTargetPosition() && state.hardware.isBackRightAtTargetPosition())
        {
            finished = true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Drive To Position Task";
    }
}
