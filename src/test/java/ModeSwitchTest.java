import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ModeSwitchTest {

    ModeSwitch modeSwitch;
    @Before
    public void setUp() throws Exception {
        this.modeSwitch = new  ModeSwitch();
    }
    @Test
    public void getMode() {

        assertThat(modeSwitch.getMode(),is(0));
        modeSwitch.nextMode();
        assertThat(modeSwitch.getMode(),is(1));

    }

    @Test
    public void getEnabledMode() {

        int enMod=0;
        int[] en = modeSwitch.getEnabledMode();
        for(int i = 0; i<6; i++){
            if (en[i]==1) enMod += 1;
        }
        assertThat(enMod, is(4));
    }

    @Test
    public void initialize() {
        modeSwitch.nextMode();

        modeSwitch.nextMode();
        //when
        modeSwitch.initialize();
        assertThat(modeSwitch.getMode(),is(0));
    }

    @Test
    public void nextMode() {
        modeSwitch.initialize();

        for (int i = 0; i<10;i++){
            modeSwitch.nextMode();
        }
        assertThat(true, is(this.modeSwitch.getMode()<6));
    }

    @Test
    public void setMode() {
        int[] enabledMode= new int[]{0, 0, 1, 1, 1, 1};
        modeSwitch.setMode(enabledMode);
        assertThat(enabledMode,is(modeSwitch.getEnabledMode()));
    }
}