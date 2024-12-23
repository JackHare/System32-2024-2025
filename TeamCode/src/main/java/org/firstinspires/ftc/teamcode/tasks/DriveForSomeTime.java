package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

import java.nio.charset.CharacterCodingException;

public class DriveForSomeTime extends Task {

    private double time;
    private double startTime;
    private double power;

    public DriveForSomeTime(State state, double time, double power)
    {
        startTime = state.getRunTimeInMilliseconds();
        this.time = time;
        this.power = power;
    }

    @Override
    public void run(State state)
    {
        if (state.getRunTimeInMilliseconds() - time > startTime)
        {
            finished = true;
            state.hardware.setDrive(0,0,0);
            return;
        }
        state.hardware.setDrive(power,0,0);
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Drive For Some Time Task";
    }
}
