package org.firstinspires.ftc.teamcode.util;

import java.util.ArrayList;

public class RoutineExecutor {

    private ArrayList<Routine> routines;

    public RoutineExecutor()
    {
        routines = new ArrayList<>();
    }

    public void executeOneRoutine() throws Exception {
        if (routines.isEmpty())
            return;

        Routine routine = routines.get(0);
        routine.execute();
        if (routine.isFinished())
        {
            routines.remove(0);
        }
    }

    public void addRoutine(Routine routine)
    {
        routines.add(routine);
    }
}
