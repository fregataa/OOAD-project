import java.time.LocalTime;
import java.util.TimerTask;

public class Timeout extends TimerTask {
    private LocalTime waitTime;

    public Timeout(){
        java.util.Timer timeoutThread = new java.util.Timer();
        timeoutThread.schedule(this, 0, 1000);
        waitTime = LocalTime.of(0,0,0);
    }

    public void setWaitTime(LocalTime time){ this.waitTime = time; }
    public LocalTime getWaitTime(){ return this.waitTime; }

    public void run() {
        waitTime = waitTime.plusSeconds(1);
    }
}