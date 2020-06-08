import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class AlarmTest {


    public Alarm alarm;

    //매 테스트 케이스마다 실행됨
    @Before
    public void setUp(){
        this.alarm = new Alarm();
    }

    //test time set and get
    @Test
    public void saveGetAlarmTime() {
        //given
        LocalTime time = LocalTime.now();

        //when
        alarm.setAlarmTime(time);

        //then
        assertThat(time, is(alarm.getAlarmTime()));
        assertThat(true, is(alarm.getActivated()));
    }

    @Test
    public void activateAlarm() {
        //when
        this.alarm.activateAlarm();
        //then
        assertThat(true, is(alarm.getActivated()));
    }

    @Test
    public void deactivateAlarm() {

        this.alarm.activateAlarm();
        this.alarm.deactivateAlarm();
        assertThat(false, is(alarm.getActivated()));
    }
}