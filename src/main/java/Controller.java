import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Controller {
    private LocalDateTime timeValue;


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

    //private Timer timer;
    private ModeSwitch modeSwitch = new ModeSwitch();
    private WorldTime worldTime = new WorldTime();
    private TimeKeeping timeKeeping = new TimeKeeping();
    private Alarm alarm = new Alarm();
    private TurnipCalc turnipCalculator = new TurnipCalc();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private Buzzer buzzer = new Buzzer();
    private Timer timer = new Timer();

    public int setCurrentMode() {
        return this.currentMode;
    }

    public void getCurrentMode(int currentMode) {
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
        timeFormatClac();
    }

    public void timeFormatClac() {
        isMorning = false;
        //둘이 바꿔주기
    }

    public void reqSetting() {
        //timeValue = timeKeeping.getCurrentTime();
        //showNextBlink
    }

    public void nextUnit() {

    }

    public void increaseUnit() {

    }

    public void initUnit() {

    }

    public void changeUnitValue() {

    }

    public void changeValue() {

    }

    public void minimizeValue() {

    }

    public void maximizeValue() {

    }

    public void reqCompleteSetting() {

    }

    public void reqStartTimer() {
        //원래는 설정하는 타이머시간
        timeValue = LocalDateTime.of(2020, 5,30,18,22);
        timer.startTimer(timeValue);
    }

    public void reqPauseTimer() {
        timer.pauseTimer();
    }

    public void reqResetTimer() {

    }

    public void reqStartStopwatch() {

    }

    public void reqPauseStopwatch() {

    }

    public void reqResetStopwatch() {

    }

    public void reqLapTime() {

    }

    public void reqActivateAlarm() {

    }

    public void reqDeactivateAlarm() {

    }

    public void reqChangeIndicatedAlarm() {

    }

    public void reqChangeTimeZone() {

    }

    public void reqInputPrice() {

    }

    public void ChangePriceValue() {

    }

    public void reqResetPrice() {

    }

    public void reqChangeDate() {

    }

    public void reqModeSwitch() {

    }

    public void reqSetIndicateMode() {

    }

    public void reqSelectMode() {

    }

    public void reqUnselectMode() {

    }

    public void reqCancelSetIndicateMode() {

    }

    public void reqStopBeep() {

    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.reqStartTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqPauseTimer();
        for (int i = 0; i < 10000; i++) { }
        controller.reqStartTimer();
    }

}

