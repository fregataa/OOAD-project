import java.time.LocalDateTime;
import java.util.Arrays;

public class TurnipPrice {

    private int[] price = new int[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0 };
    private boolean[] isInputed = new boolean[13];
    private TurnipCalc turnipCalc;

    ///***controler부분
    private int tPrice;
    private int currentPage = 0;
    private int inputtedPrice = 0;

    public TurnipPrice() {
        turnipCalc = new TurnipCalc();
    }

    public int getTurnipPrice() {
        return price[currentPage];
    }
    public String getTurnipDay() {
        String whatDayOfWeek;
        switch((currentPage + 1)/2){
        case 0 : whatDayOfWeek = "sun";
            break;
        case 1 : whatDayOfWeek = "mon";
            break;
        case 2 : whatDayOfWeek = "tue";
            break;
        case 3 : whatDayOfWeek = "wed";
            break;
        case 4 : whatDayOfWeek = "thu";
            break;
        case 5 : whatDayOfWeek = "fri";
            break;
        case 6 : whatDayOfWeek = "sat";
            break;
        default:whatDayOfWeek = "sun";

        }
        if(currentPage%2 == 1){
            whatDayOfWeek = whatDayOfWeek + "am-";
        }else{
            whatDayOfWeek = whatDayOfWeek + "pm-";
        }

        if(price[currentPage] == 0){
            return whatDayOfWeek + "---";
        }
        if(isInputed[currentPage]){
            return whatDayOfWeek + "-in";
        }else{

            return whatDayOfWeek + "est";
        }
    }

    public void savePrice(int priceValue){ //무 값을 저장하고 인덱스 1 증가
        price[currentPage] = priceValue;
        isInputed[currentPage] = true;
        inputtedPrice++;

        if(inputtedPrice>=5) {
            System.out.println(Arrays.toString(turnipCalc.calcPrice(price, isInputed)));
            price = turnipCalc.calcPrice(price,isInputed); //여기에서 calcPrice호출
        }
    }
    public void resetPrice() {//무값 초기화
        price = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0 };
        inputtedPrice = 0;
        currentPage = 0;
        isInputed = new boolean[13];
    }

    //0보다 크면 다음 페이지, 작으면 이전 페이지
    public void nextPrice(int page) {
        if (page > 0) {
            currentPage = (currentPage+1)%13;
        } else if(page < 0) {
            currentPage = (currentPage-1)%13;
        }
    }

}
