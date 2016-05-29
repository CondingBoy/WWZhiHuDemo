package helloworld.example.administrator.wwzhihudemo.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/5/29.
 */
public class DateFomatUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("MM月dd日 E", Locale.CHINA);
    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);



    public static String getTime(Date date){
        return format.format(date);
    }
    public static String getTime(String date){
        Date d=null;
        try {
            d = format2.parse(date);
            Log.e("TAG5", d.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTime(d);
    }
}
