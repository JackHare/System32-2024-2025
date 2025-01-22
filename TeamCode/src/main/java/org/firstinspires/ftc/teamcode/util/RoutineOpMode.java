package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.tasks.Arm1SafetyTask;
import org.firstinspires.ftc.teamcode.tasks.Arm2SafetyTask;
import org.firstinspires.ftc.teamcode.tasks.Arm3SafetyTask;

public abstract class RoutineOpMode extends LinearOpMode {

    // Create routine executor
    protected RoutineExecutor executor = new RoutineExecutor();

    // Initialize robot state
    protected RobotState robotState;

    @Override
    public void runOpMode() throws InterruptedException {

        robotState = new RobotState(State.INITALIZATION, hardwareMap);

        // Run arm safety routine
        executor.addRoutine(Routine.builder().addTask(new Arm1SafetyTask(robotState)).addTask(new Arm3SafetyTask(robotState)).addTask(new Arm2SafetyTask(robotState)));


        // Call user defined initialize function
        initalize();

        // Wait for op mode start
        waitForStart();

        robotState.changeState(State.START);

        while (opModeIsActive())
        {
            // Update current apriltag
            robotState.setCurrentAprilTag(robotState.getHardware().getCamera().getAprilTagInfo());

            // Execute one routine
            try {
                executor.executeOneRoutine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            if (!robotState.getHardware().getArm1().getIfArmSafetyHasBeenRun() ||
            !robotState.getHardware().getArm2().getIfArmSafetyHasBeenRun() ||
            !robotState.getHardware().getArm3().getIfArmSafetyHasBeenRun())
            {
                telemetry.update();
                continue;
            }

            // Call user defined repeat function
            try {
                repeat();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Arm 2 Drag prevention
            // We need this because if arm1 is too low, and arm2 is too far extended, arm2 will drag on the field
            // We can avoid this by pulling arm2 all the way up
            if (robotState.getHardware().getArm1().getArmPercentage() <= 0.03) {
                try {
                    robotState.getHardware().getArm2().setArmPercentage(0);
                    telemetry.addData("Arm2 Drag Prevention", "ON");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            telemetry.update();

        }

    }

    protected abstract void initalize();
    protected abstract void repeat() throws Exception;
}
