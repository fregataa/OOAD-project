import java.time.LocalTime;

public class Alarm{
    private LocalTime alarmTime;
    private boolean isActivated;
    private int maxCursor;
    private int[] maxValueOfCursor = new int[]{};
    private int maxPage;

    public Alarm() {
        this.alarmTime = LocalTime.of(00,00,00);
        this.isActivated = false;
    }

    public LocalTime getAlarmValue(){
        return this.alarmTime;
    }
    public void saveAlarmTime(LocalTime newAlarm){    //파라미터가 필요할 것 같다.
        this.alarmTime = newAlarm;
    }
    public void activateAlarm(){
        this.isActivated = true;
    }
    public void deactivateAlarm(){
        this.isActivated = false;
    }
    public int nextAlarm(){ //하나의 알람 객체는 하나의 정보만 가지기 때문에 이건 Controller의 메소드로 구형해야 할 것 같다.
        return 0;
    }
}