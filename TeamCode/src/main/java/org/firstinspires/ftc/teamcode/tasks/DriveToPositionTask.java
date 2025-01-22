package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class DriveToPositionTask extends Task {

    private int distance;
    private double power;
    private boolean firstRun = true;

    private RobotState robotState;

    public DriveToPositionTask(RobotState robotState, int distance, double power)
    {
        this.robotState = robotState;
        this.distance = -distance;
        this.power = power;
    }

    @Override
    public void run()
    {
        if (firstRun)
        {
            robotState.getHardware().getDrive().driveDistance(distance, power);
            firstRun = false;
        }
        if (robotState.getHardware().getDrive().isFrontLeftAtTargetPosition() && robotState.getHardware().getDrive().isFrontRightAtTargetPosition() && robotState.getHardware().getDrive().isBackLeftAtTargetPosition() && robotState.getHardware().getDrive().isBackRightAtTargetPosition()) {
            robotState.getHardware().getDrive().setDrive(0,0,0);
            finish();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Drive To Position Task";
    }
}
