package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.tasks.OpenGrabberTask;
import org.firstinspires.ftc.teamcode.tasks.RaiseArmPercentageTask;
import org.firstinspires.ftc.teamcode.utils.TaskOpMode;

@Autonomous(name = "Autonomous arm set up", group = "setup")
public class AutoArmSetup extends TaskOpMode{
    @Override
    public void setUp() {

    }

    @Override
    public void update() {
        taskManager.addTask(new RaiseArmPercentageTask(.25, 0));
    }

    @Override
    public void repeat() {

        if (taskManager.isEmpty())
            requestUpdate();

    }
}
