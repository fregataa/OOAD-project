import java.time.ZonedDateTime;
import java.util.Random;
public class TurnipCalc {
//    public localDate halfday;

    public int[] price = new int[13];

    public void TurnipCalc(){

    }
    public int[] calcPrice(int[] inputPrice, boolean[] isInputed){

        //random for estimate
        Random rand = new Random();

        //1:감소 2: 발산 3:증가
        int whatMode = 1;
        //일요일 값이 안들어 있을때를 위해
        int sunday = rand.nextInt(20)+90;

        if (isInputed[0]){
            sunday = inputPrice[0];
        }else{
            inputPrice[0] = sunday;
        }
        boolean[] isDescent = new boolean[13];
        int previous = sunday;
        int skipCount = 0;
        int end = 0;

        //감소 증가 여부 찾기
        for(int i = 1; i <13; i ++){
            if (isInputed[i]){
                if (previous>inputPrice[i]){

                    isDescent[i] = true;
                    for(int j = i-1; i-j <= skipCount; j--){
                        //선형으로 빈 부분 채우기
                        inputPrice[j] = inputPrice[i]-(i-j)*((inputPrice[i]-previous)/(skipCount+1));
                        isDescent[j] = true;
                    }
                }else{
                    isDescent[i] = false;
                    for(int j = i-1; i-j <= skipCount; j--){
                        //선형으로 빈 부분 채우기
                        inputPrice[j] = inputPrice[i]-(i-j)*((inputPrice[i]-previous)/(skipCount+1));
                        isDescent[j] = false;
                    }
                }
                previous = inputPrice[i];
                skipCount = 0;
                end = i;

            }else{
                skipCount++;
            }
        }




        if (isDescent[end]){
            whatMode = 1;
        }else{
            for(int j = end-1; j > 0 ;j--){
                if (isDescent[j]){
                    if (j == 1){
                        whatMode = 3;
                    }
                }else{
                    whatMode = 2;
                    break;
                }

            }
        }

        switch(whatMode){
            //감소
            case(1):
                for(int j = end;j<13;j++){
                    inputPrice[j] = (int) (((Math.min(inputPrice[j - 1], inputPrice[0])) * 0.95)
                            - rand.nextInt(4));
                }

                break;
            //발산
            case(2):
                for(int j = end;j<13;j++){
                    inputPrice[j] = inputPrice[0]+rand.nextInt(40)-20;
                }
                break;
            //증가
            case(3):
                //대박의 날
                inputPrice[end] = 400 + rand.nextInt(200);
                for(int j = end+1;j<13;j++){
                    inputPrice[j] = (int) (((Math.min(inputPrice[j - 1], inputPrice[0])) * 0.95)
                            - rand.nextInt(4));
                }
                break;
        }

//        setHighestPrice(end);
        return inputPrice;

    }
    public void setHighestPrice(int lastInputDay){
//        ZonedDateTime currentTime = TimeKeeping.getCurrentTime();
//        currentTime.getDayOfWeek()*2 +;

    }

}