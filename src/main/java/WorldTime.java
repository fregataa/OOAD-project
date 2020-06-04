import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class WorldTime {
    /*서울		ICN	+9
    런던		LHR	+0
    암스테르담	AMS	+1
    두바이		DXB	+4
    홍콩		HKG	+8
    시드니		SYD	+10
    로스엔젤레스	LAX	-8
    보스턴		BOS	-5
    멕시코시티	MEX	-6 */
    //시간이 아닌 UTC와의 차이를 저장해놓고, 인덱스를 기억해서 필요할때 계산해서 보여주는 구조
    //TimeKeeping의 시간을 인덱스의 값을 빼서 UTC 시간으로 만들어서 다시 시차 계산
    private String location[] = {"-12","-11","-10","-09","-08","-07","-06","-05","-04","-03","-02","-01"
            ,"+00","+01","+02","+03","+04","+05","+06","+07","+08","+09","+10","+11","+12"};

    private String locationName[] = {"---","SST","HNL","ANC","LAX","LAX","YYC","ORD","JFK","GRU"
            ,"---","---","UTC","CDG","JNB","SVO","DXB","MLE","OMS","BKK","HKG","ICN","SYD","---","AKL"};
    public int currentPage;
    public ZonedDateTime worldTime;
//    private int currentZone;
    private TimeKeeping timeKeeping = TimeKeeping.getInstance();
    public WorldTime(){
        //UTC+9 서울
        this.currentPage = 21;
    }
    public ZonedDateTime getWorldTime(){
        ZoneId id = ZoneId.of(location[this.currentPage]);
        return timeKeeping.getCurrentTime().now(id);
    }
    public String getUTCString(){
        return locationName[this.currentPage]+"UTC"+location[this.currentPage];

    }


    public void nextWorldTime() {

        this.currentPage=(this.currentPage+1)%25;
    }

    void changeTimeZone() {
        timeKeeping.setTimeZone(ZoneId.of(location[this.currentPage]));

//        int time_difference = location[currentPage] - location[timezone];
        //currentDateTime = currentDateTime.plusHours(time_difference);
        //setCurrentTime(worldTime);
    }
}

