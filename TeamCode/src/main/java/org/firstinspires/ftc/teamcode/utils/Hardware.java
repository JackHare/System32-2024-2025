package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Hardware
{
    // Constants
    private final double MOTOR_ACCELERATION = 0.1;
    private final double MOTOR_DECELERATION = 0.05;
    private final double MOTOR_NEUTRAL = 0;

    private final double ENCODER_CPR = 28;
    private final double GEAR_RATIO = 20.0;
    private final double WHEEL_DIAMETER = 3;
    private final double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    private final int ARM1_MAX = -4500;
    private final int ARM1_MIN = 0;
    private final int ARM2_MAX = 450;
    private final int ARM2_MIN = 0;

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

    // Arm motors
    private DcMotor arm1;
    private DcMotor arm2;

    // Arm mechanical limit sensors
    private TouchSensor arm1Limit;
    private TouchSensor arm2Limit;

    // Control hub's onboard IMU sensor
    private IMU imu;

    // Camera on the back of the robot
    private Limelight3A limelight;

    // Pincher servo for specimens
    public CRServo slurpy;

    // Slurpy intake/outtake servo for low basket
    public Servo grabber;

    // Tracks the current drive motors target position
    private int frontLeftTargetPosition = 0;
    private int frontRightTargetPosition = 0;
    private int backLeftTargetPosition = 0;
    private int backRightTargetPosition = 0;

    // Refrence back to robot state
    private State state;

    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    /**
     * Constructor for the Hardware class
     * Maps hardware to the real word robot components
     * @param hardwareMap the hardware map
     */
    public Hardware(HardwareMap hardwareMap, State state)
    {
        this.state = state;

        // Map arm motors
        arm1 = hardwareMap.get(DcMotor.class, "Arm1");
        arm2 = hardwareMap.get(DcMotor.class, "Arm2");

        // Map arm limit sensors
        arm1Limit = hardwareMap.get(TouchSensor.class, "Arm1 Limit Sensor");
        arm2Limit = hardwareMap.get(TouchSensor.class, "Arm2 Limit Sensor");

        // Map servos
        // TODO fix names in configuration
        slurpy = hardwareMap.get(CRServo.class, "Slurpy");
        grabber = hardwareMap.get(Servo.class, "Grabber");

        // Map drive motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FrontLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FrontRight");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BackLeft");
        backRightMotor = hardwareMap.get(DcMotor.class, "BackRight");

        // Map IMU
        imu = hardwareMap.get(IMU.class, "imu");

        // Set up IMU direction:
        // Logo FORWARD, USB UP
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.FORWARD;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        // Apply the IMU direction to the IMU
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        // Reset the IMU yaw at the start of each opmode
        imu.resetYaw();

        // Map the limelight

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
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set arm motors brake mode
        arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);





        // Init AprilTag processor
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();


        // Create the vision portal the easy way.
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);

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
     * Gets the arm1 limit switch
     * @return true if the limit switch is pressed, false otherwise
     */
    public boolean getArm1Limit()
    {
        return arm1Limit.isPressed();
    }

    /**
     * Gets the arm2 limit switch
     * @return true if the limit switch is pressed, false otherwise
     */
    public boolean getArm2Limit()
    {
        return arm2Limit.isPressed();
    }

    /**
     * Sets the arm motors to run using encoders
     * This turns on DANGER_ARM_SAFETY_OVERRIDE
     */
    public void setArmsToRunUsingEncoder()
    {
        state.DANGEROUS_ARM_SAFETY_OVERRIDE = true;
        arm1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Sets the arm1 power, turns on RUN_USING_ENCODER and DANGER_ARM_SAFETY_OVERRIDE
     * @param power arm power
     */
    public void setArm1Power(double power)
    {
        state.telemetry.addData("WARNING: moving arm without safety on", "true");
        setArmsToRunUsingEncoder();
        arm1.setPower(power);
    }

    /**
     * Sets the arm2 power, turns on RUN_USING_ENCODER and DANGER_ARM_SAFETY_OVERRIDE
     * @param power arm power
     */
    public void setArm2Power(double power)
    {
        state.telemetry.addData("WARNING: moving arm without safety on", "true");
        setArmsToRunUsingEncoder();
        arm2.setPower(power);
    }

    /**
     * Sets the arm1 position and power
     * @param position arm position
     * @param power arm power
     */
    public void setArm1Position(int position, double power)
    {
        if (state.DANGEROUS_ARM_SAFETY_OVERRIDE)
            state.telemetry.addData("WARNING: moving arm without safety on", "true");

        // Clip the arm position between its max and min
        position = Range.clip(position, ARM1_MAX, ARM1_MIN);

        arm1.setTargetPosition(position);
        arm1.setPower(power);
        arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets the arm2 position and power
     * @param position arm position
     * @param power arm power
     */
    public void setArm2Position(int position, double power)
    {
        if (state.DANGEROUS_ARM_SAFETY_OVERRIDE)
            state.telemetry.addData("WARNING: moving arm without safety on", "true");
        else
        {
            // Clip the arm position between its max and min
            position = Range.clip(position, ARM2_MIN, ARM2_MAX);
        }
        arm2.setTargetPosition(position);
        arm2.setPower(power);
        arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Gets the arm1 position
     * @return arm1 position
     */
    public int getArm1Position()
    {
        return arm1.getCurrentPosition();
    }

    /**
     * Gets the arm2 position
     * @return arm2 position
     */
    public int getArm2Position()
    {
        return arm2.getCurrentPosition();
    }

    /**
     * Changes the arm1 position and power
     * @param position amount to change the arm1 position
     * @param power arm power
     */
    public void changeArm1Position(int position, double power)
    {
        setArm1Position(getArm1Position() + position, power);
    }

    /**
     * Changes the arm2 position and power
     * @param position amount to change the arm2 position
     * @param power arm power
     */
    public void changeArm2Position(int position, double power)
    {
        setArm2Position(getArm2Position() + position, power);
    }

    /**
     * Gets the arm1 height percentage
     * @return arm1 height percentage
     */
    public double getArm1HeightPercentage()
    {
        return (double)(getArm1Position() - ARM1_MIN) / (double)(ARM1_MAX - ARM1_MIN);
    }

    /**
     * Gets the arm2 height percentage
     * @return arm2 height percentage
     */
    public double getArm2HeightPercentage()
    {
        return (double)(getArm2Position() - ARM2_MIN) / (double)(ARM2_MAX - ARM2_MIN);
    }

    /**
     * Sets the arm1 height percentage
     * @param percentage arm1 height percentage
     * @param power arm power
     */
    public void setArm1HeightPercentage(double percentage, double power)
    {
        percentage = Range.clip(percentage, 0, 1);
        setArm1Position((int)(percentage * (ARM1_MAX - ARM1_MIN) + ARM1_MIN), power);
    }

    /**
     * Sets the arm2 height percentage
     * @param percentage arm2 height percentage
     * @param power arm power
     */
    public void setArm2HeightPercentage(double percentage, double power)
    {
        percentage = Range.clip(percentage, 0, 1);
        setArm2Position((int)(percentage * (ARM2_MAX - ARM2_MIN) + ARM2_MIN), power);
    }

    /**
     * Stops the arm1 encoder and resets it
     */
    public void stopAndResetArm1Encoder()
    {
        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setArm1Position(0, 0);
    }

    /**
     * Stops the arm2 encoder and resets it
     */
    public void stopAndResetArm2Encoder()
    {
        arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setArm2Position(0, 0);
    }

    /**
     * Gets the yaw in degrees
     * @return yaw in degrees
     */
    public double getYawInDegrees()
    {
        YawPitchRollAngles robotOrientation;
        robotOrientation = imu.getRobotYawPitchRollAngles();
        return robotOrientation.getYaw(AngleUnit.DEGREES);
    }

    /**
     * Poll limelight for first detected april tag
     * @return AprilTagInfo object with the april tag information
     */
    public AprilTagInfo getAprilTagInfo()
    {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        AprilTagInfo aprilTagInfo = new AprilTagInfo(0,0,0,0);

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
             //   telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
               // telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
               // telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                //telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                aprilTagInfo.number = detection.id;
                aprilTagInfo.x = detection.ftcPose.x;
                aprilTagInfo.y = detection.ftcPose.y;
                aprilTagInfo.z = detection.ftcPose.z;

            } else {
                //telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                //telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop
        return aprilTagInfo;
    }

    public int getFrontLeftTargetPosition()
    {
        return frontLeftTargetPosition;
    }

    public int getFrontRightTargetPosition()
    {
        return frontRightTargetPosition;
    }

    /**
     * Gets if front left motor has reached its target position
     * @return true if front left motor has reached its target position, false otherwise
     */
    public boolean isFrontLeftAtTargetPosition()
    {
        state.telemetry.addData("POSITION: ", Math.abs(frontLeftMotor.getCurrentPosition() - frontLeftTargetPosition));
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


}
