import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimerTask;
import static java.time.temporal.TemporalAdjusters.*;

public class Controller extends TimerTask {
    private ZonedDateTime currentTime;
    private String segment1 = "000000";
    private String segment2 = "000000000";
    private int[] modeIndicator;
    private int[] changeModeIndicator;
    private boolean is24;
    private boolean isChanging;
    private boolean isActivatedTimer;
    private boolean isSelectingMode;
    private int currentCursor;
    private int currentIndicator;
    private int currentPage;
    private int maxCursor;
    private int[] maxValueOfCursor = {23, 59, 59, 31, 12, 2030};
    private int turnipValue;
    private int selectedMode;

    private TimeKeeping timeKeeping = TimeKeeping.getInstance();
    private Alarm[] alarm = new Alarm[4];
    private Stopwatch stopwatch = new Stopwatch();
    private Timer timer = new Timer();
    private WorldTime worldTime = new WorldTime();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private ModeSwitch modeSwitch = new ModeSwitch();
    private Buzzer buzzer = new Buzzer();
    private Timeout timeout = new Timeout();

    ///////////////////////////////////////////////////////

    public Timeout getTimeout(){
        return this.timeout;
    }

    public int getCurrentMode() {
        return this.modeSwitch.getMode();
    }

    public int getCurrentCursor() {
        return this.currentCursor;
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

    public boolean getIs24() {
        return is24;
    }

    public boolean getChanging() {
        return this.isChanging;
    }

    public boolean getIsActivatedTimer() {
        return this.isActivatedTimer;
    }

    public boolean getIsActivatedAlarm() { return alarm[currentPage].getActivated(); }

    public boolean getIsSelectingMode(){
        return this.isSelectingMode;
    }

    public int getCurrentIndicator() {
        return this.currentIndicator;
    }

    public boolean getIsStartedStopwatch() {
        return stopwatch.getIsStartedStopwatch();
    }

    public boolean getIsBeeping() {
        return buzzer.getIsBeeping();
    }

    ///////////////////////////////////////////////////////


    Controller() {
        for(int i=0; i<4; i++){ alarm[i] = new Alarm(); }
        modeSwitch.initialize();
        isChanging = false;
        isActivatedTimer = false;
        is24 = true;
        currentPage = 0;
        modeIndicator=modeSwitch.getEnabledMode();
    }

    @Override
    public void run() {

        GUI.getGUIInstance().invalidate();
        GUI.getGUIInstance().repaint();

        if(timeout.getWaitTime().toSecondOfDay() > 60){
            modeSwitch.initialize();
            timeout.setWaitTime(LocalTime.of(0,0,0));
            this.reqCancelSetIndicateMode();
            isChanging = false;
        }

        if(isSelectingMode){
           this.setSegment1("------");
           this.setSegment2("Modchange");
        }
        else{
            switch (getCurrentMode()) {
            case 0:
                if(!isChanging) currentTime = timeKeeping.getCurrentTime();
                this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
                this.setSegment2(currentTime.format(DateTimeFormatter.ofPattern("eeeyyMMdd", Locale.ENGLISH)));
                break;
            case 1:
                String temp = "-----0"+(currentPage+1)+"--";
                if(!isChanging) this.currentTime = ZonedDateTime.of(timeKeeping.getCurrentTime().toLocalDate(), alarm[currentPage].getAlarmTime(), timeKeeping.getCurrentTime().getZone());
                this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
                if(getIsActivatedAlarm()) setSegment2("set"+temp.substring(3));
                else setSegment2("---"+temp.substring(3));
                break;
            case 2:
                this.setSegment1(stopwatch.getStopwatchTime().format(DateTimeFormatter.ofPattern("mmssSS")));
                if(stopwatch.getLapTime()==LocalTime.of(0,0,0)) this.setSegment2("lap------");
                else this.setSegment2("lap" + stopwatch.getLapTime().format(DateTimeFormatter.ofPattern("mmssSS")));
                break;
            case 3:
                if((isActivatedTimer=timer.getIsStartedTimer())) {
                    this.setSegment1(timer.getRunTime().format(DateTimeFormatter.ofPattern("HHmmss")));
                    this.setSegment2("------run");
                }
                else {
                    if(isChanging) {
                        this.setSegment1(this.currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
                        this.setSegment2("------set");
                    } else {
                        this.setSegment1(timer.getRunTime().format(DateTimeFormatter.ofPattern("HHmmss")));
                        this.setSegment2("-----wait");
                    }
                }
                break;
            case 4:
                currentTime=worldTime.getWorldTime();
                this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
                this.setSegment2(worldTime.getUTCString());
                break;
            case 5:
                if(!isChanging) turnipValue=turnipPrice.getTurnipPrice();
                else {
                    setSegment1("------");
                }
                this.setSegment1("-" + String.format("%03d", turnipValue)+"--");
                this.setSegment2(turnipPrice.getTurnipDay());
                break;
            default: break;
            }
        }
    }

    public void reqChangeTimeFormat() {
        is24 = !is24;
    }

    public void reqSetting() {
        LocalDate nowLocalDate = timeKeeping.getCurrentTime().toLocalDate();
        ZoneId nowZoneId = timeKeeping.getCurrentTime().getZone();
        currentCursor = 0;
        isChanging = true;

        switch (getCurrentMode()) {
            case 0:
                this.currentTime = timeKeeping.getCurrentTime();
                maxCursor = 5;
                break;
            case 1:
                this.currentTime = ZonedDateTime.of(nowLocalDate, alarm[currentPage].getAlarmTime(), nowZoneId);
                maxCursor = 1;
                break;
            case 3:
                this.currentTime = ZonedDateTime.of(nowLocalDate, timer.getTimerTime(), nowZoneId);
                maxCursor = 2;
                break;
            case 5:
                turnipValue = turnipPrice.getTurnipPrice();
                if(turnipValue == 0) turnipValue = 90;
                break;
            default: break;
        }
    }


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


    public void changeUnitValue(int changeValue) {
        int value;
        switch(currentCursor){
            case 0:
                value = currentTime.getHour();
                value = changeValue + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withHour(value);
                break;
            case 1:
                value = currentTime.getMinute();
                value = changeValue + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withMinute(value);
                break;
            case 2:
                value = currentTime.getSecond();
                value = changeValue + value;
                if (value > maxValueOfCursor[currentCursor]) value = 0;
                else if (value < 0 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withSecond(value);
                break;
            case 3:
                value = currentTime.getDayOfMonth();
                value = changeValue + value;
                if (value > ((currentTime.with(lastDayOfMonth())).getDayOfMonth())) value = 1;
                else if (value < 1 ) value = (currentTime.with(lastDayOfMonth())).getDayOfMonth();
                currentTime=currentTime.withDayOfMonth(value);
                break;
            case 4:
                value = currentTime.getMonthValue();
                value = changeValue + value;
                if (value > maxValueOfCursor[currentCursor]) value = 1;
                else if (value < 1 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withMonth(value);
                break;
            case 5:
                value = currentTime.getYear();
                value = changeValue + value;
                if (value > maxValueOfCursor[currentCursor]) value = 1990;
                else if (value < 1990 ) value = maxValueOfCursor[currentCursor];
                currentTime=currentTime.withYear(value);
                break;
            default: break;
        }
    }

    public void reqCompleteSetting() {
        isChanging = false;
        switch (getCurrentMode()) {
            case 0:
                timeKeeping.setTime(this.currentTime);
                break;
            case 1:
                alarm[currentPage].setAlarmTime(currentTime.toLocalTime());
                break;
            case 3:
                timer.setTimerTime(this.currentTime.toLocalTime());
                break;
            case 5:
                turnipPrice.setTurnipPrice(turnipValue);
                break;
            default: break;
        }
    }

    public void reqStartTimer() {
        if(!timer.getRunTime().equals(LocalTime.of(0,0,0))) {
            timer.startTimer(timer.getRunTime());
            isActivatedTimer = timer.getIsStartedTimer();
        }
    }

    public void reqPauseTimer() {
        timer.pauseTimer();
        isActivatedTimer = timer.getIsStartedTimer();
    }

    public void reqResetTimer() {
        timer.resetTimer();
    }

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

    public void reqActivateAlarm() {
            alarm[currentPage].activateAlarm();
    }

    public void reqDeactivateAlarm() {
        alarm[currentPage].deactivateAlarm();
    }

    public void reqChangeIndicatedAlarm() {
        currentPage = (currentPage+1)%4;
        this.currentTime = ZonedDateTime.of(timeKeeping.getCurrentTime().toLocalDate(), alarm[currentPage].getAlarmTime(), timeKeeping.getCurrentTime().getZone());
    }

    public void reqChangeWorldTime() {
        worldTime.nextWorldTime();
    }

    public void reqChangeTimeZone() {
        worldTime.changeTimeZone();
    }

    public void reqChangePriceValue(int changeValue) {
        int maxTurnipValue = 600;
        int minTurnipValue = 0;

        turnipValue += changeValue;
        if (turnipValue > maxTurnipValue) turnipValue = minTurnipValue;
        else if (turnipValue < minTurnipValue) turnipValue = maxTurnipValue;
    }

    public void reqResetPrice() {
        turnipPrice.resetPrice();
    }

    public void reqChangeDate() {
        turnipPrice.nextPrice();
    }

    public void reqModeSwitch() {
        modeSwitch.nextMode();
    }

    public void reqSetIndicateMode() {
        changeModeIndicator = new int[]{1,0,0,0,0,0};
        isSelectingMode = true;
        selectedMode = 0;
        currentIndicator = 1;
        this.setModeIndicator(changeModeIndicator);
    }

    public void reqNextIndicator(){
        if(currentIndicator!=5) currentIndicator++;
        else currentIndicator = 1;
    }

    public void reqSelectMode() {
        if (selectedMode < 2) {
            changeModeIndicator[currentIndicator] = 1;
            selectedMode++;
            this.setModeIndicator(changeModeIndicator);
        } else if (selectedMode == 2) {
            changeModeIndicator[currentIndicator] = 1;
            modeSwitch.setMode(changeModeIndicator);
            selectedMode = 0;
            isSelectingMode = false;
        }
    }

    public void reqUnselectMode() {
        changeModeIndicator[currentIndicator] = 0;
        selectedMode--;
        this.setModeIndicator(changeModeIndicator);
    }

    public void reqCancelSetIndicateMode() {
        this.setModeIndicator(modeIndicator);
        isSelectingMode = false;
        modeSwitch.initialize();
        this.modeIndicator = modeSwitch.getEnabledMode();
    }

    public void reqStopBeep() {
        buzzer.setIsBeeping(false);
        buzzer.stopBeep();
    }
}