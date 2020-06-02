import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.LocalTime;
<<<<<<< HEAD
=======
import java.time.ZonedDateTime;
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77

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

<<<<<<< HEAD
    //private ZonedDateTime timeValue; 삭제
    private int[] modeIndicator = new int[6];
    private int currentMode;
    private String segment;
    private int currentCursor;
    private static int maxCursor = 5;
    private static int[] maxValueOfCursor = {23, 60, 60, 31, 12, 2030};
=======
    private ZonedDateTime timeValue;
    private int[] modeIndicator = {1,1,1,1,0,0};
    private int currentMode;
    private String segment1 = "000000";
    private String segment2 = "set--01--";
    private int currentCursor;
    private int maxCursor;
    private int[] maxValueOfCursor;
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
    private int currentPage;
    private int maxPage;
    private int priceValue;
    // 24 - true, 12 - false
<<<<<<< HEAD
    private Boolean is24;  //isMorning->is24
=======
    private Boolean is24;
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
    private int waitTime;

    private int value;
    private int[] maxValue;

    //추가한 변수
<<<<<<< HEAD
    private int alarmPage;
    private static int maxAlarmPage = 3;
    private LocalTime timerTime;
    private ZonedDateTime currentTime;
    private LocalTime alarmTime;
=======
    private int alarmPage; //currentPage로 수정
    private static int maxAlarmPage = 3;
    private LocalTime timerTime;
    private ZonedDateTime currentTime;
    private LocalDateTime alarmTime;
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
    private static int maxTurnipValue = 600;
    private int turnipValue;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;
    private int selectedMode;
<<<<<<< HEAD
    private int currentIndicator;
    private int turnipPage;

    private TimeKeeping timeKeeping = new TimeKeeping();
    private Alarm[] alarm = new Alarm[4];
    for(int i = 0; i<4; i++){ alarm[i] = new Alarm(); }
=======

    private TimeKeeping timeKeeping = new TimeKeeping();
    private Alarm[] alarm = new Alarm[4];
    /*
    for(int i = 0; i<4; i++){
        alarm[i] = new Alarm();
    }
    */

>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
    private Stopwatch stopwatch = new Stopwatch();
    private Timer timer = new Timer();
    private WorldTime worldTime = new WorldTime();
    private TurnipCalc turnipCalculator = new TurnipCalc();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private ModeSwitch modeSwitch = new ModeSwitch();
    private Buzzer buzzer = new Buzzer();
<<<<<<< HEAD

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
=======

    public int getCurrentMode() {
        return this.currentMode;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public int[] getModeIndicator() {
        return this.modeIndicator;
    }

    public void setModeIndicator(int[] mode) {
        this.modeIndicator = mode;
    }

    public String getSegment1() {
        return this.segment1;
    }

    public void setSegment1(String seg) {
        this.segment1 = seg;
    }

    public String getSegment2() {
        return this.segment2;
    }

    public void setSegment2(String seg) {
        this.segment2 = seg;
    }

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

    public void timeFormatCalc() {
        if (is24 == true) is24 = false;
        else is24 = true;
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

            case 1:
                alarmTime = alarm[alarmPage].getAlarmValue();
                hour = alarmTime.getHour();
                min = alarmTime.getMinute();
                sec = alarmTime.getSecond();
                return currentCursor;
            case 3:
                //오류
                //timerTime = timer.getTimeValue();
                hour = timerTime.getHour();
                min = timerTime.getMinute();
                sec = timerTime.getSecond();
                return currentCursor;
            case 4:
                System.out.println("WorldTime 모드");
                break;
            case 5:
                turnipValue = turnipPrice.inputPrice(currentPage);
            default:
                break;
        }

        //return값 다시 정해주기
        return 0;
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
        if (currentCursor != maxCursor) this.increaseUnit();
        else this.initUnit();
    }

    public void increaseUnit() {
        currentCursor++;
    }

    public void initUnit() {
        currentCursor = 0;
    }

    public void changeUnitValue(int currentCursor) {
        this.changeValue();
        if (value > maxValueOfCursor[currentCursor]) this.minimizeValue();
        else if (value < 0) this.maximizeValue();
    }

    public void changeValue() {
        /*
        if (증가버튼) {
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
        } else if (감소버튼) {
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
        */
    }

    public void minimizeValue() {
        value = 0;
    }


    //여기서부터 UI돌아가는거 확인을 위해 주석처리합니다.

    public void maximizeValue() {
        /*currentCursor에따라 maxValue값이 다르다.
        value = maxValue; */
        switch (this.currentCursor) {
            
        }
    }

    //timeValue값 생각해보기
    public void reqCompleteSetting() {
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
        switch (currentMode) {
            case 0:
                currentTime.of(year, month, day, hour, min, sec);
                timeKeeping.saveTime(currentTime);
<<<<<<< HEAD
                return (timeKeeping.getCurrentTime()).toString();
            case 1:
                alarmTime.of(hour, min, sec);
                alarm[alarmPage].saveAlarm(alarmTime);
                return (alarm[alarmPage].getAlarmValue()).toString();
            case 3:
                timerTime.of(hour, min, sec);
                timer.saveTimer(timerTime);
                return (timer.getTimeValue()).toString;
=======
                return timeKeeping.getCurrentTime();
            case 1:
                alarmTime.of(hour, min, sec);
                alarm[alarmPage].saveAlarm(alarmTime);
                return alarm[alarmPage].getAlarmValue();
            case 3:
                timerTime.of(hour, min, sec);
                timer.saveTimer(timerTime);
                return timer.getTimeValue();
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
            case 4:
                System.out.println("WorldTime 모드");
                break;
            case 5:
<<<<<<< HEAD
                turnipPrice.savePrice(value);
                return Integer.toString(value);
=======
                System.out.println("Turnip Calculator 모드");
                break;
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
            default:
                break;
        }
    }

<<<<<<< HEAD
    //여기부터 다시 수정
    //타이머
    public void reqStartTimer() {
        timer.startTimer();
=======
    //타이머
    public void reqStartTimer() {
        //timer.startTimer(timerTimeValue);
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
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
<<<<<<< HEAD
        stopwatch.lapTime();
=======
        //stopwatch.reqLapTime();
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
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
<<<<<<< HEAD
        return alarm[alarmPage].getAlarmValue();
=======
        //return alarm[alarmPage].getAlarmValue();
>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
    }

    //세계시간
    public void reqChangeWorldTime() {
<<<<<<< HEAD
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
=======
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
        this.changeValue();
        if (value > maxTurnipValue) minimizeValue();
        else if (value < 0) maximizeValue();
    }

    public void reqResetPrice() {
        turnipPrice.resetPrice();
    }

    public void reqChangeDate() {
        turnipValue = turnipPrice.nextPrice(currentPage);
        //return turnipValue;
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
            modeIndicator[currentCursor] = 1;
            selectedMode++;
        } else if (selectedMode == 2) {
            modeIndicator[currentCursor] = 1;
            //modeSwitch.saveMode(modeIndicator);
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
        selectedMode = 0;
    }

    //버저
    public void reqStopBeep() {
        buzzer.stopBeep();
    }

    //UI 확인용 Test Code
    public void testA() {
        segment1 = null;
        segment1 = "081023";
        setSegment1(segment1);
        return;
    }

    public void testB() {
        modeIndicator[5] = 1;
        modeIndicator[2] = 0;
        setModeIndicator(modeIndicator);
        return;
    }

>>>>>>> 5c94081172e18689fd3fb00d0e9d318526571d77
}