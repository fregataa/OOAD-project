import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class Timer extends TimerTask {
    private ZonedDateTime timerTime;
    private ZonedDateTime runTime;
    private int count_sec;
    private boolean isStartedTimer;

    private java.util.Timer timerThread = new java.util.Timer();
    private Buzzer buzzer = new Buzzer();
    private TimeKeeping currentTime = new TimeKeeping();


    Timer() {
        timerThread.scheduleAtFixedRate(this, 0,1000);
        timerTime = currentTime.getCurrentTime();
        timerTime = timerTime.withHour(0);
        timerTime = timerTime.withMinute(0);
        timerTime = timerTime.withSecond(0);
        runTime = timerTime;
    }

    public String getTimerTime() {
        return runTime.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    public void setTimerTime(ZonedDateTime time) {
        this.timerTime = time;
        runTime = timerTime;
        count_sec = timerTime.toLocalTime().toSecondOfDay();
    }

    public ZonedDateTime getRunTime() {
        return runTime;
    }

    public void startTimer(ZonedDateTime runTime) {
        isStartedTimer = true;
        this.runTime = runTime;
    }

    public void pauseTimer() {
        isStartedTimer = false;
    }

    public void resetTimer() {
        runTime = timerTime;
        count_sec = timerTime.toLocalTime().toSecondOfDay();
    }

    @Override
    public void run() {
        if(isStartedTimer) {
            if(count_sec > 1) {
                count_sec--;
                runTime = runTime.minusSeconds(1);
                System.out.println(count_sec);
            }
            else if(count_sec == 1){
                runTime=runTime.minusSeconds(1);
                buzzer.reqBeep();
                this.pauseTimer();
            }
        }
    }

}