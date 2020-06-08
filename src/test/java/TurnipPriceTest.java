import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TurnipPriceTest {
    public TurnipPrice turnipPrice;

    //매 테스트 케이스마다 실행됨
    @Before
    public void setUp(){
        this.turnipPrice = new TurnipPrice();
    }

    @Test
    public void getTurnipPrice() {
        assertThat(this.turnipPrice.getTurnipPrice(),is(0));

    }

    @Test
    public void getTurnipDay() {

        assertThat(this.turnipPrice.getTurnipDay(),isA(String.class));

    }

    @Test
    public void savePrice() {

        this.turnipPrice.setTurnipPrice(100);

        assertThat(this.turnipPrice.getTurnipPrice(),is(100));
    }

    @Test
    public void resetPrice() {
        //given
        this.turnipPrice.setTurnipPrice(100);
        //when
        this.turnipPrice.resetPrice();
        //then
        assertThat(this.turnipPrice.getTurnipPrice(),is(0));
    }

    @Test
    public void nextPrice() {
        //given
        this.turnipPrice.setTurnipPrice(100);
        //when
        for(int i = 13;i<13;i++){
            this.turnipPrice.nextPrice();
        }
        //then
        assertThat(this.turnipPrice.getTurnipPrice(),is(100));
    }
}