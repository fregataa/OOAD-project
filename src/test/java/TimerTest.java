import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TimerTest {

    public Timer timer;
    LocalTime time;
    //매 테스트 케이스마다 실행됨
    @Before
    public void setUp(){
        this.timer = new Timer();
    }

    @Test
    public void getTimerTime() {
        time=LocalTime.of(0,0,0);
        assertThat(time, is(timer.getTimerTime()));
    }

    @Test
    public void setTimerTime() {
        //given
        time=LocalTime.of(0,10,0);
        //when
        timer.setTimerTime(time);
        //then
        assertThat(time, is(timer.getTimerTime()));
    }

    @Test
    public void getRunTime() {
        //given
        time=LocalTime.of(0,10,0);
        //when
        timer.setTimerTime(time);
        //then
        assertThat(time, is(timer.getRunTime()));
    }

    @Test
    public void getIsStartedTimer() {
        //given
        time=LocalTime.of(0,10,0);
        //when
        timer.setTimerTime(time);
        timer.startTimer(time);
        //then
        assertThat(true, is(timer.getIsStartedTimer()));
    }

    @Test
    public void startTimer() throws InterruptedException {
        //given
        time=LocalTime.of(0,10,0);
        //when
        timer.setTimerTime(time);
        timer.startTimer(timer.getTimerTime());
        Thread.sleep(1000);
        //then
        assertThat(time, is(not(timer.getRunTime())));
    }

    @Test
    public void pauseTimer() throws InterruptedException {
        //given
        time=LocalTime.of(0,10,0);
        //when
        timer.setTimerTime(time);
        timer.startTimer(time);
        Thread.sleep(2000);

        timer.pauseTimer();
        time=timer.getRunTime();
        Thread.sleep(2000);
        //then
        assertThat(time, is(timer.getRunTime()));
    }

    @Test
    public void resetTimer() throws InterruptedException {
        //given
        time=LocalTime.of(0,10,0);
        //when
        timer.setTimerTime(time);
        timer.startTimer(time);
        Thread.sleep(2000);
        timer.pauseTimer();
        timer.resetTimer();

        Thread.sleep(500);
        //then
        assertThat(time, is(timer.getRunTime()));
    }

}