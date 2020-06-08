import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
public class StopwatchTest {

    //매 테스트 케이스마다 실행됨
    Stopwatch stopwatch;
    LocalTime time;
    @Before
    public void setUp(){
        this.stopwatch = new Stopwatch();
    }

    @Test
    public void getStopwatchTime() {
        time=LocalTime.of(0,0,0);
        assertThat(time, is(stopwatch.getStopwatchTime()));
    }

    @Test
    public void getLapTime() throws InterruptedException {
        time=LocalTime.of(0,0,1);
        stopwatch.startStopwatch();

        Thread.sleep(1000);
        stopwatch.lapTime();
        assertThat(time.getSecond(), is(stopwatch.getLapTime().getSecond()));
    }

    @Test
    public void getIsStartedStopwatch() {
        stopwatch.startStopwatch();

        assertThat(true, is(stopwatch.getIsStartedStopwatch()));
    }

    @Test
    public void startStopwatch() throws InterruptedException {
        time=LocalTime.of(0,0,0);
        stopwatch.startStopwatch();
        Thread.sleep(1000);
        assertThat(true, is(time.isBefore(stopwatch.getStopwatchTime())));

    }

    @Test
    public void pauseStopwatch() throws InterruptedException {
        time=LocalTime.of(0,0,0);
        stopwatch.startStopwatch();

        Thread.sleep(1000);
        time=stopwatch.getStopwatchTime();

        stopwatch.pauseStopwatch();

        assertThat(time.getSecond(), is(stopwatch.getStopwatchTime().getSecond()));

    }

    @Test
    public void resetStopwatch() throws InterruptedException {
        time=LocalTime.of(0,0,0);
        stopwatch.startStopwatch();

        Thread.sleep(1000);

        assertThat(time, is(not(stopwatch.getStopwatchTime())));
        stopwatch.resetStopwatch();
        assertThat(time, is(stopwatch.getStopwatchTime()));
    }

}