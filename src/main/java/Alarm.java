import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm{
    private LocalTime alarmTime;
    private boolean isActivated;
    private int maxCursor;
    private int[] maxValueOfCursor = new int[]{};
    private int maxPage;
    private Timer atimer;
    private Buzzer aBuzzer;
    private TimerTask timerTask;

    private String source = "300101";

    public Alarm() {
        this.alarmTime = LocalTime.of(00,00,00);
        this.isActivated = false;
        this.aBuzzer = new Buzzer();
        this.atimer = new Timer();
    }

    public LocalTime getAlarmValue(){
        return this.alarmTime;
    }
    public void saveAlarmTime(LocalTime newAlarm, String nowDate) throws ParseException {    //파라미터가 필요할 것 같다.
        this.atimer.purge(); //기존 타이머는 지우고 타이머를 새로 생성
        this.atimer = new Timer();
        this.alarmTime = newAlarm;
        this.source = nowDate;
        this.activateAlarm();
    }

    public void activateAlarm() throws ParseException { //현재 시간보다 앞이면 date를 다음 날로 보내줘야 해요
        this.isActivated = true;
        int offset = alarmTime.compareTo(LocalTime.now());

        //알람시간이 현재시간보다 앞이면 다음날로 활성화, 뒤면 오늘로 활성화
        //단, 변경된 시차에도 알람이 제대로 작동하게 하기 위해선 TimeKeeping의 시간을 써야 할 것 같다.

        source += alarmTime.format(DateTimeFormatter.ofPattern("HHmmss")); //알람 시간 파싱
        Date targetTime = new SimpleDateFormat("yyMMddHHmmss").parse(source);

        //설정한 알람 시간으로 Date 객체 만들어서 aTimer.schedule에 등록

        timerTask = new TimerTask() {
            @Override
            public void run() {
                aBuzzer.reqBeep();
            }
        };
        atimer.scheduleAtFixedRate(timerTask, targetTime, 86400000); //하루는 86400초, 절대적 간격으로 반복
    }
    public void deactivateAlarm(){
        this.isActivated = false;

        //Buzzer 등록된 타이머를 제거

        timerTask.cancel();
        aBuzzer.stopBeep();
    }
    public int nextAlarm(){ //하나의 알람 객체는 하나의 정보만 가지기 때문에 이건 Controller의 메소드로 구형해야 할 것 같다.
        return 0;
    }

    public boolean getActivated() {
        return this.isActivated;
    }

    //알람 설정 시 ParseException 추가!!
    //남은일 : 알람 설졍(TimeKeeping 참고)
    //활성화 여부를 불빛으로 표시
}