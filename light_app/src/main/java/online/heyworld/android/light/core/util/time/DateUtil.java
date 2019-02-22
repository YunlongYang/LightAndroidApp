package online.heyworld.android.light.core.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date fromString(String dateString){
        try {
            return format().parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String fromDate(Date date){
        return format().format(date);
    }

    private static SimpleDateFormat format(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static Date getSolicitudeDate(){
        Calendar calendar = Calendar.getInstance();
        if(calendar.get(Calendar.HOUR)>=7){

        }else{
            calendar.add(Calendar.DAY_OF_YEAR,-1);
        }
        return new Date(calendar.getTimeInMillis());
    }

    public static String getSolicitudeDateString(){
        return fromDate(getSolicitudeDate());
    }
}
