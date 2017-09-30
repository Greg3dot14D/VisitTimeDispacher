package com.example.greg3d.visittimedispacher.controller;

import android.database.Cursor;
import android.util.Log;

import com.example.greg3d.visittimedispacher.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.helpers.DBHelper;
import com.example.greg3d.visittimedispacher.helpers.Tools;
import com.example.greg3d.visittimedispacher.model.DateFormat;
import com.example.greg3d.visittimedispacher.model.DateRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 24.02.17.
 */
public class DBController {

    private static String LOG_TAG = "DBCONTROLLER";

    public static String getAverageTime(){
        List<DateRecord> recordList = DBHelper.getInstance().getRecords(VisitTimeFixerActivity.instance.getDate());
        if(recordList.size() == 0)
            return "00 ч 00 м 00 с";

        return Tools.msToString((8  * 60 * 60 * 1000) - (getMissingTimeToLong()/recordList.size()));
    }

    public static String getAverageTimeToSimpleString(){
        List<DateRecord> recordList = DBHelper.getInstance().getRecords(VisitTimeFixerActivity.instance.getDate());
        if(recordList.size() == 0)
            return "000000 с";

        return Tools.msToSimpleString((8  * 60 * 60 * 1000) - (getMissingTimeToLong()/recordList.size()));
    }

    public static String getMissingTimeToSimpleString(){
        return Tools.msToSimpleString(getMissingTimeToLong());
    }

    public static String getMissingTime(){
        return Tools.msToString(getMissingTimeToLong());
    }

    public static long getMissingTimeToLong(){
        List<DateRecord> recordList = DBHelper.getInstance().getRecords(VisitTimeFixerActivity.instance.getDate());
        List<Double> spentTimeList = new ArrayList<>();
        for(DateRecord record: recordList)
            spentTimeList.add(Tools.getSpentTime(record));

        if(spentTimeList.size() == 0)
            return 0;

        long result = 0;
        for(double time: spentTimeList)
            result += time;

        return result;
    }

    public static void doEntrance(){
        DateRecord record = new DateRecord();
        record.dateIn = Tools.getCurrentDate();
        record.dateOut = record.dateIn;
        record.isDayOff = Tools.intDayOff(Tools.getCurrentDate());
        record.isShortDay = Tools.intShortDay(Tools.getCurrentDate());
        String script = "";

        if(!isExistRecord(record.dateIn))
            script = getInsertScript(record);
        else
            script = getUpdateEntranceScript(record);


        Log.d(LOG_TAG, script);

        DBHelper.execSQL(script);
    }

    public static void doExit(){
        DateRecord record = new DateRecord();
        record.dateIn = Tools.getCurrentDate();
        record.dateOut = record.dateIn;
        record.isDayOff = Tools.intDayOff(Tools.getCurrentDate());
        record.isShortDay = Tools.intShortDay(Tools.getCurrentDate());
        String script = "";

        if(!isExistRecord(record.dateIn))
            script = getInsertScript(record);
        else
            script = getUpdateExitScript(record);

        Log.d(LOG_TAG, script);

        DBHelper.execSQL(script);
    }

    public static void addNewRecord(Date date){

        if(isExistRecord(date)){
            Log.d(LOG_TAG, "is exist");
            return;
        }

        DateRecord record = new DateRecord();
        record.dateIn = date;
        record.dateOut = date;
        record.isDayOff = Tools.intDayOff(date);
        record.isShortDay = Tools.intShortDay(date);
        String script = getInsertScript(record);

        Log.d(LOG_TAG, script);

        DBHelper.execSQL(script);
    }

    public static void deleteRecord(DateRecord record){
        String script = String.format("delete from [VISIT_DATETIME_TABLE] where id = %s", record.id);
        DBHelper.execSQL(script);
    }

    public static long getLastDateInMS(){
        return DBHelper.getInstance().getLastDateIn().getTime();
    }

    public static boolean dateInEqualsDateOut(){
        return DBHelper.getInstance().getLastDateIn()
                .compareTo(
                        DBHelper.getInstance().getLastDateOut()) == 0;
    }

    public static DateRecord getRecord(Date date){
        return DBHelper.getInstance().getRecord(date);
    }


    public static boolean isExistRecord(Date date){
        return DBHelper.getCount(String.format(
                "select * from [VISIT_DATETIME_TABLE] where [DATETIME_IN] like '%s%%'",
                Tools.dateToString(date, DateFormat.DATE_YYYY_MM_DD))) > 0;
    }


    private static String getInsertScript(DateRecord record){
        return String.format("insert into [VISIT_DATETIME_TABLE] " +
                        " ([DATETIME_IN], [DATETIME_OUT], [IS_DAYOFF], [IS_SHORTDAY]) " +
                        " values ('%s', '%s', %s, %s)",
                Tools.dateToString(record.dateIn),
                Tools.dateToString(record.dateOut),
                record.isDayOff,
                record.isShortDay);
    }

    private static String getUpdateEntranceScript(DateRecord record){
        return String.format("update [VISIT_DATETIME_TABLE] " +
                        " set [DATETIME_IN] = '%s', " +
                        " [DATETIME_OUT] = '%s' " +
                        " where [DATETIME_IN] like '%s%%'",
                Tools.dateToString(record.dateIn),
                Tools.dateToString(record.dateIn),
                Tools.dateToString(record.dateIn, DateFormat.DATE_YYYY_MM_DD));
    }

    private static String getUpdateExitScript(DateRecord record){
        return String.format("update [VISIT_DATETIME_TABLE] " +
                        " set [DATETIME_OUT] = '%s' " +
                        " where [DATETIME_IN] like '%s%%'",
                Tools.dateToString(record.dateOut),
                Tools.dateToString(record.dateIn, DateFormat.DATE_YYYY_MM_DD));
    }

    public static void updateRecord(DateRecord record){
        if (record.id == 0)
            throw new RuntimeException("id is 0");
        String query = String.format("update [VISIT_DATETIME_TABLE] " +
                " set [DATETIME_IN] = '%s'," +
                " [DATETIME_OUT] = '%s'," +
                " [IS_DAYOFF] = %s," +
                " [IS_SHORTDAY] = %s" +
                " where [ID] = %s",
                Tools.dateToString(record.dateIn),
                Tools.dateToString(record.dateOut),
                record.isDayOff,
                record.isShortDay,
                record.id);

        Log.d(LOG_TAG, query);

        DBHelper.execSQL(query);
    }
}
