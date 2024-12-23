package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class RaiseArmPercentageTask extends Task {

    private double arm1Percentage;
    private double arm2Percentage;

    public RaiseArmPercentageTask(double arm1Percentage, double arm2Percentage) {
        this.arm1Percentage = arm1Percentage;
        this.arm2Percentage = arm2Percentage;
    }

    @Override
    public void run(State state)
    {
        state.hardware.setArm1HeightPercentage(arm1Percentage, 1);
        state.hardware.setArm2HeightPercentage(arm2Percentage, 1);
        if (state.reachedTolerance(state.hardware.getArm1HeightPercentage(), arm1Percentage, 3)
                && state.reachedTolerance(state.hardware.getArm2HeightPercentage(), arm2Percentage, 3))
        {
            finished = true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Raise Arm Percentage Task";
    }
}