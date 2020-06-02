import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Controller {
    /*
   <modeIndicator index>
   * 0 - TimeKeeping
   * 1 - Alarm
   * 2 - Stopwatch
   * 3 - Timer
   * 4 - WorldTime
   * 5 - Turnip Calculator
   */

    //private ZonedDateTime timeValue; 삭제
    private int[] modeIndicator = new int[6];
    private int currentMode;
    private String segment;
    private int currentCursor;
    private static int maxCursor = 5;
    private static int[] maxValueOfCursor = {23, 60, 60, 31, 12, 2030};
    private int currentPage;
    private int maxPage;
    private int priceValue;
    // 24 - true, 12 - false
    private Boolean is24;  //isMorning->is24
    private int waitTime;

    private int value;
    private int[] maxValue;

    //추가한 변수
    private int alarmPage;
    private static int maxAlarmPage = 3;
    private LocalTime timerTime;
    private ZonedDateTime currentTime;
    private LocalTime alarmTime;
    private static int maxTurnipValue = 600;
    private int turnipValue;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;
    private int selectedMode;
    private int currentIndicator;
    private int turnipPage;

    private TimeKeeping timeKeeping = new TimeKeeping();
    private Alarm[] alarm = new Alarm[4];
    for(int i = 0; i<4; i++){ alarm[i] = new Alarm(); }
    private Stopwatch stopwatch = new Stopwatch();
    private Timer timer = new Timer();
    private WorldTime worldTime = new WorldTime();
    private TurnipCalc turnipCalculator = new TurnipCalc();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private ModeSwitch modeSwitch = new ModeSwitch();
    private Buzzer buzzer = new Buzzer();

    Controller() {
        currentMode = modeSwitch.initialize();
        is24 = true;
    }

    public void display() {
        //mode Indicator, isMorning, segment 를 UI에 전달
        switch (currentMode) {
            case 0:
                System.out.println("TimeKeeping 모드");
                break;
            case 1:
                System.out.println("Alarm 모드");
                break;
            case 2:
                System.out.println("Stopwatch 모드");
                break;
            case 3:
                System.out.println("Timer 모드");
                break;
            case 4:
                System.out.println("WorldTime 모드");
                break;
            case 5:
                System.out.println("Turnip Calculator 모드");
                break;
            default:
                break;
        }
    }

    public void reqChangeTimeFormat() {
        timeFormatCalc();
    }

    //수정필요
    public void timeFormatCalc() {
        if (is24 == true) {
            is24 = false;
        }
        else is24 = true;
    }

    //수정필요
    //public void showNextBlink() { currentCursor++; }

    // 현재 커서 위치와 시간값을 어떻게 동시에 보내면 좋을지?
    public int reqSetting() {
        currentCursor = 0;
        //showNextBlink
        switch (currentMode) {
            case 0:
                currentTime = timeKeeping.getCurrentTime();
                year = currentTime.getYear();
                month = currentTime.getMonthValue();
                day = currentTime.getDayOfMonth();
                hour = currentTime.getHour();
                min = currentTime.getMinute();
                sec = currentTime.getSecond();
                return currentTime; //currentTime String으로 넘겨준다.
            break;
            case 1:
                alarmTime = alarm[currentPage].getAlarmValue();
                hour = alarmTime.getHour();
                min = alarmTime.getMinute();
                sec = alarmTime.getSecond();
                return currentCursor;
            case 3:
                timerTime = timer.getTimeValue();
                hour = timerTime.getHour();
                min = timerTime.getMinute();
                sec = timerTime.getSecond();
                return currentCursor;
            case 4:
                System.out.println("WorldTime 모드");
                break;
            case 5:
                turnipValue = turnipPrice.getTurnipPrice(turnipPage);
            default:
                break;
        }
    }

        /*
      <cursor index>         maxValue
      * 0 - hour             23
      * 1 - min              60
      * 2 - sec              60
      * 3 - day              31
      * 4 - month            12
      * 5 - year             ?
      */

    //버튼이벤트
    public void nextUnit() {
        if (currentCursor != maxCursor) increaseUnit();
        else initUnit();
    }

    public void increaseUnit() {
        currentCursor++;
    }

    public void initUnit() {
        currentCursor = 0;
    }

    public int changeUnitValue() {
        value = changeValue();
        if (value > maxValueOfCursor[currentCursor]) minimizeValue();
        else if (value < 0) maximizeValue();
        return value;
    }

    public int changeValue() {
        if (/*증가버튼*/) {
            switch (currentCursor) {
                case 0:
                    return ++hour;
                case 1:
                    return ++min;
                case 2:
                    return ++sec;
                case 3:
                    return ++day;
                case 4:
                    return ++month;
                case 5:
                    return ++year;
                default:
                    break;
            }
        } else if (/*감소버튼*/) {
            switch (currentCursor) {
                case 0:
                    return --hour;
                case 1:
                    return --min;
                case 2:
                    return --sec;
                case 3:
                    return --day;
                case 4:
                    return --month;
                case 5:
                    return --year;
                default:
                    break;
            }
        }
    }

    public void minimizeValue() {
        value = 0;
    }

    public void maximizeValue() {
        if(currentMode != 5) value = maxValueOfCursor[currentCursor];
        else value = 600;
    }

    //timeValue값 생각해보기
    public String reqCompleteSetting() {
        switch (currentMode) {
            case 0:
                currentTime.of(year, month, day, hour, min, sec);
                timeKeeping.saveTime(currentTime);
                return (timeKeeping.getCurrentTime()).toString();
            case 1:
                alarmTime.of(hour, min, sec);
                alarm[alarmPage].saveAlarm(alarmTime);
                return (alarm[alarmPage].getAlarmValue()).toString();
            case 3:
                timerTime.of(hour, min, sec);
                timer.saveTimer(timerTime);
                return (timer.getTimeValue()).toString;
            case 4:
                System.out.println("WorldTime 모드");
                break;
            case 5:
                turnipPrice.savePrice(value);
                return Integer.toString(value);
            default:
                break;
        }
    }

    //여기부터 다시 수정
    //타이머
    public void reqStartTimer() {
        timer.startTimer();
    }

    public void reqPauseTimer() {
        timer.pauseTimer();
    }

    public void reqResetTimer() {
        timer.resetTimer();
    }

    //스탑워치
    public void reqStartStopWatch() {
        stopwatch.startStopwatch();
    }

    public void reqPauseStopWatch() {
        stopwatch.pauseStopwatch();
    }

    public void reqResetStopWatch() {
        stopwatch.resetStopwatch();
    }

    public void reqLapTime() {
        stopwatch.lapTime();
    }

    //알람
    public void reqActivateAlarm() {
        alarm[alarmPage].activateAlarm();
    }

    public void reqDeactivateAlarm() {
        alarm[alarmPage].deactivateAlarm();
    }

    public void reqChangeIndicatedAlarm() {
        if (alarmPage != maxAlarmPage) alarmPage++;
        else alarmPage = 0;
        return alarm[alarmPage].getAlarmValue();
    }

    //세계시간
    public void reqChangeWorldTime() {
        worldTime.nextWorldTime();
    }

    public void reqChangeTimeZone() {
        worldTime.changeTimeZone();
    }

    //무
    public int ChangePriceValue() {
        value = changeValue();
        if (value > maxTurnipValue) minimizeValue();
        else if (value < 0) maximizeValue();
        return value;
    }

    public void reqResetPrice() {
        turnipPrice.resetPrice();
    }

    public String reqChangeDate() {
        value = turnipPrice.nextPrice();
        return Integer.toString(value);
    }

    //모드스위치
    public void reqModeSwitch() {
        modeSwitch.nextMode();
    }

    public int reqSetIndicateMode() {
        selectedMode = 0;
        currentIndicator = 1;
        return currentIndicator;
    }

    public void reqSelectMode() {
        if (selectedMode < 2) {
            modeIndicator[currentIndicator] = 1;
            selectedMode++;
        } else if (selectedMode == 2) {
            modeIndicator[currentIndicator] = 1;
            modeSwitch.saveMode(modeIndicator);
            //modeIndicator = modeSwitch.getEnabledMode();
            selectedMode = 0;
        }
    }

    public void reqUnselectMode(int currentCursor) {
        modeIndicator[currentCursor] = 0;
        selectedMode--;
    }

    public void reqCancelSetIndicateMode() {
        /*timeKeeping모드로돌아간다.*/
        //modeIndicator = modeSwitch.getEnabledMode();
        currentMode = 0;
    }

    //추가한 메소드
    public void nextIndicator() {
        if(currentIndicator!=5) currentIndicator++;
        else currentIndicator = 1;
    }

    //버저
    public void reqStopBeep() {
        buzzer.stopBeep();
    }

    /*
    public static void main(String[] args) {

        controller.reqStartTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqPauseTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqStartTimer();

    }
    */
}