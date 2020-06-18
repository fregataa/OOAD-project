import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

import static java.lang.Thread.sleep;

public class Buzzer {
    private static Clip clip;

    public static boolean getIsBeeping() {
        return clip.isActive();
    }

    public static void reqBeep() {
        if(!clip.isActive()) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void stopBeep() {
        clip.stop();
        clip.setFramePosition(0);
    }

    public static void setBeep() {
        AudioInputStream beepSound;
        try {
            beepSound = AudioSystem.getAudioInputStream(new File("beep.wav"));
            clip = AudioSystem.getClip();
            clip.open(beepSound);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
