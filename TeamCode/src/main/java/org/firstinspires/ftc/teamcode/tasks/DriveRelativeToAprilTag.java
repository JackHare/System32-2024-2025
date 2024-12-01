package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.AprilTagInfo;
import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class DriveRelativeToAprilTag extends Task {

    private double power;
    private double targetX;
    private double targetY;
    private double targetZ;

    private final double TOLERANCE = 1;

    DriveRelativeToAprilTag(double power, double targetX, double targetY, double targetZ) {
        this.power = power;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    @Override
    public void run(State state)
    {
        AprilTagInfo aprilTag = state.currentAprilTag;

        if (Math.abs(aprilTag.x - targetX) < TOLERANCE &&
                Math.abs(aprilTag.y - targetY) < TOLERANCE &&
                Math.abs(aprilTag.z - targetZ) < TOLERANCE )
        {
            finished = true;
            return;
        }
        if (aprilTag.number == 0)
        {
            state.telemetry.addData("No April Tag Found", "true");
            finished = true;
            return;
        }

        double xError = targetX - aprilTag.x;
        double yError = targetY - aprilTag.y;
        double zError = targetZ - aprilTag.z;

        double xPower = xError * power;
        double yPower = yError * power;
        double zPower = zError * power;

        state.hardware.setDrive(xPower, yPower, 0);
    }

    @NonNull
    @Override
    public String toString() {
        return "Drive Relative To April Tag Task";
    }
}
