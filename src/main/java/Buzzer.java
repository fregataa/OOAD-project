import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

import static java.lang.Thread.sleep;

public class Buzzer {
    static Clip clip;
    static AudioInputStream beepSound;

    static public boolean getIsBeeping() {
        return clip.isActive();
    }

    Buzzer() {
        setBeep();
    }

    static public void reqBeep() {
        if(!clip.isActive()) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    static public void stopBeep() {
        clip.stop();
        clip.setFramePosition(0);
    }

    static public void setBeep() {
        try {
            beepSound = AudioSystem.getAudioInputStream(new File("beep.wav"));
            clip = AudioSystem.getClip();
            clip.open(beepSound);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
