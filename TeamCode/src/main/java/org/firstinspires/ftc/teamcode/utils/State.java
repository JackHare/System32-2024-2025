package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class State {
    public Telemetry telemetry;
    public Hardware hardware;

    public AprilTagInfo currentAprilTag = new AprilTagInfo(0, 0, 0, 0);

    // DANGEROUS_ARM_SAFETY_OVERRIDE overrides arm automatic safety methods, this flag can cause damage to the robot
    protected boolean DANGEROUS_ARM_SAFETY_OVERRIDE = false;

    private final ElapsedTime runTime = new ElapsedTime();

    /**
     * Constructor for the State class
     * @param telemetry Op mode's telemetry
     */
    public State(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.hardware = new Hardware(hardwareMap, this);
    }

    /**
     * Resets the run time
     */
    public void resetRunTime() {
        runTime.reset();
    }

    /**
     * Returns the run time in seconds
     * @return the run time in seconds
     */
    public double getRunTimeInSeconds() {
        return runTime.seconds();
    }

    /**
     * Returns the run time in milliseconds
     * @return the run time in milliseconds
     */
    public double getRunTimeInMilliseconds() {
        return runTime.milliseconds();
    }
}
