package com.example.greg3d.visittimedispacher.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.model.DateRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by greg3d on 24.02.17.
 */
public class CSVHelper {

    private static CSVHelper instance;
    private String LOG_TAG = "CSVHELPER";

    private String DIR_SD = "Download/android";
    private String FILENAME_SD = "fakedata.csv";

    public static CSVHelper getInstance(){
        if(instance == null)
            instance = new CSVHelper();
        return instance;
    }

    public List<String> getFileNameList(){
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            throw new RuntimeException("SD-карта не доступна: " + Environment.getExternalStorageState());
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();

        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);

        if(!sdPath.exists())
            sdPath.mkdir();

        String [] list = sdPath.list();

        for(int i=0; i < list.length; i ++)
            Log.d(LOG_TAG, list[i]);

        return Arrays.asList(sdPath.list());

        // добавляем свой каталог к пути
        //sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
    }

    // Чтение данных с SD карты
    public List<DateRecord> readFileSD(String fileName) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            throw new RuntimeException("SD-карта не доступна: " + Environment.getExternalStorageState());
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, fileName);
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            return readDateRecords(br);
            // читаем содержимое
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("readFileSD null");
    }

    public List<DateRecord> readFileSD() {
        return readFileSD(FILENAME_SD);
    }


    // Чтение данных из внутренней памяти
    public List<DateRecord> readCsv(Context context) {

            InputStream csvStream = context.getResources().openRawResource(R.raw.fakedata);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            BufferedReader br = new BufferedReader(csvStreamReader);

            return readDateRecords(br);
    }

    private List<DateRecord> readDateRecords(BufferedReader br){
        List<DateRecord> resultList = new ArrayList<>();

        try {
            String[] line = null;
            String str = null;
            while ((str = br.readLine()) != null){
                line = str.split(";");
                Log.d(LOG_TAG, line[0]);
                DateRecord record = new DateRecord();
                record.dateIn = Tools.stringToDateTime(line[0]);
                record.dateOut = Tools.stringToDateTime(line[1]);
                record.isDayOff = Integer.parseInt(line[2]);
                record.isShortDay = Integer.parseInt(line[3]);
                resultList.add(record);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
        }
        return resultList;
    }

    /**
     * Запись
    **/

    // Запись данных на SD карту
    public void writeFileSD(String fileName) {
        writeFileSD(fileName, DBHelper.getInstance().getRecords());
    }

    public void writeFileSD(String fileName, List<DateRecord> records) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            throw new RuntimeException("SD-карта не доступна: " + Environment.getExternalStorageState());
        }

        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, fileName);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            writeDateRecords(bw, records);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Запись данных на SD карту
    public void writeFileSD(List<DateRecord> records) {
        writeFileSD("1_" + FILENAME_SD, records);
    }

    private void writeDateRecords(BufferedWriter bw, List<DateRecord> records){

        try {
            //for(DateRecord record: records){
            //    bw.write(Tools.modelToString(record));
            //}
            for(int i = 0; i < records.size(); i ++){
                bw.write(Tools.modelToString(records.get(i)));
                if(i< records.size() - 1)
                    bw.write("\n");
            }

        } catch (IOException e) {
            //e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
        }
    }
}
