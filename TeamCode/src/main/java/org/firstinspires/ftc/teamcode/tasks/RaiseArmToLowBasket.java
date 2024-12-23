package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.utils.State;
import org.firstinspires.ftc.teamcode.utils.Task;

public class RaiseArmToLowBasket extends Task {

    /**
     * Raises arm1 and arm2 to roughly the same height as the low basket
     * @param state Robots current state
     * @return if the task has completed or not
     */
    @Override
    public void run(State state) {

        state.hardware.setArm1Position(-2500, -.5);
        state.hardware.setArm1Position(400, .5);
        if( state.hardware.getArm1Position() < -2400 && state.hardware.getArm2Position() > 370)
            finished = true;
    }

    public String toString()
    {
        return "RaiseArmToLowBasket";
    }
}