import java.time.LocalDateTime;

public class Controller {
    private LocalDateTime timeValue;
    //private Timer timer;
    private Timer timer = new Timer();

    public void reqStartTimer() {
        //원래는 설정하는 타이머시간

        timeValue = LocalDateTime.of(2020, 5,28,22,13);
        timer.startTimer(timeValue);
    }

    public void reqPauseTimer() {
        timer.pauseTimer();
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
