import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimerTask;
import java.lang.Math;
import static java.time.temporal.TemporalAdjusters.*;


public class Controller extends TimerTask {
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

    /* GUI 확인 임의 설정 */
    private int[] modeIndicator = new int[]{1,1,1,0,1,0};
    //private int currentMode;
    private String segment1 = "000000";
    private String segment2 = "set--01--";
    private Boolean is24;  //isMorning->is24
    private Boolean isChanging = false;
    private Boolean isActivatedTimer = false;
    /*---------------------*/

    private int currentCursor;
    private static int maxCursor = 5;
    private static int[] maxValueOfCursor = {23, 59, 59, 31, 12, 2030};
    private int currentPage = 0;
    private int maxPage;
    private int priceValue;
    // 24 - true, 12 - false
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
    private int selectedMode;
    private int currentIndicator;
    private int turnipPage;
    private boolean isSelectingMode;

    private TimeKeeping timeKeeping = TimeKeeping.getInstance();
    private Alarm[] alarm = new Alarm[4];
    private Stopwatch stopwatch = new Stopwatch();
    private Timer timer = new Timer();
    private WorldTime worldTime = new WorldTime();
    private TurnipCalc turnipCalculator = new TurnipCalc();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private ModeSwitch modeSwitch = new ModeSwitch();
    private Buzzer buzzer = new Buzzer();
    private Timeout timeout = new Timeout();

    public Timeout getTimeout(){ return this.timeout; }

    ///////////////////////////////////////////////////////
    public int getCurrentMode() {
        return this.modeSwitch.getMode();
    }

