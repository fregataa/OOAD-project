import java.time.LocalDateTime;

public class Controller {
    private LocalDateTime timeValue;
    private Boolean[] modeIndicator = new Boolean[6];
    private String segment;
    private int currentCursor;
    private int maxCursor;
    private int[] maxValueofCursor;
    private int currentPage;
    private int maxPage;
    private int priceValue;
    private Boolean isMorning;
    private int currentMode;
    private int waitTime;

    //private Timer timer;
    private ModeSwitch modeSwitch = new ModeSwitch();
    private WorldTime worldTime = new WorldTime();
    private TimeKeeping timeKeeping = new TimeKeeping();
    private Alarm alarm = new Alarm();
    private TurnipCalculator turnipCalculator = new TurnipCalculator();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private Buzzer buzzer = new Buzzer();


    private Timer timer = new Timer();

    public void display() {

    }

    public void reqChangeTimeFormat() {

    }

    public void timeFormatClac() {

    }

    public void reqSetting() {

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

        timeValue = LocalDateTime.of(2020, 5,28,22,13);
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
