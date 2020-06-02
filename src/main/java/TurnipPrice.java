import java.time.LocalDateTime;

public class TurnipPrice {

    private LocalDateTime date;
    private int[] price = new int[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0 };
    private Boolean isInputed[];
    private Boolean isHighestDate[];
    private int maxCursor;
    private int maxValueofCorsor[];
    private int maxPage;


    ///***controler부분
    private int tPrice;
    private int currentPage = 0;
    private int inputtedPrice = 0;

    public int getTurnipPrice(int page) {
        //수정해야함. controller에 있어서 만들었음.
        return 0;
    }

    public int reqInputPrice() {
        tPrice = inputPrice(currentPage);

        return tPrice;
    }
    public int changePriceValue(String input) {
        while((input == "C" || input == "D" )&& input != "B") {
            tPrice = tPrice + changeValue(input);
            if (tPrice > 600) {
                tPrice = minimizeValue();
            } else if (tPrice < 0) {
                tPrice = maximizeValue();
            }
        }
        if (tPrice <= 600 && tPrice >= 10)
            savePrice(tPrice);
        else
            //showError();
        reqCompleteSetting();

        return 0;
    }

    int  reqCompleteSetting() {

        return 0;
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

    public int maximizeValue() {
        return 600;
    }

    public int minimizeValue() {
        return 0;
    }
    /////***여기까지 controller 부분


    public int inputPrice(int currentPage) {//현재 페이지의 무값 반환
        return price[currentPage];
    }

    public void savePrice(int priceValue){ //무 값을 저장하고 인덱스 1 증가
        price[currentPage] = priceValue;
        inputtedPrice++;

        if(inputtedPrice>=5) {
            //calcPrice(); 여기에서 calcPrice호출
        }
    }
    public void resetPrice() {//무값 초기화
        price = new int[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0 };
        inputtedPrice = 0;
    }
    public int nextPrice(int currentPage) {
        return price[++currentPage];
    }

}
