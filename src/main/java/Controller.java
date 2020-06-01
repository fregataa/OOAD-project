import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

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

    private ZonedDateTime timeValue;
    private Boolean[] modeIndicator = new Boolean[6];
    private int currentMode;
    private String segment;
    private int currentCursor;
    private int maxCursor;
    private int[] maxValueOfCursor;
    private int currentPage;
    private int maxPage;
    private int priceValue;
    // 24 - true, 12 - false
    private Boolean is24;
    private int waitTime;

    private int value;
    private int[] maxValue;

    //추가한 변수
    private int alarmPage; //currentPage로 수정
    private static int maxAlarmPage = 3;
    private LocalTime timerTime;
    private ZonedDateTime currentTime;
    private LocalTime AlarmTime;
    private static int maxTurnipValue = 600;
    private int turnipValue;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;
    private int selectedMode;

    private TimeKeeping timeKeeping = new TimeKeeping();
    private Alarm[] alarm = new Alarm[4];
    for(int i = 0;i<4;i++){ alarm[i] = new Alarm();}

    private Stopwatch stopwatch = new Stopwatch();
    private Timer timer = new Timer();
    private WorldTime worldTime = new WorldTime();
    private TurnipCalc turnipCalculator = new TurnipCalc();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private ModeSwitch modeSwitch = new ModeSwitch();
    private Buzzer buzzer = new Buzzer();

    public int getCurrentMode() {
        return this.currentMode;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    Controller() {
        currentMode = modeSwitch.initialize();
        isMorning = true;
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

    public void timeFormatCalc() {
        if (isMorning == true) isMorning = false;
        else isMorning = true;
    }

    public void showNextBlink() {
    }
    
    public int reqSetting() {
        //timeValue = timeKeeping.getCurrentTime();
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

                return currentCursor;
            break;
            case 1:
                alarmTime = alarm[alarmPage].getAlarmValue();
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
                turnipValue = turnipPrice.inputPrice( int currentPage);
            default:
                break;
        }
    }

    /*
  <cursor index>         maxValue
  * 0~5 - Indicator
  * 6 - AM/PM
  * 7 - hour             23
  * 8 - min              60
  * 9 - sec              60
  * 10 - day             31
  * 11 - year
  */

    //버튼이벤트
    public void nextUnit() {
        if (currentCursor != maxCursor) cotroller.increaseUnit();
        else controller.initUnit();
    }

    public void increaseUnit() {
        currentCursor++;
    }

    public void initUnit() {
        currentCursor = 0;
    }

    public void changeUnitValue(int currentCursor) {
        controller.changeValue();
        if (value > maxValueOfCursor[currentCursor]) controller.minimizeValue();
        else if (value < 0) controller.maximizeValue();
    }

    public void changeValue() {
        if (/*증가버튼*/) {
            switch (currentCursor) {
                case 7:
                    hour++;
                    break;
                case 8:
                    min++;
                    break;
                case 9:
                    sec++;
                    break;
                case 10:
                    day++;
                    break;
                case 11:
                    year++;
                    break;
                default:
                    break;
            }
        } else if (/*감소버튼*/) {
            switch (currentCursor) {
                case 7:
                    hour--;
                    break;
                case 8:
                    min--;
                    break;
                case 9:
                    sec--;
                    break;
                case 10:
                    day--;
                    break;
                case 11:
                    year--;
                    break;
                default:
                    break;
            }
        }
    }

    public void minimizeValue() {
        value = 0;
    }

    public void maximizeValue() {
        /*currentCursor에따라 maxValue값이 다르다.
        value = maxValue; */
        switch (this.currentCursor) {
            case
        }
    }

    //timeValue값 생각해보기
    public void reqCompleteSetting() {
        switch (currentMode) {
            case 0:
                currentTime.of(year, month, day, hour, min, sec);
                timeKeeping.saveTime(currentTime);
                return timeKeeping.getCurrentTime();
            case 1:
                alarmTime.of(hour, min, sec);
                alarm[alarmPage].saveAlarm(alarmTime);
                return alarm[alarmPage].getAlarmValue();
            case 3:
                timerTime.of(hour, min, sec);
                timer.saveTimer(timerTime);
                return timer.getTimeValue();
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

    //타이머
    public void reqStartTimer() {
        timer.startTimer(timerTimeValue);
    }

    public void reqPauseTimer() {
        timer.pauseTimer();
    }

    public void reqResetTimer() {
        timer.resetTimer();
    }

    //스탑워치
    public void reqStartStopWatch() {
        stopWatch.startStopWatch();
    }

    public void reqPauseStopWatch() {
        stopWatch.pauseStopWatch();
    }

    public void reqResetStopWatch() {
        stopWatch.resetStopwatch();
    }

    public void reqLapTime() {
        stopWatch.reqLapTime();
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
        worldTime.nextWorldTime( int currentPage);
    }

    public void reqChangeTimeZone() {
        worldTime.changeTimeZone( int currentPage);
    }

    //무
    /* reqSetting으로 가도 될듯..?
    public void reqInputPrice() { turnipPrice.inputPrice(int currentPage); }
     */

    public void ChangePriceValue() {
        controller.changeValue();
        if (value > maxTurnipValue) minimizeValue();
        else if (value < 0) maximizeValue();
    }

    public void reqResetPrice() {
        turnipPrice.resetPrice();
    }

    public void reqChangeDate() {
        turnipValue = turnipPrice.nextPrice(currentPage);
        return turnipValue;
    }

    //모드스위치
    public void reqModeSwitch() {
        modeSwitch.nextMode(currentMode);
    }

    public int reqSetIndicateMode() {
        currentCursor = 1;
        return currentCursor;
    }

    public void reqSelectMode(int currentCursor) {
        if (selectedMode < 2) {
            modeIndicator[currentCursor] = true;
            selectedMode++;
        } else if (selectedMode == 2) {
            modeIndicator[currentCursor] = true;
            modeSwitch.saveMode(modeIndicator);
            //modeIndicator = modeSwitch.getEnabledMode();
            selectedMode = 0;
        }
    }

    public void reqUnselectMode(int currentCursor) {
        modeIndicator[currentCursor] = false;
        selectedMode--;
    }

    public void reqCancelSetIndicateMode() {
        /*timeKeeping모드로돌아간다.*/
        //modeIndicator = modeSwitch.getEnabledMode();
        currentMode = 0;
        selectedMode = 0;
    }

    //버저
    public void reqStopBeep() {
        buzzer.stopBeep();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        /*
        controller.reqStartTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqPauseTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqStartTimer();
         */
    }
}