package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.tasks.DriveRelativeToAprilTag;
import org.firstinspires.ftc.teamcode.tasks.DriveToPositionTask;
import org.firstinspires.ftc.teamcode.utils.TaskOpMode;

@TeleOp(name = "Drive Inch Test", group = "Test")
public class DriveInchesTest extends TaskOpMode {
    @Override
    public void setUp()
    {

    }

    @Override
    public void update()
    {

       // taskManager.addTask(new DriveToPositionTask(10, 0.5));
    //    taskManager.addTask(new DriveRelativeToAprilTag(0.25, 5, 30, 0));
        if (updateCount == 0)
            taskManager.addTask(new DriveToPositionTask(5, 0.05));
    }

    @Override
    public void repeat()
    {
        state.telemetry.addData("Front Left current position", state.hardware.getFrontLeftMotorPosition());
        state.telemetry.addData("Front Left target position", state.hardware.getFrontLeftTargetPosition());

    }
}