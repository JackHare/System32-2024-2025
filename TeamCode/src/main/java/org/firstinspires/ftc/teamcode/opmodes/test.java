package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Routine;
import org.firstinspires.ftc.teamcode.util.RoutineExecutor;
import org.firstinspires.ftc.teamcode.util.RoutineOpMode;

@TeleOp(name="test", group="tests")
public class test extends RoutineOpMode {
    @Override
    protected void initalize() {

    }

    double percent = 0;

    @Override
    protected void repeat() throws Exception {

        telemetry.addData("Arm Encoder", robotState.getHardware().getArm1().getPosition());
        telemetry.addData("Arm Percent", robotState.getHardware().getArm1().getArmPercentage());
        telemetry.addData("Target", percent);
        robotState.getHardware().getArm3().setArmPercentage(percent);

        if (gamepad1.a)
            percent += .01;

    }
}
