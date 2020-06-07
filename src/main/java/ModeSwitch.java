public class ModeSwitch {
    private int currentMode;
    private int[] enabledMode= new int[]{1, 1, 1, 1, 0, 0};

    public int getMode() { return this.currentMode; }

    public void setMode(int[] enabledMode) {
        this.enabledMode = enabledMode;
    }

    public int[] getEnabledMode() { return this.enabledMode; }

    ModeSwitch() {

    }

    public void initialize() {
        currentMode = 0;
    }

    public void nextMode() {
        do {
            currentMode = (currentMode+1)%6;
        } while(enabledMode[currentMode] == 0);
    }

}

