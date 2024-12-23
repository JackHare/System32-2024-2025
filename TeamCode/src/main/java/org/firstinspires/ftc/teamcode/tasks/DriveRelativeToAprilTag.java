package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utils.AprilTagInfo;
import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class DriveRelativeToAprilTag extends Task {

    private double power;
    private double targetX;
    private double targetY;
    private double targetZ;

    private boolean spottedAprilTag = false;

    private final double TOLERANCE = 1;

    public DriveRelativeToAprilTag(double power, double targetX, double targetY, double targetZ) {
        this.power = power;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    @Override
    public void run(State state)
    {
        AprilTagInfo aprilTag = state.currentAprilTag;

        if (
                Math.abs(aprilTag.y - targetY) < TOLERANCE &&
                Math.abs(aprilTag.z - targetZ) < TOLERANCE )
        {
            finished = true;
            return;
        }
        if (aprilTag.number == 0)
        {
            if (spottedAprilTag)
            {
                state.telemetry.addData("Lost sight of april tag, ended task", "true");
                state.hardware.setDrive(0,0,0);
                finished = true;
            }
            else
            {
                state.telemetry.addData("Haven't seen apriltag yet!", "true");
            }
            return;
        }

        double xError = targetX - aprilTag.x;
        double yError = targetY - aprilTag.y;

        double xPower = Range.clip(xError * 0.02, -power, power);
        double yPower = Range.clip(yError * 0.02, -power, power);

        if (Math.abs(xError) < TOLERANCE && Math.abs(yError) < TOLERANCE)
        {
            state.telemetry.addData("Reached designated spot", "true");
            state.hardware.setDrive(0,0,0);
            finished = true;
        }


        state.hardware.setDrive(yPower * -1, xPower, 0);
    }

    @NonNull
    @Override
    public String toString() {
        return "Drive Relative To April Tag Task";
    }
}
