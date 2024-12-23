package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.tasks.RaiseArmPercentageTask;
import org.firstinspires.ftc.teamcode.utils.TaskOpMode;

@TeleOp(name = "Arm Encoder Measurer Test", group = "Test")
public class ArmEncoderMeasurer extends TaskOpMode {

    int arm1Percentage = 1;

    @Override
    public void setUp() {

    }

    @Override
    public void update() {
    }

    @Override
    public void repeat()
    {

        if (gamepad1.dpad_up)
            arm1Percentage += 1;
        if (gamepad1.dpad_down)
            arm1Percentage -= 1;

        //  state.hardware.setArm2Position( arm1Percentage, .5);
        state.hardware.setArm1HeightPercentage(.5, 1);
        telemetry.addData("Arm2 target Percentage", arm1Percentage);
        telemetry.addData("Arm2 actual percentage", state.hardware.getArm2HeightPercentage());
        telemetry.addData("Arm2 encoder position", state.hardware.getArm2Position());



        //requestUpdate();
    }
}