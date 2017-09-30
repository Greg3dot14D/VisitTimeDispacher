package com.example.greg3d.visittimedispacher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.command.InsertCommand;
import com.example.greg3d.visittimedispacher.css.CssManager;
import com.example.greg3d.visittimedispacher.css.EditButtonCss;
import com.example.greg3d.visittimedispacher.dialog.DatePickerDialogImpl;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;
import com.example.greg3d.visittimedispacher.framework.factory.ActivityFactory;
import com.example.greg3d.visittimedispacher.framework.helpers.ViewHelper;
import com.example.greg3d.visittimedispacher.helpers.ActivitiesManager;
import com.example.greg3d.visittimedispacher.helpers.DBHelper;
import com.example.greg3d.visittimedispacher.helpers.Tools;
import com.example.greg3d.visittimedispacher.model.DateRecord;

import java.util.Calendar;

/**
 * Created by greg3d on 22.04.17.
 */
public class SomeActivity extends Activity implements View.OnClickListener{

    private static final String LOG = "SomeActivity";
    GridView gridView;

    private static SomeActivity instance;

    public static SomeActivity getInstance(){
        return instance;
    }

    private Controls controls;

    @Override
    public void onClick(View view) {
        ViewHelper v = new ViewHelper(view);

        if(v.idEquals(controls.insert_Button)){
            new DatePickerDialogImpl(this, Calendar.getInstance().getTime(), new InsertCommand()).show();
        }
    }

    public static class Controls {
        @FindBy(R.id.tv_Insert)
        public TextView insert_Button;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.some_activity);

        instance = this;

        CellAdapter adapter = new CellAdapter(this);

        gridView = (GridView) findViewById(R.id.gvBase);
        gridView.setAdapter(adapter);

        // Интервал между строк
        gridView.setVerticalSpacing(5);
        // Интервал между столбцов
        gridView.setHorizontalSpacing(10);

        final Activity activity = this;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((EditText) findViewById(R.id.et_fileName)).setText(parent.getItemAtPosition(position).toString());
                Log.d(LOG, "id ->" + view.getId());


                //Intent answerIntent = new Intent();
                // TODO - to long
                //answerIntent.putExtra("id", id);
                ActivitiesManager.startEditRecordActivity(activity, view.getId());
                //setResult(RESULT_OK, answerIntent);
            }
        });

        controls = new Controls();
        ActivityFactory.InitActivity(this, controls);
        ActivityFactory.setListener(this, controls);
        ActivityFactory.InitFonts(this,controls, CssManager.getEditButtonCss());
    }

    public static void refresh(){
        //CellAdapter adapter = new CellAdapter(this);
        instance.gridView.setAdapter(new CellAdapter(instance));
    }

    public static class CellAdapter extends ArrayAdapter<DateRecord>
    {
        public CellAdapter(Context context) {
            super(context, R.layout.cell, DBHelper.getInstance().getRecords(VisitTimeFixerActivity.instance.getDate()));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DateRecord cell = getItem(position);

            if (convertView == null)
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.cell, null);
            ((TextView) convertView.findViewById(R.id.c_Date))
                    .setText(String.format("ДАТА    : %s", Tools.getDateToString(cell.dateIn)));

            String dayOff = "";
            switch(cell.isDayOff){
                case 1: dayOff = " (выходной)";
                    break;
                case -1: dayOff = " (прогул)";
                    break;
            }

            convertView.setId((int)cell.id);

            Log.d(LOG, "id1->" + cell.id);

            ((TextView) convertView.findViewById(R.id.c_DayOfWeek))
                    .setText(String.format("День недели: %s%s", Tools.getDayOfWeek(cell.dateIn), dayOff));
                            //+ (cell.isDayOff == 1 ? " (выходной)": ""));

            ((TextView) convertView.findViewById(R.id.c_FromDate))
                    .setText(String.format("Вход    : %s",
                            (cell.isDayOff < 0 ? "--:--:--" : Tools.getTimeToString(cell.dateIn))));

            ((TextView) convertView.findViewById(R.id.c_ToDate))
                    .setText(String.format("Выход   : %s",
                            (cell.isDayOff < 0 ? "--:--:--" : Tools.getTimeToString(cell.dateOut))));

            String missingTime = Tools.getMissingTime(cell);
            if(missingTime.contains("-"))
                ((TextView) convertView.findViewById(R.id.c_MissingTime))
                    .setText(String.format("Переработка: %s", missingTime.replace("-", "")));
            else
                ((TextView) convertView.findViewById(R.id.c_MissingTime))
                        .setText(String.format("Недоработка: %s", missingTime));
            return convertView;
        }
    }
}
