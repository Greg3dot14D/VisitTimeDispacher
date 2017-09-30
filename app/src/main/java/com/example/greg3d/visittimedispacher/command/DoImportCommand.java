package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.FileListActivity;
import com.example.greg3d.visittimedispacher.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.helpers.CSVHelper;
import com.example.greg3d.visittimedispacher.helpers.DBHelper;

/**
 * Created by greg3d on 01.05.17.
 */
public class DoImportCommand implements Command{

    private FileListActivity activity;

    public DoImportCommand(FileListActivity activity){
        this.activity = activity;
    }

    @Override
    public void execute() {
        activity.setDoImport();
        DBHelper.getInstance().setRecords(CSVHelper.getInstance().readFileSD(activity.controls.fileName_EditText.getText().toString()));
        activity.finish();
        VisitTimeFixerActivity.refresh();
    }
}
