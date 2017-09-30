package com.example.greg3d.visittimedispacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.helpers.DBHelper;
import com.example.greg3d.visittimedispacher.model.DateRecord;

/**
 * Created by greg3d on 22.04.17.
 */
public class ListActivity {

    public class CellAdapter extends ArrayAdapter<DateRecord>
    {
        public CellAdapter(Context context) {
            //super(context, android.R.layout.grid_cell, lCell);
            super(context, R.layout.cell, DBHelper.getInstance().getRecords());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DateRecord cell = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        //.inflate(android.R.layout.simple_list_item_2, null);
                        .inflate(R.layout.cell, null);
            }
            ((TextView) convertView.findViewById(R.id.c_FromDate))
                    .setText("Дата :" + cell.dateIn.toString());

            ((TextView) convertView.findViewById(R.id.c_ToDate))
                    .setText("Разница дней :" + String.valueOf(cell.dateOut.toString()));

            return convertView;
        }
    }
}
