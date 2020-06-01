import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm{
    private LocalTime alarmTime;
    private boolean isActivated;
    private int maxCursor;
    private int[] maxValueOfCursor = new int[];
    private int maxPage;

    public LocalTime getAlarmValue(){ reuturn this.alarmTime;}
    public void saveAlarmTime(){}
    public void activateAlarm(){}
    public void deactivateAlarm(){}
}