package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.tasks.DriveMotorResetTask;
import org.firstinspires.ftc.teamcode.tasks.DriveRelativeToAprilTagTask;
import org.firstinspires.ftc.teamcode.tasks.DriveToPositionTask;
import org.firstinspires.ftc.teamcode.tasks.SetArmsPercentageTask;
import org.firstinspires.ftc.teamcode.util.Routine;
import org.firstinspires.ftc.teamcode.util.RoutineOpMode;

@Autonomous(name = "Red Auto")
public class RedAuto extends RoutineOpMode {

    int updateCounter = 0;

    @Override
    protected void initalize() {

    }

    @Override
    protected void repeat() throws Exception {

        if (updateCounter == 0)
        {
            updateCounter++;
            executor.addRoutine(Routine.builder().addTask(new DriveToPositionTask(robotState, 15, 1)));
            executor.addRoutine(Routine.builder().addTask(new DriveMotorResetTask(robotState)));
            executor.addRoutine(Routine.builder().addTask(new DriveRelativeToAprilTagTask(robotState, 1, 0, 24,0)));
            executor.addRoutine(Routine.builder().addTask(new DriveMotorResetTask(robotState)));
        }

        telemetry.addData("FrontLeft", robotState.getHardware().getDrive().getFrontLeftPower());
        telemetry.addData("FrontRight", robotState.getHardware().getDrive().getFrontRightPower());
        telemetry.addData("BackLeft", robotState.getHardware().getDrive().getBackLeftPower());
        telemetry.addData("BackRight", robotState.getHardware().getDrive().getBackRightPower());
    }
}
