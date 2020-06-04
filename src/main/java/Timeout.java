import java.time.LocalTime;
import java.util.TimerTask;

public class Timeout extends TimerTask {
    private java.util.Timer gTimer = new java.util.Timer();
    private LocalTime waitTime;

    public Timeout(){
        waitTime = LocalTime.of(0,0,0);
        gTimer.schedule(this, 0, 1000);
    }

    public void setWaitTime(LocalTime time){ this.waitTime = time; }
    public LocalTime getWaitTime(){ return this.waitTime; }

    public void run() {
        waitTime = waitTime.plusSeconds(1);
    }

}