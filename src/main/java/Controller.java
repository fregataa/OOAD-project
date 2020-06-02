import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

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
    private int[] modeIndicator = new int[]{1,1,1,1,0,0};
    private int currentMode;
    private String segment1 = "000000";
    private String segment2 = "set--01--";
    private Boolean is24;  //isMorning->is24
    /*---------------------*/

    private int currentCursor;
    private static int maxCursor = 5;
    private static int[] maxValueOfCursor = {23, 60, 60, 31, 12, 2030};
    private int currentPage;
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
    //for(int i=0; i<4; i++){ alarm[i] = new Alarm(); }
    private Stopwatch stopwatch = new Stopwatch();
    private Timer timer = new Timer();
    private WorldTime worldTime = new WorldTime();
    private TurnipCalc turnipCalculator = new TurnipCalc();
    private TurnipPrice turnipPrice = new TurnipPrice();
    private ModeSwitch modeSwitch = new ModeSwitch();
    private Buzzer buzzer = new Buzzer();



    /*
    * 아래 get메소드들은 대부분 UI에서 부르는 용도로 사용함.
    * set메소드들은 controller나 이하 Object에서 set으로 설정하면 UI쪽에서 설정한 것을 UI가 get으로 처리함.
    * UI쪽에서 변경되는 것은 현재 모드(currentMode), 전체모드(Modeindicator), 패널 1-2줄(segment1/2), AM/PM(is24)임.
    * is24는 처리가 좀 다르니 GUI쪽 코드 참고하면 좋을 것 같음.
    */
    ///////////////////////////////////////////////////////
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

    public Boolean getIs24() {
        return is24;
    }

    public void setIs24(Boolean is24) {
        this.is24 = is24;
    }
    ///////////////////////////////////////////////////////


    Controller() {
        modeSwitch.initialize();
        setCurrentMode(0);
        is24 = true;
    }

    @Override
    public void run() {
        //mode Indicator, isMorning, segment 를 UI에 전달
        //TimerTask 에 의해 GUI 쪽에서 계속 불림.
        GUI.getGUIInstance().invalidate();
        GUI.getGUIInstance().repaint();
        switch (currentMode) {
            case 0:
                System.out.println("TimeKeeping 모드");
                currentTime = ZonedDateTime.now();
                if(is24 == true) this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("hhmmss")));
                else this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
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


    //수정필요
    //public void showNextBlink() { currentCursor++; }


    // 현재 커서 위치와 시간값을 어떻게 동시에 보내면 좋을지?
    //=> 그냥 set 메소드만들어서 gui 쪽에서 부르면 됨.
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
                //아래와 같이 currentTime.format(DateTimeFormatter.ofPattern("HHmmss"))사용하면 됨.(String 전환)
                this.setSegment1(currentTime.format(DateTimeFormatter.ofPattern("HHmmss")));
            break;
            case 1:
                alarmTime = (alarm[currentPage].getAlarmValue()).toLocalTime();
                hour = alarmTime.getHour();
                min = alarmTime.getMinute();
                sec = alarmTime.getSecond();
                return currentCursor;
            case 3:
                //불러오지 않아도 됨
                //그냥 int형으로 만들어서 추가될때마다 ++해주면 됨
                //그리고 그걸 string으로 timer나 다른 곳에 전달.
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

    public int changeUnitValue() {
        /*
          아래 주석 처리한 이유는 controller에서의 changevalue함수는
          GUI 쪽에서 C/D 버튼에서 동시에 불려야하기 때문에 인자로 int button을 줄 수 밖에 없었음.
          changeUpValue()/changeDownValue()를 따로 만들지 않는 한 changevalue함수는 리턴값을 활용하기 힘듦.
        */
        //value = changeValue();
        if (value > maxValueOfCursor[currentCursor]) minimizeValue();
        else if (value < 0) maximizeValue();
        return value;
    }

    public int changeValue(int button) {
        /*
        * 아래는 굳이 삭제하지 않았지만 zonedTime 관련 메소드를 찾아보시면
        * plusYear/ plusMonth 등 저장된 Time 에 대한 요소를 자동으로 더해주는 메소드가 있음.
        * 따라서 int 형으로 선언하지 않아도 값 변경이 가능하며, 윤년과 월말에 대한 계산도 알아서 됨.
        */

        //임의로 0을 up/ 1을 down 이라고
        if (button == 0) {
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
        } else if (button == 1) {
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

        //return값 임의로 넣음.
        return 0;
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
        //아래도 마찬가지로 245 line 근처 changeValue() 함수 아래 주석을 보시면
        //그냥 자체 메소드 이용해서 저장한 값 저장하고, 불러오면 됨.
        /*
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
        */

        //return값 임의로 넣음
        return "000000";
    }

    //여기부터 다시 수정
    //타이머
    public void reqStartTimer() {
        timer.startTimer(currentTime.toLocalTime());
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
        //return alarm[alarmPage].getAlarmValue();
    }

    //세계시간
    public void reqChangeWorldTime() {
        worldTime.nextWorldTime(currentPage);
    }

    public void reqChangeTimeZone() {
        worldTime.changeTimeZone(currentPage);
    }

    //무
    public int ChangePriceValue() {
        //value = changeValue();
        if (value > maxTurnipValue) minimizeValue();
        else if (value < 0) maximizeValue();
        return value;
    }

    public void reqResetPrice() {
        turnipPrice.resetPrice();
    }

    public String reqChangeDate() {
        value = turnipPrice.nextPrice(currentPage);
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


}