import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class TurnipPrice {
    private LocalDateTime now;
    private int currentPage = 0;
    private int inputtedPrice = 0;
    private int[] price = new int[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0 };
    private boolean[] isInputted = new boolean[13];
    private boolean isSetHighestDay = false;
    private TimeKeeping timeKeeping = TimeKeeping.getInstance();
    private TimerTask timerTask;
    private Timer turnipThread;
    private TurnipCalc turnipCalc;
    private Buzzer buzzer;

    TurnipPrice() {
        this.turnipThread = new Timer();
        turnipCalc = new TurnipCalc();
        this.buzzer = new Buzzer();
    }

    public int getTurnipPrice() {
        return price[currentPage];
    }

    public void setTurnipPrice(int priceValue){ //무 값을 저장하고 인덱스 1 증가
        price[currentPage] = priceValue;
        isInputted[currentPage] = true;
        inputtedPrice++;

        if(inputtedPrice>=5) {
            System.out.println(Arrays.toString(turnipCalc.calcPrice(price, isInputted)));
            price = turnipCalc.calcPrice(price,isInputted); //여기에서 calcPrice호출
            setHighestDay();
        }
    }

    public String getTurnipDay() {
        String dayOfWeek[] = new String[]{"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
        String timeOfDay[] = new String[]{"am-", "pm-"};
        String whatDayOfWeek = timeOfDay[currentPage%2] + dayOfWeek[currentPage/2];

        if(price[currentPage] == 0){
            return whatDayOfWeek + "---";
        }
        if(isInputted[currentPage]){
            return whatDayOfWeek + "-in";
        }else{
            return whatDayOfWeek + "est";
        }
    }

    public void resetPrice() {//무값 초기화
        price = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0 };
        inputtedPrice = 0;
        currentPage = 0;
        isInputted = new boolean[13];
        if(isSetHighestDay) this.deactivateAlarm();
    }

    //0보다 크면 다음 페이지, 작으면 이전 페이지
    public void nextPrice() {
        currentPage = (currentPage+1)%13;
    }

    private void setHighestDay(){
        int maxDay = 0;
        int maxPrice = 0;
        isSetHighestDay = true;

        for(int i = (timeKeeping.getCurrentTime().getDayOfWeek().getValue()%7 ) * 2; i<13; i++){
            if(price[i] > maxPrice){
                maxDay = i;
                maxPrice = price[i];
            }
        }
        if (maxDay == 0) return;
        int finalMaxDay = maxDay;

        timerTask = new TimerTask() {
            @Override
            public void run() {
                now = timeKeeping.getCurrentTime().toLocalDateTime();
                if (finalMaxDay % 2 == 0) {
                    if (12 == now.getHour() && (finalMaxDay + 1) / 2 == (now.getDayOfWeek().getValue()%7) && 0 == now.getMinute() && 0 == now.getSecond()) {
                        buzzer.reqBeep();
                    }
                } else {
                    if (9 == now.getHour() && (finalMaxDay + 1) / 2 == (now.getDayOfWeek().getValue()%7)  && 0 == now.getMinute() && 0 == now.getSecond()) {
                        buzzer.reqBeep();
                    }
                }
            }
        };

        turnipThread.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void deactivateAlarm(){
        //Buzzer 등록된 타이머를 제거
        timerTask.cancel();
        buzzer.stopBeep();
    }
}
