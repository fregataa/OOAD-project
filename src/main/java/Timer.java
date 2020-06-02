import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;

public class Timer extends TimerTask {
    private LocalDateTime timerTime;
    private LocalDateTime startTime;
    private int maxCursor;
    private int maxValueofCursor[];
    private int maxPage;
    private int count_sec;
    private long count;
    private boolean isStartedTimer;

    private java.util.Timer stimer = new java.util.Timer();

    @Override
    public void run() {
        if(count_sec>0 && isStartedTimer) {
            count_sec--;
            System.out.println(count_sec);
        }
        else if(!isStartedTimer) stimer.cancel();
        else {
            System.out.println("ringringring");
            stimer.cancel();
        }
    }

    public void startTimer(LocalTime timerValue) {
        isStartedTimer = true;
        //startTime = LocalDateTime.now();
        //count = timeValue.toSecondOfDay()
        count_sec = timerValue.toSecondOfDay();
        stimer.schedule(this, 0,1000);
    }

    public LocalDateTime getTimerValue() {
        return timerTime;
    }

    public void saveTimer(LocalDateTime timeValue) {
        this.timerTime = timeValue;
    }

    public void pauseTimer() {
        isStartedTimer = false;
    }

    public void resetTimer() {
        saveTimer(timerTime);
    }

}