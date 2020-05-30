import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class TimeKeeping {
    public static int maxCursor = 6 ;
    public int[] maxValueCursor = {60,60,60,60,60,60};
    private ZoneId timeZone = ZoneId.of("Asia/Seoul");
    private ZonedDateTime currentTime = ZonedDateTime.now(timeZone);
    //    public
    //return current Time
    //for display and alarm
    public LocalTime getTimeValue(){
        return currentTime.toLocalTime();
    }

    //return ZonedDateTime
    //for WorldTime
    public ZonedDateTime getCurrentTime(){
        return currentTime;
    }

    public void setCurrentTime(ZonedDateTime Time){
        currentTime = Time;
    }


    //set Timekeeping TimeZone
    //input integer to timeZone value -12 to 12 ~if over, set to 12 or -12~
    public void setTimeZone(int timeZoneToChange){
        //max min cast
        if (timeZoneToChange > 12){
            timeZoneToChange = 12;
        }else if (timeZoneToChange < -12){
            timeZoneToChange = -12;
        }

        //set ZoneID
        String tz = Integer.toUnsignedString(timeZoneToChange);
        if (timeZoneToChange >= 0){
            tz = "+" + tz;
        }else{
            tz = "-" + tz;
        }
        //set TimeZone
        timeZone = ZoneId.of(tz);
        currentTime = ZonedDateTime.now(timeZone);
    }

}