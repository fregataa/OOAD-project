public class ModeSwitch {

    private int[] enabledMode=new int[]{1, 1, 1, 1, 1, 1};
    private int currentMode;
    private int maxCursor;
    private int maxValueOfCursor;
    private int maxPage;



    /////////////////////////////////////////////


    /////////////////////////////////////////////


    ModeSwitch() {
        currentMode = 0;
    }

    public void initialize(){
        currentMode = 0;
    }
    public int nextMode(){
        //enable된 모드 나올때까지 계속 증가시키기
        do{
            currentMode++;
            currentMode = currentMode%6;
        }while(enabledMode[currentMode] == 0);
        return currentMode;
    }
    public void switchMode(){

    }
    public void saveMode(int[] enabledMode){
        this.enabledMode = enabledMode;
    }
    public int timeOut(){
        initialize();
        return currentMode;
    }

    public int getMode() {
        return this.currentMode;
    }

    public void setMode(int mode) {
        this.currentMode = mode;
    }
}

