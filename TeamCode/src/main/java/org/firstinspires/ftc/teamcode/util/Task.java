package org.firstinspires.ftc.teamcode.util;

import androidx.annotation.NonNull;

public abstract class Task {

    private boolean finished = false;
    public abstract void run() throws Exception;
    @NonNull
    public abstract String toString();

    public boolean isFinished() {
        return finished;
    }

    protected void finish() {
        finished = true;
    }

}
