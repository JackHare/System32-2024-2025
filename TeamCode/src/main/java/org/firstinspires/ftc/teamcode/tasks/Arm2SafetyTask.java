package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class Arm2SafetyTask extends Task {
    @Override
    public void run(State state)
    {
        state.hardware.setArm2Power(-0.5);
        if(state.hardware.getArm2Limit())
        {
            state.hardware.setArm2Power(0);
            state.hardware.stopAndResetArm2Encoder();
            state.telemetry.addData("Arm 2 Safety turned on", "true");
            finished = true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Arm 2 Safety Task";
    }
}
