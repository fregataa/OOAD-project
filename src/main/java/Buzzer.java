import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

import static java.lang.Thread.sleep;

public class Buzzer {
    static boolean isBeeping;
    static Clip clip;
    static AudioInputStream beepSound;

    public boolean getIsBeeping() {
        return isBeeping;
    }

    public void setIsBeeping(boolean isBeeping) {
        this.isBeeping = isBeeping;
    }

    Buzzer() {
        setBeep();
    }

    public void reqBeep() {
        if(!isBeeping) {
            isBeeping = true;
            clip.start();
            try {
                sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            };
            clip.stop();
            setBeep();
        }
    }


    public void stopBeep() {
        clip.stop();
        setBeep();
    }

    public void setBeep() {
        isBeeping = false;
        try {
            this.beepSound = AudioSystem.getAudioInputStream(new File("beep.wav"));
            this.clip = AudioSystem.getClip();
            this.clip.open(beepSound);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
