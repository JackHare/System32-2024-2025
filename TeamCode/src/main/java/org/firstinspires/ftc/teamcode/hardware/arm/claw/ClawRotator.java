package org.firstinspires.ftc.teamcode.hardware.arm.claw;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawRotator {
    private Servo servo;

    public ClawRotator(HardwareMap hardwareMap)
    {
        this.servo = hardwareMap.get(Servo.class, "ClawSpin");
    }

    public double getClawRotatorPosition()
    {
        return servo.getPosition();
    }

    public void setClawRotatorPosition(double position)
    {
        servo.setPosition(position);
    }
}
