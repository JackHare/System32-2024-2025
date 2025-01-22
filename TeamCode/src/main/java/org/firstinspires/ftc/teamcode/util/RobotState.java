package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.sensor.AprilTagInfo;

public class RobotState {

    private State state;

    private Hardware hardware;

    private ElapsedTime runtime = new ElapsedTime();

    private AprilTagInfo currentAprilTag;
    public RobotState(State state, HardwareMap hardwareMap)
    {
        this.state = State.INITALIZATION;
        initialize(hardwareMap);
    }

    public void changeState(State state) {

        this.state = state;

        switch (state) {
            case START:
                start();
                break;

            case RUNNING:
                break;



            default:
        }

    }

    private void start() {

        runtime.reset();

        // Reset claw rotation
      //  getHardware().getClawRotator().setClawRotatorPosition(0);
    }

    private void initialize(HardwareMap hardwareMap) {
        hardware = new Hardware(hardwareMap);
    }

    public Hardware getHardware() {
        return hardware;
    }

    public boolean atTolerance(double realValue, double targetValue, double tolerance)
    {
        return Math.abs(realValue - targetValue) < tolerance;
    }

    public AprilTagInfo getCurrentAprilTag() {
        return new AprilTagInfo(currentAprilTag);
    }

    public void setCurrentAprilTag(AprilTagInfo currentAprilTag) {
        this.currentAprilTag = new AprilTagInfo(currentAprilTag);
    }
}
