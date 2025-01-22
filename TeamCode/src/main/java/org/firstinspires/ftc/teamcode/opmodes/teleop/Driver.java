package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.tasks.SetArmsPercentageTask;
import org.firstinspires.ftc.teamcode.util.Routine;
import org.firstinspires.ftc.teamcode.util.RoutineOpMode;

@TeleOp(name="Driver", group="teleop")
public class Driver extends RoutineOpMode {

    private double arm1Percentage;
    private double arm2Percentage;
    private double arm3Percentage;
    @Override
    protected void initalize() {

    }

    @Override
    protected void repeat() throws Exception
    {
        robotState.getHardware().getDrive().setDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        robotState.getHardware().getClawRotator().setClawRotatorPosition(gamepad1.right_trigger);

        telemetry.addData("Claw rotation", robotState.getHardware().getClawRotator().getClawRotatorPosition());

        if (gamepad1.right_bumper)
            robotState.getHardware().getPincher().setPincherPosition(1);
        else
            robotState.getHardware().getPincher().setPincherPosition(0);

        if (gamepad1.a)
            arm3Percentage += 0.01;

        if (gamepad1.b)
            arm3Percentage -= 0.01;

        if (gamepad1.dpad_up)
            arm1Percentage += 0.01;

        if (gamepad1.dpad_down)
            arm1Percentage -= 0.01;

        if (gamepad1.dpad_right)
            arm2Percentage += 0.01;

        if (gamepad1.dpad_left)
            arm2Percentage -= 0.01;

        arm1Percentage = Range.clip(arm1Percentage, 0, 1);
        arm2Percentage = Range.clip(arm2Percentage, 0, 1);
        arm3Percentage = Range.clip(arm3Percentage, 0, 1);

        executor.addRoutine(Routine.builder().addTask(new SetArmsPercentageTask(robotState, arm1Percentage, arm2Percentage, arm3Percentage)));

    }
}

