import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

class TimeKeeping {
    private ZoneId timeZone = ZoneId.of("+9");
    private ZonedDateTime currentTime = ZonedDateTime.now(timeZone);
    private long subTime;

    //singleTone Design
    public TimeKeeping(){

    }

    private static class LazyHolder {
        public static final TimeKeeping INSTANCE = new TimeKeeping();
    }

    public static TimeKeeping getInstance(){
        return LazyHolder.INSTANCE;
    }

    public ZonedDateTime getCurrentTime(){
        currentTime = currentTime.now(timeZone).plusSeconds(subTime);
        return currentTime;
    }

    public void setSubTime(ZonedDateTime Time){
        subTime=ChronoUnit.SECONDS.between(currentTime.now(timeZone), Time);
    }

    public void setTimeZone(ZoneId timeZoneToChange){
        timeZone = timeZoneToChange;
    }

}