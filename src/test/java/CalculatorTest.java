import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void main() {
        assertEquals(0, 0);
    }

    @Test
    public void add() {
        Calculator c = new Calculator();
        int r = c.add();
        assertEquals(6, r);     // answer 6
    }

    @Test
    public void minus(){
        Calculator c = new Calculator();
        int r = c.minus();
        assertEquals(3, r);     // answer 3
    }
}