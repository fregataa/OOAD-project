import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm{
    private LocalDateTime alarmTime;
    private boolean isActivated;
    private int maxCursor;
    private int[] maxValueOfCursor = new int[];
    private int maxPage;

    public LocalDateTime alarmTime getAlarmValue(){ reuturn this.alarmTime;}
    public void saveAlarmTime(){}
    public void activateAlarm(){}
    public void deactivateAlarm(){}
    public int nextAlarm(){}
}