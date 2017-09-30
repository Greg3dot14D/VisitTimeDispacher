package com.example.greg3d.visittimedispacher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.command.DoExportCommand;
import com.example.greg3d.visittimedispacher.command.DoImportCommand;
import com.example.greg3d.visittimedispacher.dialog.YesNoDialog;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;
import com.example.greg3d.visittimedispacher.framework.factory.ActivityFactory;
import com.example.greg3d.visittimedispacher.framework.helpers.ViewHelper;
import com.example.greg3d.visittimedispacher.helpers.CSVHelper;
import com.example.greg3d.visittimedispacher.helpers.DBHelper;
import com.example.greg3d.visittimedispacher.helpers.Tools;
import com.example.greg3d.visittimedispacher.model.DateRecord;

import java.util.Date;

/**
 * Created by greg3d on 22.04.17.
 */
public class FileListActivity extends Activity implements View.OnClickListener {

    GridView gridView;

    private static boolean doExport = true;

    private static FileListActivity instance;

    public static FileListActivity getInstance(){
        return instance;
    }

    public Controls controls;

    public static class Controls {
        @FindBy(R.id.et_fileName)
        public EditText fileName_EditText;

        @FindBy(R.id.b_saveFile)
        public Button saveFile_Button;
    }

    public static void setDoExport(){
        doExport = true;
    }

    public static void setDoImport(){
        doExport = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filelist_activity);

        CellAdapter adapter = new CellAdapter(this);

        gridView = (GridView) findViewById(R.id.gv_filelist);
        gridView.setAdapter(adapter);

        // Интервал между строк
        gridView.setVerticalSpacing(10);
        // Интервал между столбцов
        gridView.setHorizontalSpacing(10);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((EditText)findViewById(R.id.et_fileName)).setText(parent.getItemAtPosition(position).toString());
            }
        });

        controls = new Controls();
        ActivityFactory.InitActivity(this, controls);
        ActivityFactory.setListener(this, controls);
        if(doExport)
            controls.fileName_EditText.setText(String.format("%s_visittime.csv", this.getCorrentDate()));
    }

    private String getCorrentDate(){
        return Tools.dateToString(Tools.getCurrentDate(), "ddMMyy");
    }

    @Override
    public void onClick(View view) {
        ViewHelper v = new ViewHelper(view);

        if(v.idEquals(controls.saveFile_Button)){
            if(doExport)
                new YesNoDialog(this, new DoExportCommand(this), "Выполняем экспорт во внешний файл ?").showAndCloseActivity();
            else
                new YesNoDialog(this, new DoImportCommand(this), "Выполняем импорт из текстового файла ?").showAndCloseActivity();
        }
    }

    public class CellAdapter extends ArrayAdapter<String>
    {
        public CellAdapter(Context context) {
            //super(context, android.R.layout.grid_cell, lCell);
            super(context, R.layout.filelist_cell, CSVHelper.getInstance().getFileNameList());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String cell = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        //.inflate(android.R.layout.simple_list_item_2, null);
                        .inflate(R.layout.filelist_cell, null);
            }
            ((TextView) convertView.findViewById(R.id.c_fileName))
                    .setText(cell);
            return convertView;
        }
    }
}
