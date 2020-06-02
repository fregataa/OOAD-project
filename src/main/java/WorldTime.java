import java.time.ZonedDateTime;

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
    public Integer location[] = {9, 0, 1, 4, 8, 10, -8, -5, -6};
    public String locationName[] = {"ICN", "LHR", "AMS", "DXB", "HKG", "SYD", "LAX", "BOS", "MEX"};
    public int maxCursor;
    public int maxValueofCursor[];
    public int maxPage;
    public ZonedDateTime worldTime;
    //현재 표시중인 세계시간이 몇번째 시간인지에 대한 정보와 TimeKeeping이 몇번째 정보를 표시중인지에 대한 정보가 어딘가에는 있어야 한다.
    //TimeKeeping의 int timezone
    int timezone;

    /*int nextWorldTime(int currentPage) {
        ZonedDateTime currentDateTime;
        //TimeKeeping을 currentDateTime에 가져왔다고 가정

        ZonedDateTime worldTimeDate = currentDateTime.minusHours(location[timezone]);

        if(currentPage != maxCursor)
            worldTimeDate = currentDateTime.plusHours(location[currentPage++]);

        else
            worldTimeDate = currentDateTime.minusHours(location[0]);

        return currentPage;
    }*/


    int nextWorldTime(int currentPage) {
        //worldTime = setTimeZone(location[++currentPage]);

        return currentPage;
    }

    void changeTimeZone(int currentPage) {
        int time_difference = location[currentPage] - location[timezone];
        //currentDateTime = currentDateTime.plusHours(time_difference);
        //setCurrentTime(worldTime);
    }
}

