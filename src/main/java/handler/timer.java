package handler;

import java.util.Timer;
import java.util.TimerTask;

import static function.dataupdate.DataUpdate;

public class timer extends TimerTask {
public static Timer timer = new Timer();
    public void run() {
        DataUpdate();
    }

    public static void main() {
        timer.schedule(new timer(), 1000, 10000);
    }
}

