package com.example.greg3d.visittimedispacher.activities.someactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.activities.someactivity.adapters.CellAdapter;
import com.example.greg3d.visittimedispacher.activities.someactivity.controls.Controls;
import com.example.greg3d.visittimedispacher.command.InsertCommand;
import com.example.greg3d.visittimedispacher.css.CssManager;
import com.example.greg3d.visittimedispacher.dialog.DatePickerDialogImpl;
import com.example.greg3d.visittimedispacher.framework.factory.ActivityFactory;
import com.example.greg3d.visittimedispacher.framework.helpers.ViewHelper;
import com.example.greg3d.visittimedispacher.helpers.ActivitiesManager;

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
                ActivitiesManager.startEditRecordActivity(activity, view.getId());
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

//    public static class CellAdapter extends ArrayAdapter<DateRecord>
//    {
//        public CellAdapter(Context context) {
//            super(context, R.layout.cell, DBHelper.getInstance().getRecords(VisitTimeFixerActivity.instance.getDate()));
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            DateRecord cell = getItem(position);
//
//            if (convertView == null)
//                convertView = LayoutInflater.from(getContext())
//                        .inflate(R.layout.cell, null);
//
//            ConvertView controls = new ConvertView();
//            ViewFactory.InitView(convertView, controls);
//
//            //((TextView) convertView.findViewById(R.id.c_Date))
//            controls.date_TextView
//                    .setText(String.format("ДАТА    : %s", Tools.getDateToString(cell.dateIn)));
//
//            String dayOff = "";
//            switch(cell.isDayOff){
//                case 1: dayOff = " (выходной)";
//                    break;
//                case -1: dayOff = " (прогул)";
//                    break;
//            }
//
//            convertView.setId((int) cell.id);
//
//            controls.dayOfWeek_TextView
//                    .setText(String.format("День недели: %s%s", Tools.getDayOfWeek(cell.dateIn), dayOff));
//            controls.fromDate_TextView
//                    .setText(String.format("Вход    : %s",
//                            (cell.isDayOff < 0 ? "--:--:--" : Tools.getTimeToString(cell.dateIn))));
//            controls.toDate_TextView
//                    .setText(String.format("Выход   : %s",
//                            (cell.isDayOff < 0 ? "--:--:--" : Tools.getTimeToString(cell.dateOut))));
//
//            String missingTime = Tools.getMissingTime(cell);
//            if(missingTime.contains("-"))
//                controls.missingTime_TextView
//                    .setText(String.format("Переработка: %s", missingTime.replace("-", "")));
//            else
//                controls.missingTime_TextView
//                        .setText(String.format("Недоработка: %s", missingTime));
//            return convertView;
//        }
//    }
}
