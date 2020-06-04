import java.time.LocalDateTime;

public class TurnipPrice {

    private LocalDateTime date;
    private int[] price = new int[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0 };
    private boolean isInputed[] = new boolean[13];
    private boolean isHighestDate[];
    private int maxCursor;
    private int maxValueofCorsor[];
    private int maxPage;
    private TurnipCalc turnipCalc;

    ///***controler부분
    private int tPrice;
    private int currentPage = 0;
    private int inputtedPrice = 0;

    public int getTurnipPrice() {
        return price[currentPage];
    }

    public int changeValue(String button) {
        int result = 0;
        switch (button) {
            case "C": result = 1;
            break;
            case "D": result = -1;
            break;
        }
        return result;
    }

    public int inputPrice(int currentPage) {//현재 페이지의 무값 반환
        return price[currentPage];
    }

    public void savePrice(int priceValue){ //무 값을 저장하고 인덱스 1 증가
        price[currentPage] = priceValue;
        isInputed[currentPage] = true;
        inputtedPrice++;

        if(inputtedPrice>=5) {
            price = turnipCalc.calcPrice(price,isInputed); //여기에서 calcPrice호출
        }
    }
    public void resetPrice() {//무값 초기화
        price = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0 };
        inputtedPrice = 0;
        currentPage = 0;
    }

    //0보다 크면 다음 페이지, 작으면 이전 페이지
    public void nextPrice(int page) {
        if (page > 0) {
            currentPage++;
        } else if(page < 0) {
            currentPage--;
        }
    }

}
