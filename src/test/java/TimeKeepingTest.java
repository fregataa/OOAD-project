
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TimeKeepingTest {

    public TimeKeeping timeKeeping;

    //매 테스트 케이스마다 실행됨
    @Before
    public void setUp(){
        this.timeKeeping = TimeKeeping.getInstance();
    }

    @Test
    public void getCurrentTime() {
        // given
        //when
        this.timeKeeping.setTimeZone(ZoneId.of("+9"));
        //then
        assertThat(ZonedDateTime.now().toLocalDateTime(), is(this.timeKeeping.getCurrentTime().toLocalDateTime()));

    }
    @Test
    public void setTimeZone() {
        // given

        for(int i = -12; i <= 12; i++) {
            // when
            ZoneId id = ZoneId.of(i<0?Integer.toString(i):"+"+i);
            this.timeKeeping.setTimeZone(id);
            ZoneId result = this.timeKeeping.getCurrentTime().getZone();

            //then

            assertThat(result, is(id));
        }
    }
}
