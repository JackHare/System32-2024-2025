package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.sensor.AprilTagInfo;
import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class DriveRelativeToAprilTagTask extends Task
{

    private double power;
    private double targetX;
    private double targetY;
    private double targetZ;

    private boolean spottedAprilTag = false;

    private final double TOLERANCE = 1;

    private RobotState robotState;

    public DriveRelativeToAprilTagTask(RobotState robotState, double power, double targetX, double targetY, double targetZ) {
        this.power = power;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.robotState = robotState;
    }

    @Override
    public void run()
    {
        AprilTagInfo aprilTag = robotState.getCurrentAprilTag();
        if(aprilTag.x == 0 && aprilTag.y == 0 && aprilTag.y == 0 && aprilTag.number == 0) {
            robotState.getHardware().getDrive().setDrive(0,0,0);
            finish();
        }
        if (
                Math.abs(aprilTag.y - targetY) < TOLERANCE &&
                        Math.abs(aprilTag.z - targetZ) < TOLERANCE )
        {
            robotState.getHardware().getDrive().setDrive(0,0,0);

            finish();
            return;
        }
        if (aprilTag.number == 0)
        {
            if (spottedAprilTag)
            {
                robotState.getHardware().getDrive().setDrive(0,0,0);
                finish();
            }
            return;
        }

        double xError = targetX - aprilTag.x;
        double yError = targetY - aprilTag.y;

        double xPower = Range.clip(xError * 0.05, -power, power);
        double yPower = Range.clip(yError * 0.05, -power, power);

        if (Math.abs(xError) < TOLERANCE && Math.abs(yError) < TOLERANCE)
        {
            robotState.getHardware().getDrive().setDrive(0,0,0);
            finish();
        }


        robotState.getHardware().getDrive().setDrive(yPower * -1, xPower, 0);
    }


    @NonNull
    @Override
    public String toString() {
        return "Drive Relative To April Tag Task";
    }
}
