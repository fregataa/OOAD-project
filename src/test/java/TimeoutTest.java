import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeoutTest {
    Timeout timeout;
    //매 테스트 케이스마다 실행됨
    @Before
    public void setUp(){
        this.timeout = new Timeout();
    }
}