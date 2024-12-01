package org.firstinspires.ftc.teamcode.utils;

import java.util.ArrayList;

public class TaskManager
{

    // A list of tasks to run
    private ArrayList <Task> tasks = new ArrayList<>();

    // State of the robot
    private State state;

    /**
     * Constructor for the TaskManager class
     * @param state the state of the robot
     */
    public TaskManager(State state)
    {
        this.state = state;
        tasks = new ArrayList<>();
    }

    /**
     * Resets the task manager
     */
    public void reset()
    {
        state.telemetry.addData("Resetting Task Manager", "true");
        tasks.clear();
    }

    /**
     * Adds a task to the task manager
     * @param task the task to add
     */
    public void addTask(Task task)
    {
        state.telemetry.addData("Adding Task", task.toString());
        tasks.add(task);
    }

    /**
     * Immediately queues a task to run
     * @param task the task to queue
     */
    public void immediatelyQueueTask(Task task)
    {
        state.telemetry.addData("Immediately Queuing Task", task.toString());
        tasks.add(0, task);
    }

    /**
     * Runs all tasks in the task manager or until a task fails to finish
     */
    public void runTasks()
    {
        // Loop over each task
        for (int i = 0; i < tasks.size(); i++)
        {
            Task task = tasks.get(i);

            // Run the task
            task.run(state);
            state.telemetry.addData("Running Task", task.toString());

            // If the task has completed, run the next task, otherwise break
            if (task.finished)
            {
                state.telemetry.addData("Task Finished", task.toString());
                tasks.remove(i);
            } else
            {
                state.telemetry.addData("Task Not Finished", task.toString());
                break;
            }
        }
    }

    /**
     * Checks if the task manager is empty
     * @return true if the task manager is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return tasks.isEmpty();
    }

    /**
     * Gets the number of tasks in the task manager
     * @return the number of tasks in the task manager
     */
    public int getTaskCount()
    {
        return tasks.size();
    }

    /**
     * Gets a task from the task manager
     * @param index given index to check
     * @return the task at the given index
     */
    public Task getTask(int index) {
        // Check if the index is valid
        if (index < 0 || index >= tasks.size())
        {
            state.telemetry.addData("Invalid Index", index);
            return null;
        }
        return tasks.get(index);
    }

}
