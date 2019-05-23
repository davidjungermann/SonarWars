package com.example.myapplication;

import java.util.Timer;
import java.util.TimerTask;

public class CoolDown {
    private static CoolDown instance = null;
    private boolean valid;
    private Timer timer;
    private long delay = 200;

    private CoolDown() {
        timer = new Timer();
        valid = true;
    }

    public static CoolDown getSharedInstance() {
        if (instance == null) {
            instance = new CoolDown();
        }
        return instance;
    }

    public boolean checkValidity() {
        if (valid) {
            valid = false;
            timer.schedule(new Task(), delay);
            return true;
        }
        return false;
    }

    class Task extends TimerTask {

        public void run() {
            valid = true;
        }

    }
}
