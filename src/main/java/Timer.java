import java.time.LocalTime;
import java.util.TimerTask;

public class Timer extends TimerTask {
    private LocalTime timerTime;
    private LocalTime runTime;
    private int count_sec;
    private boolean isStartedTimer;

    public LocalTime getTimerTime() {
        return timerTime;
    }

    public void setTimerTime(LocalTime time) {
        this.timerTime = time;
        runTime = timerTime;
        count_sec = timerTime.toSecondOfDay();
    }

    public LocalTime getRunTime() {
        return runTime;
    }

    public boolean getIsStartedTimer() {
        return isStartedTimer;
    }

    Timer() {
        java.util.Timer timerThread = new java.util.Timer();
        timerThread.scheduleAtFixedRate(this, 0,1000);
        timerTime = LocalTime.of(00,00,00);
        runTime = timerTime;
    }

    public void startTimer(LocalTime runTime) {
        isStartedTimer = true;
        this.runTime = runTime;
    }

    public void pauseTimer() {
        isStartedTimer = false;
    }

    public void resetTimer() {
        runTime = timerTime;
        count_sec = timerTime.toSecondOfDay();
    }

    @Override
    public void run() {
        if(isStartedTimer) {
            if(count_sec > 1) {
                count_sec--;
                runTime = runTime.minusSeconds(1);
            }
            else if(count_sec == 1){
                runTime=runTime.minusSeconds(1);
                isStartedTimer = false;
                this.resetTimer();
                Buzzer.reqBeep();
            }
        }
    }

}