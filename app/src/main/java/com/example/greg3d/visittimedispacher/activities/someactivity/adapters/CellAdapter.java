package com.example.greg3d.visittimedispacher.activities.someactivity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.activities.someactivity.controls.ConvertView;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.framework.factory.ViewFactory;
import com.example.greg3d.visittimedispacher.helpers.DBHelper;
import com.example.greg3d.visittimedispacher.helpers.Tools;
import com.example.greg3d.visittimedispacher.model.DateRecord;

/**
 * Created by greg3d on 01.10.17.
 */
public class CellAdapter extends ArrayAdapter<DateRecord>
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

        ConvertView controls = new ConvertView();
        ViewFactory.InitView(convertView, controls);

        //((TextView) convertView.findViewById(R.id.c_Date))
        controls.date_TextView
                .setText(String.format("ДАТА    : %s", Tools.getDateToString(cell.dateIn)));

        String dayOff = "";
        switch(cell.isDayOff){
            case 1: dayOff = " (выходной)";
                break;
            case -1: dayOff = " (прогул)";
                break;
        }

        convertView.setId((int) cell.id);

        controls.dayOfWeek_TextView
                .setText(String.format("День недели: %s%s", Tools.getDayOfWeek(cell.dateIn), dayOff));
        controls.fromDate_TextView
                .setText(String.format("Вход    : %s",
                        (cell.isDayOff < 0 ? "--:--:--" : Tools.getTimeToString(cell.dateIn))));
        controls.toDate_TextView
                .setText(String.format("Выход   : %s",
                        (cell.isDayOff < 0 ? "--:--:--" : Tools.getTimeToString(cell.dateOut))));

        String missingTime = Tools.getMissingTime(cell);
        if(missingTime.contains("-"))
            controls.missingTime_TextView
                    .setText(String.format("Переработка: %s", missingTime.replace("-", "")));
        else
            controls.missingTime_TextView
                    .setText(String.format("Недоработка: %s", missingTime));
        return convertView;
    }
}
