package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class IdleTask extends Task {

    private double startTime;
    private double duration;

    public IdleTask(State state, double duration)
    {
        this.duration = duration;
        this.startTime = state.getRunTimeInMilliseconds();
    }

    @Override
    public void run(State state) {

        if (state.getRunTimeInMilliseconds() - startTime > duration)
        {
            finished = true;
        }

    }

    @NonNull
    @Override
    public String toString()
    {
        return "Idle Task";
    }
}
