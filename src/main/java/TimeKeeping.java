import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

class TimeKeeping {
    public static int maxCursor = 6 ;
    public int[] maxValueCursor = {60,60,60,60,60,60};
    private ZoneId timeZone = ZoneId.of("+9");
    private ZonedDateTime currentTime = ZonedDateTime.now(timeZone);
    private long subTime;

    //singleTone Design
    private TimeKeeping(){

    }
    private static class LazyHolder {
        public static final TimeKeeping INSTANCE = new TimeKeeping();
    }
    public static TimeKeeping getInstance(){
        return LazyHolder.INSTANCE;
    }
    //    public
    //return current Time
    //for display and alarm
    public LocalTime getLocalTimeValsue(){
        return currentTime.toLocalTime();
    }

    //return ZonedDateTime
    //for WorldTime
    public ZonedDateTime getCurrentTime(){
        currentTime = currentTime.now(timeZone).plusSeconds(subTime);
        return currentTime;
    }

    public void setCurrentTime(ZonedDateTime Time){
        subTime=ChronoUnit.SECONDS.between(currentTime.now(timeZone), Time);

    }


    //set Timekeeping TimeZone
    //input integer to timeZone value -12 to 12 ~if over, set to 12 or -12~
    public void setTimeZone(ZoneId timeZoneToChange){

        //set TimeZone
        timeZone = timeZoneToChange;
    }

}