package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.tasks.Arm1SafetyTask;
import org.firstinspires.ftc.teamcode.tasks.Arm2SafetyTask;

public abstract class TaskOpMode extends LinearOpMode {

    // runUpdate is set to true so that it runs when the opmode first starts
    private boolean runUpdate = true;
    private int updateCount = 0;

    public State state;
    public TaskManager taskManager;

    @Override
    public void runOpMode() throws InterruptedException
    {
        // Initialize robot state and hardware
        state = new State(hardwareMap, telemetry);
        telemetry.addData("State Initialized", "true");

        // Initialize task manager
        taskManager = new TaskManager(state);
        telemetry.addData("Task Manager Initialized", "true");

        if (!state.DANGEROUS_ARM_SAFETY_OVERRIDE)
        {
            taskManager.addTask(new Arm1SafetyTask());
            taskManager.addTask(new Arm2SafetyTask());
            telemetry.addData("Arm Safety Tasks Initialized", "true");
        }
        else
        {
            telemetry.addData("WARNING: Arm Safety Tasks overridden, unsafe operations may occur", "true");
        }

        // Call user defined setup method
        setUp();
        telemetry.addData("Finish Running Setup", "true");

        // Wait for start to be pressed on driver hub
        telemetry.addData("Waiting for opmode start", "true");
        waitForStart();

        // Reset runtime so that its time == 0 at opmode start
        state.resetRunTime();
        telemetry.addData("Reset Runtime", "true");

        // OpMode loop
        while (opModeIsActive())
        {
            // Call user defined repeat method
            repeat();
            telemetry.addData("Ran repeat", "true");

            // Run tasks
            if (!taskManager.isEmpty())
            {
                taskManager.runTasks();
                telemetry.addData("Ran Tasks", "true");
            }
            else
            {
                telemetry.addData("No Tasks to Run", "true");
            }

            // Call user defined update method if runUpdate has been triggered
            if (runUpdate)
            {
                update();
                updateCount++;
                runUpdate = false;
                telemetry.addData("Running Update", "true");
                telemetry.addData("Update Count", updateCount);
            }
            else
            {
                telemetry.addData("Not Running Update", "true");
            }

            // Sync telemetry to driver hub
            telemetry.update();
        }
    }

    /**
     * Requests the update method to be called
     */
    public void requestUpdate()
    {
        runUpdate = true;
        telemetry.addData("Requesting Update", "true");
    }

    /**
     * Runs the arm safety methods
     * Updates the DANGEROUS_ARM_SAFETY_OVERRIDE flag
     * Note: these tasks are appended to the front of the task queue
     */
    public void runArmSafety()
    {
        state.DANGEROUS_ARM_SAFETY_OVERRIDE = false;
        taskManager.immediatelyQueueTask(new Arm2SafetyTask());
        taskManager.immediatelyQueueTask(new Arm1SafetyTask());
        telemetry.addData("Running Arm Safety", "true");
    }

    /**
     * Gets the number of times the update method has been called
     * @return number of times update method has been called
     */
    public int getUpdateCount()
    {
        return updateCount;
    }

    /**
     * Gets whether the arm is safe to use
     * @return whether the arm is safe to use
     */
    public boolean isArmSafe()
    {
        return !state.DANGEROUS_ARM_SAFETY_OVERRIDE;
    }

    /**
     * User defined setup method, runs once when the opmode is initialized
     */
    public abstract void setUp();

    /**
     * User defined update method, runs when an update is triggered
     */
    public abstract void update();

    /**
     * User defined loop method, runs every opmode loop
     */
    public abstract void repeat();

}
