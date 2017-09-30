package com.example.greg3d.visittimedispacher.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.greg3d.visittimedispacher.model.DateRecord;

import java.text.ParsePosition;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg3d on 24.02.17.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String LOG_TAG = "DB_HELPER";
    private SQLiteDatabase db;

    private static DBHelper instance;



    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
        Log.d(LOG_TAG, "DBHelper Starts");
        this.db = this.getWritableDatabase();
        Log.d(LOG_TAG, "getWritableDatabase Done");
        //this.setFakeDate();
        Log.d(LOG_TAG, "fakeInsert Done");
        instance = this;
    }

    public static DBHelper getInstance(){
        return instance;
    }

    public static void execSQL(String sql){
        instance.db.execSQL(sql);
    }

    public static int getCount(String sql){
        Cursor c = instance.db.rawQuery(sql, null);
        return c.getCount();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицы с полями
        this.db = sqLiteDatabase;
        this.createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // TEXT как строка формата ISO8601 ("YYYY-MM-DD HH:MM:SS.SSS").
    private void createTable(){
        String script = " CREATE TABLE [VISIT_DATETIME_TABLE] (" +
                " [ID] integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " [DATETIME_IN] TEXT," +
                " [DATETIME_OUT] TEXT," +
                " [IS_DAYOFF] INTEGER DEFAULT 0," +
                " [IS_SHORTDAY] INTEGER DEFAULT 0)";
        this.db.execSQL(script);
    }

    public void setRecords(List<DateRecord> list){
        this.deleteRecords();
        for(DateRecord record: list){
            String script = String.format("insert into [VISIT_DATETIME_TABLE] " +
            " ([DATETIME_IN], [DATETIME_OUT], [IS_DAYOFF], [IS_SHORTDAY]) " +
                    " values ('%s', '%s', %s, %s)",
                    Tools.dateToString(record.dateIn),
                    Tools.dateToString(record.dateOut),
                    record.isDayOff,
                    record.isShortDay);

            Log.d(LOG_TAG, script);

            this.db.execSQL(script);
        }
    }

    public List<DateRecord> getRecords(){
        return getRecordsImpl("select * from [VISIT_DATETIME_TABLE]");
    }

    public List<DateRecord> getRecords(Date date){
        Log.d(LOG_TAG, String.format("select * from [VISIT_DATETIME_TABLE] where [DATETIME_IN] like '%s%%'",
                Tools.dateToString(date, "yyyy-MM-")));
        return getRecordsImpl(String.format("select * from [VISIT_DATETIME_TABLE] where [DATETIME_IN] like '%s%%'",
                Tools.dateToString(date, "yyyy-MM-")));
    }

    public DateRecord getRecord(Date date){
        Log.d(LOG_TAG, String.format("select * from [VISIT_DATETIME_TABLE] where [DATETIME_IN] like '%s%%'",
                Tools.dateToString(date, "yyyy-MM-dd")));

        return getRecordsImpl(String.format("select * from [VISIT_DATETIME_TABLE] where [DATETIME_IN] like '%s%%'",
                Tools.dateToString(date,"yyyy-MM-dd"))).get(0);
    }

    public Date getLastDateIn(){
        Date date = null;
        try {
            String query = "select max([DATETIME_IN]) as DATETIME_IN  from [VISIT_DATETIME_TABLE]";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            date = Tools.stringToDateTime(cursor.getString(cursor.getColumnIndex("DATETIME_IN")));
            cursor.close();
        }catch(Exception e){}

        return date;
    }

    public Date getLastDateOut(){
        Date date = null;
        try {
            String query = "select max([DATETIME_OUT]) as DATETIME_OUT from [VISIT_DATETIME_TABLE]";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            date = Tools.stringToDateTime(cursor.getString(cursor.getColumnIndex("DATETIME_OUT")));
            cursor.close();
        }catch(Exception e){}

        return date;
    }

    private List<DateRecord> getRecordsImpl(String query){
        List<DateRecord> recordList = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                 recordList.add(getRecord(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recordList;
    }

    public DateRecord getRecordById(long id){

        Cursor cursor = db.rawQuery(String.format("select * from [VISIT_DATETIME_TABLE] where ID = '%s'", id), null);
        DateRecord record = null;
        if (cursor.moveToFirst())
            record = getRecord(cursor);
        cursor.close();
        return record;
    }

    private DateRecord getRecord(Cursor cursor){
        DateRecord record = new DateRecord();
        record.id = cursor.getLong(cursor.getColumnIndex("ID"));
        record.dateIn = Tools.stringToDateTime(cursor.getString(cursor.getColumnIndex("DATETIME_IN")));
        record.dateOut = Tools.stringToDateTime(cursor.getString(cursor.getColumnIndex("DATETIME_OUT")));
        record.isDayOff = cursor.getInt(cursor.getColumnIndex("IS_DAYOFF"));
        record.isShortDay = cursor.getInt(cursor.getColumnIndex("IS_SHORTDAY"));
        return record;
    }

    private void deleteRecords(){
        this.db.execSQL("delete from VISIT_DATETIME_TABLE");
    }

}
