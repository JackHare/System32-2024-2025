package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class Arm1SafetyTask extends Task {
    @Override
    public void run(State state)
    {
        state.hardware.setArm1Power(0.5);
        if(state.hardware.getArm1Limit())
        {
            state.hardware.setArm1Power(0);
            state.hardware.stopAndResetArm1Encoder();
            state.telemetry.addData("Arm 1 Safety turned on", "true");
            finished = true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Arm 1 Safety Task";
    }
}
