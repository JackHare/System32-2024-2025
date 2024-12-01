package org.firstinspires.ftc.teamcode.utils;

import androidx.annotation.NonNull;

public abstract class Task
{
    // If the task has finished running or not
    public boolean finished = false;

    /**
     * Runs a task
     * @param state the state of the robot
     */
    public abstract void run(State state);

    @NonNull
    public String toString()
    {
        return "Generic Task";
    }

}
