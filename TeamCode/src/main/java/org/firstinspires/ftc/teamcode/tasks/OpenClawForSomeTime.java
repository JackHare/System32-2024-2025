package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class OpenClawForSomeTime extends Task {

    private double time;
    private double startTime;
    private double position;

    public OpenClawForSomeTime(State state, double time, double position)
    {
        startTime = state.getRunTimeInMilliseconds();
        this.time = time;
        this.position = position;
    }


    @Override
    public void run(State state)
    {
        if (state.getRunTimeInMilliseconds() - time > startTime)
        {
            finished = true;
            throw new RuntimeException("Open claw is not done");
          //  state.hardware.
            //return;
        }
      //  state.hardware.setDrive(power,0,0);
    }
}
