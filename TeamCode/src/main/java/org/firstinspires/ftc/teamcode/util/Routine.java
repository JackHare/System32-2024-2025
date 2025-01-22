package org.firstinspires.ftc.teamcode.util;

import java.util.ArrayList;

public class Routine {

    private ArrayList<Task> tasks;

    private Routine() {
        tasks = new ArrayList<Task>();
    }

    public static Routine builder() {
        return new Routine();
    }

    public Routine addTask(Task task) {
        tasks.add(task);
        return this;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void execute() throws Exception {
        for (int i = 0; i < tasks.size(); i++)
        {
            Task task = tasks.get(i);
            task.run();
            if (task.isFinished())
            {
                tasks.remove(i);
                i--;
            }
        }

    }

    public boolean isFinished() {
        return tasks.isEmpty();
    }
}
