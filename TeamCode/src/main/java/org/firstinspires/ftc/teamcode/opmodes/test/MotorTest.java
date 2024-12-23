package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MotorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        DcMotor frontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "BackRight");
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");

        while (opModeIsActive())
        {
            if (gamepad1.a)
                frontRight.setPower(.5);
            else
                frontRight.setPower(0);

            if (gamepad1.b)
                frontLeft.setPower(.5);
            else
                frontLeft.setPower(0);

            if (gamepad1.x)
                backRight.setPower(.5);
            else
                backRight.setPower(0);

            if (gamepad1.y)
                backLeft.setPower(.5);
            else
                backLeft.setPower(0);


        }

    }
}
