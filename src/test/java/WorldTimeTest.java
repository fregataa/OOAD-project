import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class WorldTimeTest {

    public TimeKeeping timeKeeping;
    public WorldTime worldTime;

    //매 테스트 케이스마다 실행됨
    @Before
    public void setUp(){
        this.worldTime = new WorldTime();
        this.timeKeeping = TimeKeeping.getInstance();
    }

    @Test
    public void getWorldTime() {
        assertThat(ZonedDateTime.now().toLocalDateTime().withNano(0), is(this.worldTime.getWorldTime().toLocalDateTime().withNano(0)));
    }

    @Test
    public void getUTCString() {
        assertThat(worldTime.getUTCString(),containsString("UTC")  );
    }

    @Test
    public void nextWorldTime() {
        ZonedDateTime time;
        time = worldTime.getWorldTime();

        worldTime.nextWorldTime();
        assertThat(time,is(not(worldTime.getWorldTime())));
    }

    @Test
    public void changeTimeZone() {
        ZonedDateTime time;
        worldTime.nextWorldTime();
        worldTime.nextWorldTime();

        time = worldTime.getWorldTime();
        worldTime.changeTimeZone();

        assertThat(time.getZone(),is(timeKeeping.getCurrentTime().getZone()));

    }
}