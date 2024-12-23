package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.tasks.RaiseArmToLowBasket;
import org.firstinspires.ftc.teamcode.utils.TaskOpMode;

@TeleOp(name = "Driver", group = "TeleOp")
public class Driver extends TaskOpMode {

    private boolean pincherIsOpen = false;

    @Override
    public void setUp() {

    }

    @Override
    public void update() {

    }

    @Override
    public void repeat() {
        state.hardware.setDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        state.hardware.slurpy.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

        if (gamepad1.left_bumper)
        {
            if (pincherIsOpen)
                state.hardware.grabber.setPosition(.5);
            else
                state.hardware.grabber.setPosition(0);
            pincherIsOpen = !pincherIsOpen;
        }

        if (gamepad1.dpad_up)
            state.hardware.changeArm1Position(-100, 1);
        if (gamepad1.dpad_down)
            state.hardware.changeArm1Position(100, 1);

        if (gamepad1.dpad_right)
            state.hardware.changeArm2Position(75, 1);
        if (gamepad1.dpad_left)
            state.hardware.changeArm2Position(-75, 1);

     //   if (gamepad1.b) {
     //       taskManager.addTask(new LowerArmTask());

        //}

        if (gamepad1.a)
        {
            taskManager.addTask(new RaiseArmToLowBasket());
        }
    }
}
