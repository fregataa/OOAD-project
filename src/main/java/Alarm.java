import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm{
    private LocalTime alarmTime;
    private LocalTime now;
    private boolean isActivated;
    private Timer alarmThread;
    private Buzzer aBuzzer;
    private TimerTask timerTask;
    private TimeKeeping timeKeeping = TimeKeeping.getInstance();

    public LocalTime getAlarmValue(){
        return this.alarmTime;
    }

    public boolean getActivated() {
        return this.isActivated;
    }


    Alarm() {
        this.alarmTime = LocalTime.of(00,00,00);
        this.isActivated = false;
        this.aBuzzer = new Buzzer();
        this.alarmThread = new Timer();
    }

    public void saveAlarmTime(LocalTime newAlarm) {    //파라미터가 필요할 것 같다.
        this.alarmThread.purge(); //기존 타이머는 지우고 타이머를 새로 생성
        this.alarmThread = new Timer();
        this.alarmTime = newAlarm;
        this.activateAlarm();
    }

    public void activateAlarm() { //현재 시간보다 앞이면 date를 다음 날로 보내줘야 해요
        this.isActivated = true;
        //알람시간이 현재시간보다 앞이면 다음날로 활성화, 뒤면 오늘로 활성화
        //단, 변경된 시차에도 알람이 제대로 작동하게 하기 위해선 TimeKeeping의 시간을 써야 할 것 같다.
        //설정한 알람 시간으로 Date 객체 만들어서 aTimer.schedule에 등록

        timerTask = new TimerTask() {
            @Override
            public void run() {
                now = timeKeeping.getCurrentTime().toLocalTime();
                //if(alarmTime.compareTo(now) == 0)
                //aBuzzer.reqBeep();

                if(alarmTime.getHour() == now.getHour() && alarmTime.getMinute() == now.getMinute() && 0==now.getSecond()) {
                    aBuzzer.reqBeep();
                }
            }
        };

        alarmThread.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void deactivateAlarm(){
        this.isActivated = false;
        timerTask.cancel();
        aBuzzer.stopBeep();
    }

    //알람 설정 시 ParseException 추가!!
    //남은일 : 알람 설졍(TimeKeeping 참고)
    //활성화 여부를 불빛으로 표시
}