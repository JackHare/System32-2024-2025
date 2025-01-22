package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class OpenPincherTask extends Task
{

    private RobotState robotState;

    public OpenPincherTask(RobotState robotState)
    {
        this.robotState = robotState;
    }

    @Override
    public void run() throws Exception {
        robotState.getHardware().getPincher().setPincherPosition(1);
        finish();
    }

    @NonNull
    @Override
    public String toString() {
        return "Open Pincher Task";
    }
}
