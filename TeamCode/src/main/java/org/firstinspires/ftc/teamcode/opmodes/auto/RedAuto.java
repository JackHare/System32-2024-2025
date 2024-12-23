package org.firstinspires.ftc.teamcode.opmodes.auto;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.tasks.CloseGrabberTask;
import org.firstinspires.ftc.teamcode.tasks.DriveMotorReset;
import org.firstinspires.ftc.teamcode.tasks.DriveRelativeToAprilTag;
import org.firstinspires.ftc.teamcode.tasks.DriveToPositionTask;
import org.firstinspires.ftc.teamcode.tasks.IdleTask;
import org.firstinspires.ftc.teamcode.tasks.LowerArmTask;
import org.firstinspires.ftc.teamcode.tasks.OpenGrabberTask;
import org.firstinspires.ftc.teamcode.tasks.RaiseArmPercentageTask;
import org.firstinspires.ftc.teamcode.tasks.TurnDegreesTask;
import org.firstinspires.ftc.teamcode.utils.Alliance;
import org.firstinspires.ftc.teamcode.utils.TaskOpMode;

@Autonomous(name="Autonomous (RED)", group = "auto", preselectTeleOp = "Driver")
public class RedAuto extends TaskOpMode {

    Alliance alliance;

    @Override
    public void setUp()
    {
        // Set the alliance to red alliance
        alliance = Alliance.RED;
        state.DANGEROUS_ARM_SAFETY_OVERRIDE = true;
    }

    @Override
    public void update() {

        if (updateCount == 0) {
            taskManager.addTask(new DriveToPositionTask( 15, -1));
            taskManager.addTask(new DriveMotorReset());
            taskManager.addTask(new DriveRelativeToAprilTag(1, 0, 26, 0));
            taskManager.addTask(new DriveMotorReset());
            taskManager.addTask(new RaiseArmPercentageTask(1 - .25, .9));
            taskManager.addTask(new DriveRelativeToAprilTag(1, 0, 30, 0));
            taskManager.addTask(new DriveMotorReset());
            taskManager.addTask(new OpenGrabberTask());
            taskManager.addTask(new RaiseArmPercentageTask(.94 - .25, .9));


        }




    }

    @Override
    public void repeat()
    {
        if (taskManager.isEmpty())
            state.hardware.setDrive(0,0,0);
        telemetry.addData("Heading", state.hardware.getYawInDegrees());
    }
}
