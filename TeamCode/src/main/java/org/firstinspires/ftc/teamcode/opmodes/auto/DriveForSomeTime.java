package org.firstinspires.ftc.teamcode.opmodes.auto;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class DriveForSomeTime extends Task {

    private double startTime;
    private double duration;
    private double power;

    public DriveForSomeTime(State state, int duration, int power)
    {
        this.duration = duration;
        this.power = power;
        this.startTime = state.getRunTimeInMilliseconds();
    }

    @Override
    public void run(State state)
    {
        state.hardware.setDrive(power, 0,0);
        if (state.getRunTimeInMilliseconds() - startTime > duration)
        {
            state.hardware.setDrive(0,0,0);
            finished = true;
        }
    }
}
