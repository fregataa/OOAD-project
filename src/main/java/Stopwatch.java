import java.time.LocalTime;
import java.util.TimerTask;

public class Stopwatch extends TimerTask{
    private LocalTime stopwatchTime;
    private LocalTime lapTime;
    private boolean isStartedStopwatch;
    private java.util.Timer mTimer = new java.util.Timer();

    public Stopwatch(){
        this.stopwatchTime = LocalTime.of(0,0,0);
        this.lapTime = LocalTime.of(0,0,0);
    }

    @Override
    public void run() {
        if(isStartedStopwatch){
            this.stopwatchTime = this.stopwatchTime.plusSeconds(1);
            System.out.println(this.stopwatchTime);
        }
    }

    public void startStopwatch(){
        isStartedStopwatch = true;
        this.mTimer.scheduleAtFixedRate(this,0,1000);

    }

    public void pauseStopwatch(){
        isStartedStopwatch = false;
    }

    public boolean getIsStartedStopwatch(){return this.isStartedStopwatch;}

    public void resetStopwatch(){
        this.stopwatchTime = LocalTime.of(0,0,0);
        this.lapTime = LocalTime.of(0,0,0);
    }

    public void lapTime(){ lapTime = stopwatchTime; }

    public LocalTime getLapTime(){ return lapTime; }

    public LocalTime getStopwatchTime() { return this.stopwatchTime; }
}
