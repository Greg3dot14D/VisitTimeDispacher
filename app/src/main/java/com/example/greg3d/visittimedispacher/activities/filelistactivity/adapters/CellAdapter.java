package com.example.greg3d.visittimedispacher.activities.filelistactivity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.helpers.CSVHelper;

/**
 * Created by greg3d on 01.10.17.
 */
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
