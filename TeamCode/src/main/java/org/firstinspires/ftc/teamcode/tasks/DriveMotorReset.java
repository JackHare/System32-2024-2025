package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class DriveMotorReset extends Task {
    @Override
    public void run(State state) {

        state.hardware.setDrive(0,0,0);
        finished = true;

    }

    @NonNull
    public String toString()
    {
        return "Drive Motor Reset Task";
    }
}
