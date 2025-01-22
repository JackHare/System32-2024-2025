package org.firstinspires.ftc.teamcode.hardware.sensor;

public class AprilTagInfo
{
    public int number;
    public double x;
    public double y;
    public double z;

    /**
     * Constructor for AprilTagInfo
     * @param number the number of the tag
     * @param x apriltag's relative x position in inches
     * @param y apriltag's relative y position in inches
     * @param z apriltag's relative z position in inches
     */
    public AprilTagInfo(int number, double x, double y, double z) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AprilTagInfo(AprilTagInfo currentAprilTag) {
        this.number = currentAprilTag.number;
        this.x = currentAprilTag.x;
        this.y = currentAprilTag.y;
        this.z = currentAprilTag.z;
    }
}
