package org.firstinspires.ftc.teamcode.hardware.arm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

public class Arm2 {
    private final int MAX_POSITION = 1200; //TODO FIND VALUE
    private  final int MIN_POSITION = 100; //TODO FIND VALUE

    private boolean hasArmSafetyBeenRun = false;

    private DcMotor motor;
    private TouchSensor limitSwitch;

    public Arm2(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "Arm2");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.limitSwitch = hardwareMap.get(TouchSensor.class,"Arm2Limit");
    }

    public void setPower(double power) throws Exception {
        if (!hasArmSafetyBeenRun)
            motor.setPower(power);
        else
            throw new Exception("Setting arm power manually after arm safety has been run is not allowed.");
    }

    public void runArmSafety() throws Exception {

        if (getIfArmSafetyHasBeenRun())
            return;

        if (limitSwitch.isPressed()) {
            setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hasArmSafetyBeenRun = true;
        }
        else {
            motor.setPower(-.5);
        }
    }

    public void setPosition(int position) throws Exception {
        if (!hasArmSafetyBeenRun)
            throw new Exception("Setting arm position before arm safety has been run is not allowed.");
        else
        {
            position = Range.clip(position, MIN_POSITION, MAX_POSITION);

            this.motor.setTargetPosition(position);
            this.motor.setPower(1);
            this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void changePosition(int change) throws Exception {
        setPosition(motor.getCurrentPosition() + change);
    }

    public int getPosition() {
        return motor.getCurrentPosition();
    }

    public void setArmPercentage(double percentage) throws Exception {
        percentage = Range.clip(percentage, 0, 1);
        setPosition((int)(percentage * (MAX_POSITION - MIN_POSITION)) + MIN_POSITION);
    }

    public double getArmPercentage() {
        return (double)(motor.getCurrentPosition() - MIN_POSITION) / (MAX_POSITION - MIN_POSITION);
    }

    public void resetArm() throws Exception {
        setPosition(MIN_POSITION);
    }

    public boolean getIfArmSafetyHasBeenRun()
    {
        return hasArmSafetyBeenRun;
    }
}