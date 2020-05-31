import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Controller {
    private ZonedDateTime timeValue;
    /*
    <modeIndicator index>
    * 0 - TimeKeeping
    * 1 - Alarm
    * 2 - Stopwatch
    * 3 - Timer
    * 4 - WorldTime
    * 5 - Turnip Calculator
    */
    private Boolean[] modeIndicator = new Boolean[6];
    private int currentMode;
    private String segment;
    private int currentCursor;
    private int maxCursor;
    private int[] maxValueofCursor;
    private int currentPage;
    private int maxPage;
    private int priceValue;
    // 24 - true, 12 - false
    private Boolean isMorning;
    private int waitTime;

    private int value;
    private int [] maxValue;

    private TimeKeeping timeKeeping = new TimeKeeping();
    private Alarm [] alarm = new Alarm[4];
    for(int i=0; i<4; i++){ alarm[i] = new Alarm(); }
    private StopWatch stopWatch = new StopWatch();
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


    /* <modeIndicator index>
     * 0 - TimeKeeping
     * 1 - Alarm
     * 2 - Stopwatch
     * 3 - Timer
     * 4 - WorldTime
     * 5 - Turnip Calculator
     */
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
        if(isMorning==true) isMorning = false;
        else isMorning = true;
    }

    public int reqSetting() {
        //timeValue = timeKeeping.getCurrentTime();
        //showNextBlink
        switch (currentMode) {
            case 0:
                timeValue = timeKeeping.getCurrentTime();
                return currentCursor;
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

    public void nextUnit() {
        if(currentCursor!=maxCursor) cotroller.increaseUnit();
        else controller.initUnit();
    }
    public void increaseUnit() { currentCursor++; }
    public void initUnit() { currentCursor = 0; }
    public void changeUnitValue() { }
    public void changeValue() {
        if(/*증가버튼*/) value++;
        else if(/*감소버튼*/)value--;
    }
    public void minimizeValue() { value=0; }
    public void maximizeValue() { /*currentCursor에따라 maxValue값이 다르다.*/ }
    public void reqCompleteSetting() {
        switch (currentMode) {
            case 0:
                timer.saveTime(ZonedDateTime timeValue);
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
    public void reqStartTimer() {
        //원래는 설정하는 타이머시간
        timeValue = LocalDateTime.of(2020, 5,30,18,22);
        timer.startTimer(timeValue);
    }
    public void reqPauseTimer() { timer.pauseTimer(); }
    public void reqResetTimer() { timer.resetTimer(); }
    public void reqStartStopwatch() { stopWatch.startStopWatch();}
    public void reqPauseStopwatch() { stopWatch.pauseStopWatch();}
    public void reqResetStopwatch() { stopWatch.resetStopwatch();}
    public void reqLapTime() { stopWatch.reqLapTime();}
    public void reqActivateAlarm() { alarm.activateAlarm();}
    public void reqDeactivateAlarm() { alarm.deactivateAlarm(); }
    public void reqChangeIndicatedAlarm() {    }
    public void reqChangeTimeZone() {    }
    public void reqInputPrice() {    }
    public void ChangePriceValue() {    }
    public void reqResetPrice() {    }
    public void reqChangeDate() {    }
    public void reqModeSwitch() {    }
    public void reqSetIndicateMode() {    }
    public void reqSelectMode() {    }
    public void reqUnselectMode() {    }
    public void reqCancelSetIndicateMode() {    }
    public void reqStopBeep() {  }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.reqStartTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqPauseTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqStartTimer();
    }

}