    public void setCurrentMode(int currentMode) {
        this.modeSwitch.setMode(currentMode);
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

    public Boolean getIs24() {
        return is24;
    }

    public void setIs24(Boolean is24) {
        this.is24 = is24;
    }

    public Boolean getChanging() {
        return this.isChanging;
    }

    public Boolean getIsActivatedTimer() {
        return this.isActivatedTimer;
    }

    public boolean getIsSelectingMode(){ return this.isSelectingMode; }

    public int getCurrentIndicator() { return this.currentIndicator;}
    ///////////////////////////////////////////////////////

    public boolean getIsStartedStopwatch(){return stopwatch.getIsStartedStopwatch();}


    Controller() {
        for(int i=0; i<4; i++){ alarm[i] = new Alarm(); }
        alarmTime = alarm[currentPage].getAlarmValue();
        modeSwitch.initialize();
        setCurrentMode(0);
        is24 = true;

    }

    @Override
    public void run() {

        GUI.getGUIInstance().invalidate();
        GUI.getGUIInstance().repaint();

        if(timeout.getWaitTime().toSecondOfDay()>60){
            setCurrentMode(0);
            timeout.setWaitTime(LocalTime.of(0,0,0));
            this.reqCancelSetIndicateMode();
        }

        if(isSelectingMode){
           this.setSegment1("");
           this.setSegment2("Mod");
        }
        else{
            switch (getCurrentMode()) {
                case 0:
                    System.out.println("TimeKeeping 모드");

                    if(isChanging == false) {
                        currentTime = timeKeeping.getCurrentTime();
                    }
                    //HH로 줘야만 제대로 작동합니다.
                    this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
                    this.setSegment2(currentTime.format(DateTimeFormatter.ofPattern("eeeyyMMdd", Locale.ENGLISH)));
                    break;
                case 1:
                    System.out.println("Alarm 모드");
                    //선택한 알람 시간 보여주는 부분
                    String temp = "-----0"+Integer.toString(currentPage+1)+"--";
                    this.setSegment1(alarmTime.format(DateTimeFormatter.ofPattern("HHmmss")));

                    if(isActivatedAlarm())
                        setSegment2("set"+temp.substring(3));
                    else
                        setSegment2("---"+temp.substring(3));

                    break;
                case 2:
                    this.setSegment1(stopwatch.getStopwatchTime().format(DateTimeFormatter.ofPattern("HHmmss")));
                    if(stopwatch.getLapTime()==LocalTime.of(0,0,0))
                        this.setSegment2("lap------");
                    else
                        this.setSegment2("lap" + stopwatch.getLapTime().format(DateTimeFormatter.ofPattern("HHmmss")));
                    System.out.println("Stopwatch 모드");
                    break;
                case 3:
                    if(isActivatedTimer) {
                        this.setSegment1(timer.getRunTime().format(DateTimeFormatter.ofPattern("HHmmss")));
                        this.setSegment2("------run");
                    }
                    else if(!isActivatedTimer) {
                        if(isChanging) {
                            this.setSegment1(this.currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
                            this.setSegment2("------set");
                        } else {
                            this.setSegment1(timer.getTimerTime());
                            this.setSegment2("-----wait");
                        }
                    }
                    System.out.println("Timer 모드");
                    break;
                case 4:
                    System.out.println("WorldTime 모드");
                    currentTime=worldTime.getWorldTime();
                    this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
                    this.setSegment2(worldTime.getUTCString());
                    break;
                case 5:
                    System.out.println("Turnip Calculator 모드");
                    if(isChanging == false) {
                        turnipValue=turnipPrice.getTurnipPrice();
                    }
                    this.setSegment1(String.format("%04d", turnipValue)+"  ");
                    break;
                default:
                    break;
            }

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
        switch (currentCursor) {
            case 0:
                this.setSegment1(this.currentTime.format(DateTimeFormatter.ofPattern("--mmss")));
                break;
            case 1:
                this.setSegment1(this.currentTime.format(DateTimeFormatter.ofPattern("HH--ss")));
                break;
            case 2:
                this.setSegment1(this.currentTime.format(DateTimeFormatter.ofPattern("HHmm--")));
                break;
            case 3:
                this.setSegment2(this.currentTime.format(DateTimeFormatter.ofPattern("eeeyyMM--", Locale.ENGLISH)));
                break;
            case 4:
                this.setSegment2(this.currentTime.format(DateTimeFormatter.ofPattern("eeeyy--dd", Locale.ENGLISH)));
                break;
            case 5:
                this.setSegment2(this.currentTime.format(DateTimeFormatter.ofPattern("eee--MMdd", Locale.ENGLISH)));
                break;
        }
    }
   


    public int reqSetting() {
        currentCursor = 0;
        isChanging = true;
        //showNextBlink
        switch (getCurrentMode()) {
            case 0:
                this.currentTime = timeKeeping.getCurrentTime();
                maxCursor = 5;
                break;
            case 1:
                alarmTime = (alarm[currentPage].getAlarmValue());
                this.currentTime = this.currentTime.withHour(0);
                this.currentTime = this.currentTime.withMinute(0);
                this.currentTime = this.currentTime.withSecond(0);
                maxCursor = 1;
                break;
            case 3:
                this.currentTime = timeKeeping.getCurrentTime();
                this.currentTime = this.currentTime.withHour(0);
                this.currentTime = this.currentTime.withMinute(0);
                this.currentTime = this.currentTime.withSecond(0);
                maxCursor = 2;
                break;
            case 4:
                System.out.println("WorldTime 모드");
                break;
            case 5:
                turnipValue = turnipPrice.getTurnipPrice();
            default:
                break;
        }

        return 0;
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

    public void changeUnitValue(int increase) {
        int value;
        currentTime.getHour();
        switch(currentCursor){
            case 0:
                value = currentTime.getHour();
                value = increase + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withHour(value);
                break;
            case 1:
                value = currentTime.getMinute();
                value = increase + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withMinute(value);
                break;
            case 2:
                value = currentTime.getSecond();
                value = increase + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withSecond(value);
                break;
            case 3:
                value = currentTime.getDayOfMonth();
                value = increase + value;
                if (value > ((currentTime.with(lastDayOfMonth())).getDayOfMonth())) value = 1;
                else if (value < 1 ) value = (currentTime.with(lastDayOfMonth())).getDayOfMonth();
                currentTime=currentTime.withDayOfMonth(value);
                break;
            case 4:
                value = currentTime.getMonthValue();
                value = increase + value;
                if (value > maxValueOfCursor[currentCursor]) value = 1;
                else if (value < 1 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withMonth(value);
                break;
            case 5:
                currentTime=currentTime.plusYears(increase);
                break;

        }
        return ;
    }

    public void changeUnitValue2(LocalTime itsTime, int increase) {

        int value;
        switch(currentCursor){
            case 0:
                value = itsTime.getHour();
                value = increase + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                alarmTime=itsTime.withHour(value);
                break;
            case 1:
                value = itsTime.getMinute();
                value = increase + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                alarmTime=itsTime.withMinute(value);
                break;
        }

        return ;
    }

    public LocalTime getAlarmTime() {
        return alarmTime;
    }

    public boolean isActivatedAlarm() {
        return alarm[currentPage].getActivated();
    }


    public void minimizeValue() {
        value = 0;
    }

    public void maximizeValue() {
        if(getCurrentMode() != 5) value = maxValueOfCursor[currentCursor];
        else value = 600;
    }

    public String reqCompleteSetting() {
        //아래도 마찬가지로 245 line 근처 changeValue() 함수 아래 주석을 보시면
        //그냥 자체 메소드 이용해서 저장한 값 저장하고, 불러오면 됨.
        switch (getCurrentMode()) {
            case 0:
                timeKeeping.setCurrentTime(this.currentTime);
                break;
            case 1:


                break;
            case 2:

                break;
            case 3:
                timer.setTimerTime(this.currentTime);
                break;
            case 4:

                break;
            case 5:
                turnipPrice.savePrice(turnipValue);
                break;
            default:
                break;
        }
        isChanging = false;

        //return값 임의로 넣음
        return "000000";
    }

    //타이머
    public void reqStartTimer() {
        isActivatedTimer = true;
        timer.startTimer(timer.getRunTime());
        return;
    }

    public void reqPauseTimer() {
        isActivatedTimer = false;
        timer.pauseTimer();
        return;
    }

    public void reqResetTimer() {
        timer.resetTimer();
        return;
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
        try {
            alarm[currentPage].activateAlarm();
        } catch (ParseException e) {}
    }

    public void reqDeactivateAlarm() {

        alarm[currentPage].deactivateAlarm();
    }
    ///    format : "set--01--";
    public void reqChangeIndicatedAlarm() {
        if (currentPage != maxAlarmPage) currentPage++;
        else currentPage = 0;
        alarmTime = alarm[currentPage].getAlarmValue();
    }

    //세계시간
    public void reqChangeWorldTime() {
        worldTime.nextWorldTime();
    }

    public void reqChangeTimeZone() {
        worldTime.changeTimeZone();
    }

    //무
    public void ChangePriceValue(int value) {
        //value = changeValue();
        turnipValue += value;

        if (turnipValue > maxTurnipValue) minimizeValue();
        else if (turnipValue < 0) maximizeValue();

    }

    public void reqResetPrice() {
        turnipPrice.resetPrice();
    }

    public void reqChangeDate() {
        turnipPrice.nextPrice(+1);
//        return Integer.toString(value);
    }

    //모드스위치
    public void reqModeSwitch() {
        modeSwitch.nextMode();
    }

    public void reqSetIndicateMode() {
        modeIndicator[0] = 1;
        for(int i=1;i<6; i++) modeIndicator[i] = 0;
        isSelectingMode = true;
        selectedMode = 0;
        currentIndicator = 1;
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
            isSelectingMode = false;
        }
    }

    public void reqUnselectMode() {
        modeIndicator[currentIndicator] = 0;
        selectedMode--;
    }

    public void reqCancelSetIndicateMode() {
        /*timeKeeping모드로돌아간다.*/
        //modeIndicator = modeSwitch.getEnabledMode();
        isSelectingMode = false;
        setCurrentMode(0);
    }

    //추가한 메소드
    public void reqNextIndicator(){
        if(currentIndicator!=5) currentIndicator++;
        else currentIndicator = 1;
    }

    //버저
    public void reqStopBeep() {
        buzzer.stopBeep();
    }


}