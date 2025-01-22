package org.firstinspires.ftc.teamcode.hardware.arm.claw;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pincher {
    private Servo servo;

    public Pincher(HardwareMap hardwareMap)
    {
        servo = hardwareMap.get(Servo.class, "ClawPincher");
    }

    public double getPincherPosition()
    {
        return servo.getPosition();
    }

    public void setPincherPosition(double position)
    {
        servo.setPosition(position);
    }

}
