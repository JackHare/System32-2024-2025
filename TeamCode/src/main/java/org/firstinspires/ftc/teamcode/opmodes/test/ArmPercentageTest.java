package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.tasks.RaiseArmPercentageTask;
import org.firstinspires.ftc.teamcode.utils.TaskOpMode;

@TeleOp(name = "Arm Percentage Test", group = "Test")
public class ArmPercentageTest extends TaskOpMode {

    double arm1Percentage = 0;

    @Override
    public void setUp() {

    }

    @Override
    public void update() {
        taskManager.addTask(new RaiseArmPercentageTask( arm1Percentage, 0.75));
    }

    @Override
    public void repeat()
    {

        if (gamepad1.dpad_up)
            arm1Percentage += .001;
        if (gamepad1.dpad_down)
            arm1Percentage -= .001;

        telemetry.addData("Arm1 target Percentage", arm1Percentage);
        telemetry.addData("Arm1 actual percentage", state.hardware.getArm1HeightPercentage());
        telemetry.addData("Arm1 encoder position", state.hardware.getArm1Position());


        requestUpdate();
    }
}