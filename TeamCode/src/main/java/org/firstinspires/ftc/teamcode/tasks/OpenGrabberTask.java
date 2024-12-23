package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class OpenGrabberTask extends Task {

    @Override
    public void run(State state) {
        state.hardware.grabber.setPosition(.5);
        finished = true;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Open Grabber Task";
    }
}
