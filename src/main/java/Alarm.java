import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm{
    private LocalTime alarmTime;
    private LocalTime now;
    private boolean isActivated;
    private Timer alarmThread;
    private TimerTask timerTask;
    private TimeKeeping timeKeeping = TimeKeeping.getInstance();

    public LocalTime getAlarmTime(){
        return this.alarmTime;
    }

    public void setAlarmTime(LocalTime newAlarm) {
        this.alarmThread.purge();
        this.alarmThread = new Timer();
        this.alarmTime = newAlarm;
        this.activateAlarm();
    }

    public boolean getActivated() {
        return this.isActivated;
    }


    Alarm() {
        this.alarmTime = LocalTime.of(00,00,00);
        this.isActivated = false;
        this.alarmThread = new Timer();
    }


    public void activateAlarm() {
        this.isActivated = true;

        timerTask = new TimerTask() {
            @Override
            public void run() {
                now = timeKeeping.getCurrentTime().toLocalTime();
                if(isActivated && alarmTime.getHour() == now.getHour() && alarmTime.getMinute() == now.getMinute() && 0==now.getSecond()) {
                    Buzzer.reqBeep();
                }
            }
        };
        alarmThread.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void deactivateAlarm(){
        if(timerTask!=null) {
            timerTask.cancel();
            alarmThread.cancel();
            alarmThread.purge();
        }
        this.isActivated = false;
        Buzzer.stopBeep();
    }
}