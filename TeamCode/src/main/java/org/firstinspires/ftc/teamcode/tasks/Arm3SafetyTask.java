package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class Arm3SafetyTask  extends Task {

    RobotState robotState;

    public Arm3SafetyTask(RobotState robotState) {
        this.robotState = robotState;
    }

    @Override
    public void run() throws Exception {

        robotState.getHardware().getArm3().runArmSafety();
        if (robotState.getHardware().getArm3().getIfArmSafetyHasBeenRun())
            finish();

    }

    @NonNull
    @Override
    public String toString() {
        return "Arm3 Safety Task";
    }
}