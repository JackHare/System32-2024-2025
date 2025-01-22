package org.firstinspires.ftc.teamcode.hardware.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Camera {

    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    public Camera(HardwareMap hardwareMap)
    {
        // Init AprilTag processor
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();


        // Create the vision portal the easy way.
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
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

}
