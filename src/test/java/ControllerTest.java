import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.CombinableMatcher.either;
import static org.junit.Assert.*;

public class ControllerTest {

    Controller controller;
    LocalDateTime time;
    @Before
    public void setUp() throws Exception {
        this.controller = new Controller();
        Timer update = new Timer();
        update.scheduleAtFixedRate(this.controller, 0, 10);
    }



    @Test
    public void getTimeKeepingTime() throws InterruptedException {
        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))));
    }

    @Test
    public void setTimeKeepingTime() throws InterruptedException {
        Thread.sleep(100);
        controller.reqSetting();
        int seg;
        //시
        Thread.sleep(1000);
        seg= Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(10000)).or(is(-230000)));
        controller.changeUnitValue(-1);

        //분
        controller.nextUnit();
        Thread.sleep(500);
         seg = Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(100)).or(is(-5900)));
        controller.changeUnitValue(-1);

        //초
        controller.nextUnit();
        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(1)).or(is(-59)));
        controller.changeUnitValue(-1);

        //일
        controller.nextUnit();
        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment2().substring(7,9));
        controller.changeUnitValue(+1);

        Thread.sleep(1000);
        seg = Integer.parseInt(controller.getSegment2().substring(7,9)) - seg;
        assertThat(seg, either(is(1)).or(is(-27)).or(is(-28)).or(is(-29)).or(is(-30)));
        controller.changeUnitValue(-1);

        //월
        controller.nextUnit();
        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment2().substring(5,7));
        controller.changeUnitValue(+1);

        Thread.sleep(1000);
        seg = Integer.parseInt(controller.getSegment2().substring(5,7)) - seg;
        assertThat(seg, either(is(1)).or(is(-11)));
        controller.changeUnitValue(-1);

        //년
        controller.nextUnit();
        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment2().substring(3,5));
        controller.changeUnitValue(+1);

        Thread.sleep(1000);
        seg = Integer.parseInt(controller.getSegment2().substring(3,5)) - seg;
        assertThat(seg, is(1));
        controller.changeUnitValue(-1);
