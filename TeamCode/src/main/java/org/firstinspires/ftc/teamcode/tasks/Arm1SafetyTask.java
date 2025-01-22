package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class Arm1SafetyTask extends Task {

    RobotState robotState;

    public Arm1SafetyTask(RobotState robotState) {
        this.robotState = robotState;
    }

    @Override
    public void run() throws Exception {

        robotState.getHardware().getArm1().runArmSafety();
        if (robotState.getHardware().getArm1().getIfArmSafetyHasBeenRun())
            finish();

    }

    @NonNull
    @Override
    public String toString() {
        return "Arm1 Safety Task";
    }
}
