package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class LowerArmTask extends Task {
    @Override
    public void run(State state)
    {
        state.hardware.setArm1HeightPercentage(1, .5);
        state.hardware.setArm2HeightPercentage(1, .5);
        if (state.hardware.getArm1HeightPercentage() < 5 && state.hardware.getArm2HeightPercentage() < 5)
            finished = true;
    }

    @NonNull
    @Override
    public String toString() {
        return "Lower Arm Task";
    }
}
