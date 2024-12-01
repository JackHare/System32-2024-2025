package org.firstinspires.ftc.teamcode.utils;

public class AprilTagInfo {
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
    AprilTagInfo(int number, double x, double y, double z) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}