package org.firstinspires.ftc.teamcode.tasks;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class TurnDegreesTask extends Task {

    private double degrees;
    private double power;

    public TurnDegreesTask(double degrees, double power) {
        this.degrees = degrees;
        this.power = power;
    }

    @Override
    public void run(State state)
    {
        double preportionalPower = power * (degrees - state.hardware.getYawInDegrees()) / degrees;
        if (state.hardware.getYawInDegrees() < degrees)
            state.hardware.setDrive(0, preportionalPower, 0);
        else
            state.hardware.setDrive(0, -preportionalPower, 0);

        if (Math.abs(state.hardware.getYawInDegrees() - degrees) < 5)
            finished = true;
    }


    @NonNull
    @Override
    public String toString() {
        return "Turn Degrees Task";
    }
}
