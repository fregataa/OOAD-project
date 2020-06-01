import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatch extends TimerTask{
    private LocalTime stopWatchTime = LocalTime.of(0,0,0);
    private int maxCursor;
    private int maxValueOfCursor[];
    private int maxPage;
    Timer m_timer = new Timer();

    public void run() {
        this.stopWatchTime = this.stopWatchTime.plusSeconds(1);
        System.out.println(this.stopwatchTime);
    }

    public void startStopWatch(){
        this.stopWatchTime = LocalTime.of(0,0,0);
        this.m_timer.schedule(this,0,1000);
    }

    public void pauseStopWatch(){
        this.m_timer.cancel();
    }

    public void resetStopWatch(){
        this.stopWatchTime = LocalTime.of(0,0,0);
    }

    public LocalTime lapTime(){
        return this.stopWatchTime;
    }
}
