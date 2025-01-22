package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.RoutineOpMode;

@TeleOp(name = "Drive Motor Test", group = "test")
public class DriveMotorTest extends RoutineOpMode {
    @Override
    protected void initalize() {

    }

    @Override
    protected void repeat() throws Exception {

        telemetry.addData("X", "Front Left Motor");
        telemetry.addData("Y", "Front Right Motor");
        telemetry.addData("A", "Back Left Motor");
        telemetry.addData("B", "Back Right Motor");

        if (gamepad1.x)
            robotState.getHardware().getDrive().setFrontLeftPower(.25);

        if (gamepad1.y)
            robotState.getHardware().getDrive().setFrontRightPower(.25);

        if (gamepad1.a)
            robotState.getHardware().getDrive().setBackLeftPower(.25);

        if (gamepad1.b)
            robotState.getHardware().getDrive().setBackRightPower(.25);

    }
}