//        assertThat(controller.getSegment1(),
//                is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))));
    }

    @Test
    public void getAlarmTime() throws InterruptedException {
        this.controller.reqModeSwitch();
        Thread.sleep(10);

        assertThat(controller.getSegment1(),
                is(LocalTime.of(0,0,0).format(DateTimeFormatter.ofPattern("HHmmss"))));
    }

    @Test
    public void setAlarmPage() throws InterruptedException {
        this.controller.reqModeSwitch();
        Thread.sleep(10);
        controller.reqSetting();
        String seg;
        //시
        Thread.sleep(1000);
        controller.changeUnitValue(+1);
        controller.reqCompleteSetting();
        Thread.sleep(1000);
        seg = controller.getSegment1();
        for (int i = 0; i < 4; i++)
            controller.reqChangeIndicatedAlarm();

        Thread.sleep(1000);

        assertThat(seg,is(controller.getSegment1()));
    }
    @Test
    public void setAlarmTime() throws InterruptedException {
        Thread.sleep(100);
        this.controller.reqModeSwitch();

        int seg;
        Thread.sleep(100);
        controller.reqSetting();
        //시
        Thread.sleep(1000);
        seg= Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(10000)).or(is(-230000)));
        controller.changeUnitValue(-1);

        //분
        controller.nextUnit();
        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(100)).or(is(-5900)));
        controller.changeUnitValue(-1);

    }

    @Test
    public void getStopwatchTime() throws InterruptedException {
        for(int i = 0; i<2; i++)
            this.controller.reqModeSwitch();

        assertThat(controller.getSegment1(),
                is(LocalTime.of(0,0,0).format(DateTimeFormatter.ofPattern("HHmmss"))));
    }
    @Test
    public void startStopwatch() throws InterruptedException {
        for(int i = 0; i<2; i++)
            this.controller.reqModeSwitch();
        controller.reqStartStopWatch();
        Thread.sleep(2000);
        assertThat(controller.getSegment1(),
                is(not(LocalTime.of(0,0,0).format(DateTimeFormatter.ofPattern("HHmmss")))));
    }


    @Test
    public void getTimerTime() throws InterruptedException {
        for(int i = 0; i<3; i++)
            this.controller.reqModeSwitch();

        assertThat(controller.getSegment1(),
                is(LocalTime.of(0,0,0).format(DateTimeFormatter.ofPattern("HHmmss"))));
    }

    @Test
    public void setTimerTime() throws InterruptedException {
        for(int i = 0; i<3; i++)
            this.controller.reqModeSwitch();

        Thread.sleep(100);
        controller.reqSetting();

        int seg;

        //시
        Thread.sleep(1000);
        seg= Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(10000)).or(is(-230000)));

        //분
        controller.nextUnit();
        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(100)).or(is(-5900)));

        //초
        controller.nextUnit();
        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1());
        controller.changeUnitValue(+1);

        Thread.sleep(500);
        seg = Integer.parseInt(controller.getSegment1()) - seg;
        assertThat(seg, either(is(1)).or(is(-59)));

        controller.reqCompleteSetting();

        assertThat(10101, is(Integer.parseInt(controller.getSegment1())));

        controller.reqStartTimer();
        Thread.sleep(2000);
        assertThat(10101, is(not(Integer.parseInt(controller.getSegment1()))));

    }


    public void modeindi(){
        this.controller.reqSetIndicateMode();
        this.controller.reqNextIndicator();
        this.controller.reqNextIndicator();
        this.controller.reqSelectMode();
        this.controller.reqNextIndicator();
        this.controller.reqSelectMode();
        this.controller.reqNextIndicator();
        this.controller.reqSelectMode();
    }
    @Test
    public void modeIndicateChange() throws InterruptedException {
        this.controller.reqSetIndicateMode();
        this.controller.reqNextIndicator();
        this.controller.reqNextIndicator();
        this.controller.reqSelectMode();
        this.controller.reqNextIndicator();
        this.controller.reqSelectMode();
        this.controller.reqNextIndicator();
        this.controller.reqSelectMode();


        for(int i = 0; i<3; i++)
            this.controller.reqModeSwitch();
        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is("-000--"));
    }

    @Test
    public void getWorldTime() throws InterruptedException {
        modeindi();
        for(int i = 0; i<2; i++)
            this.controller.reqModeSwitch();

        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))));

    }
    @Test
    public void changeWorldTime() throws InterruptedException {
        modeindi();
        for(int i = 0; i<2; i++)
            this.controller.reqModeSwitch();

        controller.reqChangeWorldTime();
        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is(ZonedDateTime.now(ZoneId.of("+10")).format(DateTimeFormatter.ofPattern("HHmmss"))));

        for(int i = 0; i<24; i++)
            controller.reqChangeWorldTime();
        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))));
    }

    @Test
    public void setWorldTimeZone() throws InterruptedException {
        modeindi();
        for(int i = 0; i<2; i++)
            this.controller.reqModeSwitch();
        controller.reqChangeWorldTime();
        controller.reqChangeTimeZone();

        for(int i = 0; i<2; i++)
            this.controller.reqModeSwitch();
        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is(ZonedDateTime.now(ZoneId.of("+10")).format(DateTimeFormatter.ofPattern("HHmmss"))));

    }

    @Test
    public void setTurnip() throws InterruptedException {
        modeindi();
        for(int i = 0; i<3; i++)
            this.controller.reqModeSwitch();

        controller.reqSetting();
        controller.reqChangePriceValue(+1);

        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is("-091--"));
    }

    @Test
    public void getTurnipCalc() throws InterruptedException {
        modeindi();
        for(int i = 0; i<3; i++)
            this.controller.reqModeSwitch();

        controller.reqSetting();
        controller.reqChangePriceValue(+10);
        controller.reqCompleteSetting();
        controller.reqChangeDate();


        controller.reqSetting();
//        controller.reqChangePriceValue(+0);
        controller.reqCompleteSetting();
        controller.reqChangeDate();


        controller.reqSetting();
        controller.reqChangePriceValue(-10);
        controller.reqCompleteSetting();
        controller.reqChangeDate();


        controller.reqSetting();
        controller.reqChangePriceValue(-20);
        controller.reqCompleteSetting();
        controller.reqChangeDate();


        controller.reqSetting();
        controller.reqChangePriceValue(-30);
        controller.reqCompleteSetting();
        controller.reqChangeDate();


        controller.reqSetting();
        controller.reqChangePriceValue(-20);
        controller.reqCompleteSetting();
        controller.reqChangeDate();

        Thread.sleep(1000);
        assertThat(controller.getSegment1(),
                is(not("-000--")));
    }

}