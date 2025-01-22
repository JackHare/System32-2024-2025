package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.RobotState;
import org.firstinspires.ftc.teamcode.util.Task;

public class SetArmsPercentageTask extends Task {

    private RobotState robotState;

    private double arm1Percentage;
    private double arm2Percentage;
    private double arm3Percentage;

    public SetArmsPercentageTask(RobotState robotState, double arm1Percentage, double arm2Percentage, double arm3Percentage) {
        this.robotState = robotState;
        this.arm1Percentage = arm1Percentage;
        this.arm2Percentage = arm2Percentage;
        this.arm3Percentage = arm3Percentage;
    }

    @Override
    public void run() throws Exception {

        robotState.getHardware().getArm1().setArmPercentage(arm1Percentage);
        robotState.getHardware().getArm2().setArmPercentage(arm2Percentage);
        robotState.getHardware().getArm3().setArmPercentage(arm3Percentage);

        if (robotState.atTolerance(robotState.getHardware().getArm1().getArmPercentage(), arm1Percentage, 0.01) && robotState.atTolerance(robotState.getHardware().getArm2().getArmPercentage(), arm2Percentage, 0.01) && robotState.atTolerance(robotState.getHardware().getArm3().getArmPercentage(), arm3Percentage, 0.01))
          finish();

    }

    @NonNull
    @Override
    public String toString() {
        return "";
    }
}
