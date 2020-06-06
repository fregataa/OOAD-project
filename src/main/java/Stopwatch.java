import java.time.LocalTime;
import java.util.TimerTask;

public class Stopwatch extends TimerTask{
    private LocalTime stopwatchTime;
    private LocalTime lapTime;
    private boolean isStartedStopwatch;

    public LocalTime getStopwatchTime() { return this.stopwatchTime; }

    public LocalTime getLapTime(){ return lapTime; }

    public boolean getIsStartedStopwatch(){return this.isStartedStopwatch;}


    Stopwatch() {
        java.util.Timer stopwatchThread = new java.util.Timer();
        this.stopwatchTime = LocalTime.of(0,0,0);
        this.lapTime = LocalTime.of(0,0,0);
        stopwatchThread.scheduleAtFixedRate(this,0,10);
    }

    public void startStopwatch(){
        isStartedStopwatch = true;
    }

    public void pauseStopwatch(){
        isStartedStopwatch = false;
    }


    public void resetStopwatch(){
        this.stopwatchTime = LocalTime.of(0,0,0);
        this.lapTime = LocalTime.of(0,0,0);
        isStartedStopwatch = false;
    }

    public void lapTime(){ lapTime = stopwatchTime; }


    @Override
    public void run() {
        if(isStartedStopwatch){
            this.stopwatchTime = this.stopwatchTime.plusNanos(10000000);
            if(this.stopwatchTime.getHour() == 1) this.resetStopwatch();
        }
    }
}
