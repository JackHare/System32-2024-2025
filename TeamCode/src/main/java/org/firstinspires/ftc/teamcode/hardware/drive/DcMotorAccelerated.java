package org.firstinspires.ftc.teamcode.hardware.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DcMotorAccelerated {
    private static final double MAX_MOTOR_POWER = 1;
    private static final double MIN_MOTOR_POWER = -1;

    private final DcMotor acceleratedMotor;
    private double acceleRate;
    private double currentPower;
    private double deceleRate;
    private double targetPower;
    private double neutral;

    public DcMotorAccelerated(DcMotor aMotor, double accel, double decel, double aNeutral) {
        acceleratedMotor = aMotor;
        setAccelerationRates (accel, decel);
        setNeutral(aNeutral);
    }

    public void setAccelerationRates(double anAccelerationRateMotor, double aDecelerationRateMotor) {
        acceleRate = anAccelerationRateMotor;
        deceleRate = aDecelerationRateMotor;
    }

    public double getCurrentPower() {
        return acceleratedMotor.getPower();
    }

    public double getTargetPower() {
        return targetPower;
    }

    public void setNeutral(double aNeutral) {
        neutral = aNeutral;
    }

    public void setTargetPower(double aTargetPower) {
        targetPower = aTargetPower;
    }

    public void stopMotorHard() {
        acceleratedMotor.setPower(0.0);
    }

    public void update() {
        if(currentPower > neutral) {
            //Positive direction
            //Accelerating.
            if(currentPower < targetPower) {
                currentPower += acceleRate;
                currentPower = Math.min(currentPower, targetPower);
            }
            //Decelerating.
            else if(currentPower > targetPower) {
                currentPower -= deceleRate;
                currentPower = Math.max(currentPower, neutral);
            }
            else {
                //Do nothing.
            }
        }
        else if(currentPower < neutral){
            //Negative direction
            //Decelerating.
            if(currentPower < targetPower) {
                currentPower += deceleRate;
                currentPower = Math.min(currentPower, neutral);
            }
            //Accelerating.
            else if(currentPower > targetPower) {
                currentPower -= acceleRate;
                currentPower = Math.max(currentPower, targetPower);
            }
            else {
                //Do nothing.
            }
        }
        else {
            //Minimum power
            //Start moving in the positive direction.
            if(targetPower > neutral) {
                currentPower += acceleRate;
            }
            //Start moving in the negative direction.
            else if(targetPower < neutral) {
                currentPower -= acceleRate;
            }
            else {
                //Don't move.
            }
        }
        currentPower = Math.min(currentPower, MAX_MOTOR_POWER);
        currentPower = Math.max(currentPower, MIN_MOTOR_POWER);
        acceleratedMotor.setPower(currentPower);
    }
}