

public class ModeSwitch {

    private boolean[] enabledMode = {true, true, true, true, false, false};
    private int currentMode;
    private int maxCursor;
    private int maxValueOfCursor;
    private int maxPage;



    /////////////////////////////////////////////


    /////////////////////////////////////////////


    ModeSwitch() {
        currentMode = 0;
    }

    public int initialize(){
        return currentMode;
    }
    public int nextMode(int currnetMode){
        return maxPage;
    }
    public void switchMode(){

    }
    public void saveMode(boolean enabledMode){

    }
    public void timeOut(){

    }
}

