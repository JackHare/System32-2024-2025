package org.firstinspires.ftc.teamcode.hardware.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class Drive {

    // Constants
    private final double MOTOR_ACCELERATION = 0.1;
    private final double MOTOR_DECELERATION = 0.05;
    private final double MOTOR_NEUTRAL = 0;

    private final double ENCODER_CPR = 28;
    private final double GEAR_RATIO = 20.0;
    private final double WHEEL_DIAMETER = 3;
    private final double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    private final int SET_TO_POSITION_TOLERANCE = 5;

    // Drive motors
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    // Acceleration control for drive motors
    private DcMotorAccelerated frontLeft;
    private DcMotorAccelerated frontRight;
    private DcMotorAccelerated backLeft;
    private DcMotorAccelerated backRight;

    // Tracks the current drive motors target position
    private int frontLeftTargetPosition = 0;
    private int frontRightTargetPosition = 0;
    private int backLeftTargetPosition = 0;
    private int backRightTargetPosition = 0;

    public Drive(HardwareMap hardwareMap)
    {
        this.frontLeftMotor = hardwareMap.get(DcMotor.class, "FrontLeft");
        this.frontRightMotor = hardwareMap.get(DcMotor.class, "FrontRight");
        this.backLeftMotor = hardwareMap.get(DcMotor.class, "BackLeft");
        this.backRightMotor = hardwareMap.get(DcMotor.class, "BackRight");

        // Set up acceleration control for the drive motors
        frontLeft = new DcMotorAccelerated(frontLeftMotor, MOTOR_ACCELERATION, MOTOR_DECELERATION, MOTOR_NEUTRAL);
        frontRight = new DcMotorAccelerated(frontRightMotor, MOTOR_ACCELERATION, MOTOR_DECELERATION, MOTOR_NEUTRAL);
        backLeft = new DcMotorAccelerated(backLeftMotor, MOTOR_ACCELERATION, MOTOR_DECELERATION, MOTOR_NEUTRAL);
        backRight = new DcMotorAccelerated(backRightMotor, MOTOR_ACCELERATION, MOTOR_DECELERATION, MOTOR_NEUTRAL);

        // Set the drive motors to run with RUN_USING_ENCODER
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reverse some motors so that their direction is the same as the other drive motors
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Sets front left power, and updates acceleration control
     * @param power the power to set the motor to
     */
    public void setFrontLeftPower(double power)
    {
        setDriveMotorsToRunWithEncoders();
        frontLeft.setTargetPower(power);
        frontLeft.update();
    }

    /**
     * Sets front right power, and updates acceleration control
     * @param power the power to set the motor to
     */
    public void setFrontRightPower(double power)
    {
        setDriveMotorsToRunWithEncoders();
        frontRight.setTargetPower(power);
        frontRight.update();
    }

    /**
     * Sets back left power, and updates acceleration control
     * @param power the power to set the motor to
     */
    public void setBackLeftPower(double power)
    {
        setDriveMotorsToRunWithEncoders();
        backLeft.setTargetPower(power);
        backLeft.update();
    }

    /**
     * Sets back right power, and updates acceleration control
     * @param power the power to set the motor to
     */
    public void setBackRightPower(double power)
    {
        setDriveMotorsToRunWithEncoders();
        backRight.setTargetPower(power);
        backRight.update();
    }

    /**
     * Set Left and right motor speed
     * @param axial forward-back speed
     * @param lateral left-right speed
     * @param yaw turn speed
     */
    /**
     * Set Left and right motor speed
     * @param axial forward-back speed
     * @param lateral left-right speed
     * @param yaw turn speed
     */
    public void setDrive(double axial, double lateral, double yaw)
    {

        // Flip the lateral power, I don't really know why this has to happen
        lateral *= -1;


        //Calc for wheels
        double frontLeftPower  = axial + lateral - yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral + yaw;

        //Clamp motor powers to -1 and 1
        double[] appliedPowers = scalePowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        //Set motor power
        setFrontLeftPower(appliedPowers[0]);
        setBackLeftPower(appliedPowers[1]);
        setFrontRightPower(appliedPowers[2]);
        setBackRightPower(appliedPowers[3]);
    }


    /**
     * Scales the motor powers to be between -1 and 1
     * @param frontLeftPower front left motor power
     * @param frontRightPower front right motor power
     * @param backLeftPower back left motor power
     * @param backRightPower back right motor power
     * @return scaled motor powers
     */
    private double[] scalePowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower)
    {
        double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));

        if(max > 1) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        return new double[]{frontLeftPower, frontRightPower, backLeftPower, backRightPower};
    }


    /**
     * Sets the frontLeft motor position and power
     * @param position the amount of counts to set the motor
     * @param power the power to set the motor to
     */
    public void setFrontLeftMotorPosition(int position, double power)
    {
        frontLeftTargetPosition = position;
        frontLeftMotor.setTargetPosition(frontLeftTargetPosition);
        frontLeftMotor.setPower(power);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets the frontRight motor position and power
     * @param position the amount of counts to set the motor
     * @param power the power to set the motor to
     */
    public void setFrontRightMotorPosition(int position, double power)
    {
        frontRightTargetPosition = position;
        frontRightMotor.setTargetPosition(frontRightTargetPosition);
        frontRightMotor.setPower(power);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets the backLeft motor position and power
     * @param position the amount of counts to set the motor
     * @param power the power to set the motor to
     */
    public void setBackLeftMotorPosition(int position, double power)
    {
        backLeftTargetPosition = position;
        backLeftMotor.setTargetPosition(backLeftTargetPosition);
        backLeftMotor.setPower(power);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets the backRight motor position and power
     * @param position the amount of counts to set the motor
     * @param power the power to set the motor to
     */
    public void setBackRightMotorPosition(int position, double power)
    {
        backRightTargetPosition = position;
        backRightMotor.setTargetPosition(backRightTargetPosition);
        backRightMotor.setPower(power);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Gets the front left motor position
     * @return front left motor position
     */
    public int getFrontLeftMotorPosition()
    {
        return frontLeftMotor.getCurrentPosition();
    }

    /**
     * Gets the front right motor position
     * @return front right motor position
     */
    public int getFrontRightMotorPosition()
    {
        return frontRightMotor.getCurrentPosition();
    }

    /**
     * Gets the back left motor position
     * @return back left motor position
     */
    public int getBackLeftMotorPosition()
    {
        return backLeftMotor.getCurrentPosition();
    }

    /**
     * Gets the back right motor position
     * @return back right motor position
     */
    public int getBackRightMotorPosition()
    {
        return backRightMotor.getCurrentPosition();
    }

    /**
     * Changes the front left motor's target position and power
     * @param position the amount of counts to change the motor
     * @param power the power to set the motor to
     */
    public void changeFrontLeftMotorPosition(int position, double power)
    {
        setFrontLeftMotorPosition(getFrontLeftMotorPosition() + position, power);
    }

    /**
     * Changes the front right motor's target position and power
     * @param position the amount of counts to change the motor
     * @param power the power to set the motor to
     */
    public void changeFrontRightMotorPosition(int position, double power)
    {
        setFrontRightMotorPosition(getFrontRightMotorPosition() + position, power);
    }

    /**
     * Changes the back left motor's target position and power
     * @param position the amount of counts to change the motor
     * @param power the power to set the motor to
     */
    public void changeBackLeftMotorPosition(int position, double power)
    {
        setBackLeftMotorPosition(getBackLeftMotorPosition() + position, power);
    }

    /**
     * Changes the back right motor's target position and power
     * @param position the amount of counts to change the motor
     * @param power the power to set the motor to
     */
    public void changeBackRightMotorPosition(int position, double power)
    {
        setBackRightMotorPosition(getBackRightMotorPosition() + position, power);
    }

    /**
     * Drives the robot a certain distance in inches
     * @param distance the distance to drive in inches
     * @param power motor power
     */
    public void driveDistance(int distance, double power)
    {
        // https://pastebin.com/raw/pKzpptSj
        double ROTATIONS = distance / CIRCUMFERENCE;
        double counts =  ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        changeFrontLeftMotorPosition((int) Math.round(counts), power);
        changeFrontRightMotorPosition((int) Math.round(counts), power);
        changeBackLeftMotorPosition((int) Math.round(counts), power);
        changeBackRightMotorPosition((int) Math.round(counts), power);
    }

    /**
     * Sets the drive motors to run with encoders
     */
    public void setDriveMotorsToRunWithEncoders()
    {
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Gets if front left motor has reached its target position
     * @return true if front left motor has reached its target position, false otherwise
     */
    public boolean isFrontLeftAtTargetPosition()
    {
        return Math.abs(frontLeftMotor.getCurrentPosition() - frontLeftTargetPosition) <= SET_TO_POSITION_TOLERANCE;
    }

    /**
     * Gets if front right motor has reached its target position
     * @return true if front right motor has reached its target position, false otherwise
     */
    public boolean isFrontRightAtTargetPosition()
    {
        return Math.abs(frontRightMotor.getCurrentPosition() - frontRightTargetPosition) <= SET_TO_POSITION_TOLERANCE;
    }

    /**
     * Gets if back left motor has reached its target position
     * @return true if back left motor has reached its target position, false otherwise
     */
    public boolean isBackLeftAtTargetPosition()
    {
        return Math.abs(backLeftMotor.getCurrentPosition() - backLeftTargetPosition) <= SET_TO_POSITION_TOLERANCE;
    }

    /**
     * Gets if back right motor has reached its target position
     * @return true if back right motor has reached its target position, false otherwise
     */
    public boolean isBackRightAtTargetPosition()
    {
        return Math.abs(backRightMotor.getCurrentPosition() - backRightTargetPosition) <= SET_TO_POSITION_TOLERANCE;
    }

    public double getFrontLeftPower()
    {
        return frontLeftMotor.getPower();
    }

    public double getFrontRightPower()
    {
        return frontRightMotor.getPower();
    }

    public double getBackLeftPower()
    {
        return backLeftMotor.getPower();
    }

    public double getBackRightPower()
    {
        return backRightMotor.getPower();
    }
}
