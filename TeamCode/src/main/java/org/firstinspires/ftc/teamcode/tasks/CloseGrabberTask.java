package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class CloseGrabberTask extends Task {
    @Override
    public void run(State state) {
        state.hardware.grabber.setPosition(0);
        finished = true;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Close Grabber Task";
    }
}
