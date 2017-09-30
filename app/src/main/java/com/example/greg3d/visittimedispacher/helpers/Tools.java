package com.example.greg3d.visittimedispacher.helpers;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.greg3d.visittimedispacher.model.DateRecord;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 24.02.17.
 */
public class Tools {

    //private static String getDateFormat = "dd.MM.yyyy";
    private static String getDateFormat = "dd.MM.yyyy";
    private static String getTimeFormat = "HH:mm:ss";
    private static String getDateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    public static Date stringToDateTime(String aDate) {
        return stringToDate(aDate,getDateTimeFormat);
    }

    public static Date stringToDate(String aDate,String aFormat) {
        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        // TODO - убрать try
        //Date stringDate = simpledateformat.parse(aDate, pos);
        Date stringDate = Calendar.getInstance().getTime();

        try {
            stringDate = simpledateformat.parse(aDate, pos);
        }catch(Exception e){
            Log.d("TOOLS", e.getStackTrace().toString());
        }
        return stringDate;
    }

    public static String dateToString(Date date){
        return dateToString(date, getDateTimeFormat);
    }

    public static String getDateToString(Date date){
        return dateToString(date, getDateFormat);
    }

    public static String getTimeToString(Date date){
        return dateToString(date, getTimeFormat);
    }

    public static String dateToString(Date date, String dateFormat){
        SimpleDateFormat simpledateformat = new SimpleDateFormat(dateFormat);
        if(date == null) {
            date = Calendar.getInstance().getTime();
            Log.d("TOOLS", "date is null");
        }
        return simpledateformat.format(date);
    }

    public static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static int getIntDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    // Короткий день
    public static int intShortDay(Date date){
        if(getIntDayOfWeek(date) == 6)
            return 1;
        return 0;
    }

    // Выходной
    public static int intDayOff(Date date){
        if((getIntDayOfWeek(date) == 1) ||(getIntDayOfWeek(date) == 7))
            return 1;
        return 0;
    }

    public static String modelToString(DateRecord record){
        return String.format("%s;%s;%s;%s;"
                , dateToString(record.dateIn)
                , dateToString(record.dateOut)
                , record.isDayOff
                , record.isShortDay);
    }

    public static String getDayOfWeek(Date date){
        switch (getIntDayOfWeek(date)){
            case 2:
                return "Пн";
            case 3:
                return "Вт";
            case 4:
                return "Ср";
            case 5:
                return "Чт";
            case 6:
                return "Пт";
            case 7:
                return "Сб";
            case 1:
                return "Вс";
            default:
                return "Хз";
        }
    }

    public static String getMissingTime(DateRecord record){
        return msToString((long)getSpentTime(record));
    }

    public static double getSpentTime(DateRecord record){
        if(record.isDayOff < 0)
            return -(record.isDayOff * (record.isShortDay == 1 ? 7.75 :  9 ) ) * 60 * 60 * 1000;

        return (record.isShortDay == 1 ? 7.75 :  9 ) * 60 * 60 * 1000
                - (record.dateOut.getTime() - record.dateIn.getTime())
                - (record.isDayOff * (record.isShortDay == 1 ? 7.75 :  9 ) ) * 60 * 60 * 1000;
    }

    public static String msToString(long ms){
        String sign = "";
        if(ms < 0)
            sign = "- ";
        long hours = ms/(1000 * 60 * 60);
        long minutas = (ms - hours * 60 * 60 * 1000)/(1000 * 60);
        long seconds = (ms - hours * 60 * 60* 1000 - minutas * 60 * 1000)/1000;

        return sign + String.format
                //(" %s ч %s м %s с"
                (" %s:%s:%s"
                , Math.abs(hours) > 9 ? Math.abs(hours) : "0" + hours
                , Math.abs(minutas) > 9 ? Math.abs(minutas) : "0" + minutas
                , Math.abs(seconds) > 9 ? Math.abs(seconds) : "0" + seconds
        ).replace("-", "");
    }

    public static String msToSimpleString(long ms){
        String sign = "";
        if(ms < 0)
            sign = "- ";
        long hours = ms/(1000 * 60 * 60);
        long minutas = (ms - hours * 60 * 60 * 1000)/(1000 * 60);
        long seconds = (ms - hours * 60 * 60* 1000 - minutas * 60 * 1000)/1000;

        return
                sign +
                        String.format("%s%s%s"
                                , Math.abs(hours) > 9 ? Math.abs(hours) : "0" + hours
                                , Math.abs(minutas) > 9 ? Math.abs(minutas) : "0" + minutas
                                , Math.abs(seconds) > 9 ? Math.abs(seconds) : "0" + seconds
                        ).replace("-", "");
    }

    public static void showToast(Context context) {
        //создаем и отображаем текстовое уведомление
        Toast toast = Toast.makeText(context,
                "Пора покормить кота!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
