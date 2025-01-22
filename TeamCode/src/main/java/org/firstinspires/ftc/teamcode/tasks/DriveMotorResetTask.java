package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class DriveMotorResetTask extends Task {

    private RobotState robotState;

    public DriveMotorResetTask(RobotState robotState)
    {
        this.robotState = robotState;
    }

    @Override
    public void run() throws Exception {

        robotState.getHardware().getDrive().setDrive(0,0,0);
        finish();

    }

    @NonNull
    @Override
    public String toString() {
        return "Drive Motor Reset";
    }
}
