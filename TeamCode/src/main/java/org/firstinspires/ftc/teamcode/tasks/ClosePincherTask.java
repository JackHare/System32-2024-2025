package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class ClosePincherTask extends Task
{

    private RobotState robotState;

    public ClosePincherTask(RobotState robotState)
    {
        this.robotState = robotState;
    }

    @Override
    public void run() throws Exception {
        robotState.getHardware().getPincher().setPincherPosition(0);
        finish();
    }

    @NonNull
    @Override
    public String toString() {
        return "Close Pincher Task";
    }
}
