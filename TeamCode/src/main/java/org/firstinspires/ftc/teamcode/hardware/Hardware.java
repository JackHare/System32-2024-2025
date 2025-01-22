package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.arm.Arm1;
import org.firstinspires.ftc.teamcode.hardware.arm.Arm2;
import org.firstinspires.ftc.teamcode.hardware.arm.Arm3;
import org.firstinspires.ftc.teamcode.hardware.arm.claw.ClawRotator;
import org.firstinspires.ftc.teamcode.hardware.arm.claw.Pincher;
import org.firstinspires.ftc.teamcode.hardware.drive.Drive;
import org.firstinspires.ftc.teamcode.hardware.sensor.Camera;
import org.firstinspires.ftc.teamcode.hardware.sensor.RobotIMU;

public class Hardware {

    private Arm1 arm1;
    private Arm2 arm2;
    private Arm3 arm3;
    private Drive drive;

    private Pincher pincher;
    private ClawRotator clawRotator;

    private RobotIMU robotIMU;

    private Camera camera;

    public Hardware(HardwareMap hardwareMap) {

        arm1 = new Arm1(hardwareMap);
        arm2 = new Arm2(hardwareMap);
        arm3 = new Arm3(hardwareMap);

        drive = new Drive(hardwareMap);

        pincher = new Pincher(hardwareMap);
        clawRotator = new ClawRotator(hardwareMap);

        robotIMU = new RobotIMU(hardwareMap);

        camera = new Camera(hardwareMap);
    }

    public Arm1 getArm1() {
        return arm1;
    }

    public Arm2 getArm2() {
        return arm2;
    }

    public Arm3 getArm3() {
        return arm3;
    }

    public Drive getDrive()
    {
        return drive;
    }

    public Pincher getPincher()
    {
        return pincher;
    }

    public ClawRotator getClawRotator()
    {
        return clawRotator;
    }

    public RobotIMU getIMU()
    {
        return robotIMU;
    }

    public Camera getCamera()
    {
        return camera;
    }
}
