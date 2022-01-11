package handler;

import java.util.Timer;
import java.util.TimerTask;

import static function.dataupdate.DataUpdate;

public class timer extends TimerTask {

    public void run() {
        DataUpdate();
    }

    public static void main() {
        Timer timer = new Timer();
        timer.schedule(new timer(), 1000, 10000);
    }
}

