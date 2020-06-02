import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

import static java.lang.Thread.sleep;


public class Buzzer {
    private Boolean isActivated;
    Clip clip;
    AudioInputStream beep;

    public Buzzer() {
        try {
            this.beep = AudioSystem.getAudioInputStream(new File("beep.wav"));
            this.clip = AudioSystem.getClip();
            this.clip.open(beep);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reqBeep() {
        clip.start();
        try {
            sleep(5000);
        } catch (Exception e){e.printStackTrace();};
        clip.stop();
    }

    public void stopBeep() {
        clip.stop();
    }
}
