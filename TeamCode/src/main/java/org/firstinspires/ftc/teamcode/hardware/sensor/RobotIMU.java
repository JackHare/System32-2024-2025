package org.firstinspires.ftc.teamcode.hardware.sensor;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class RobotIMU {
    private IMU imu;

    public RobotIMU(HardwareMap hardwareMap)
    {
        // Map IMU
        imu = hardwareMap.get(IMU.class, "imu");

        // Set up IMU direction:
        // Logo FORWARD, USB UP
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        // Apply the IMU direction to the IMU
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        // Reset the IMU yaw at the start of each opmode
        imu.resetYaw();
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
}
