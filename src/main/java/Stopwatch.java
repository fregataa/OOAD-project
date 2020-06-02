import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch extends TimerTask{
    private LocalTime stopwatchTime = LocalTime.of(0,0,0);
    private int maxCursor;
    private int maxValueOfCursor[];
    private int maxPage;
    Timer m_timer = new Timer();

    public void run() {
        this.stopwatchTime = this.stopwatchTime.plusSeconds(1);
        System.out.println(this.stopwatchTime);
    }

    public void startStopwatch(){
        this.stopwatchTime = LocalTime.of(0,0,0);
        this.m_timer.schedule(this,0,1000);
    }

    public void pauseStopwatch(){
        this.m_timer.cancel();
    }

    public void resetStopwatch(){
        this.stopwatchTime = LocalTime.of(0,0,0);
    }

    public LocalTime lapTime(){
        return this.stopwatchTime;
    }
}
