package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class Arm2SafetyTask extends Task {

    RobotState robotState;

    public Arm2SafetyTask(RobotState robotState) {
        this.robotState = robotState;
    }

    @Override
    public void run() throws Exception {

        robotState.getHardware().getArm2().runArmSafety();
        if (robotState.getHardware().getArm2().getIfArmSafetyHasBeenRun())
            finish();

    }

    @NonNull
    @Override
    public String toString() {
        return "Arm2 Safety Task";
    }
}
