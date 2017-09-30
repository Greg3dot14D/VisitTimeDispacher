package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.activities.filelistactivity.FileListActivity;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.helpers.CSVHelper;

/**
 * Created by greg3d on 01.05.17.
 */
public class DoExportCommand implements Command{

    private FileListActivity activity;

    public DoExportCommand(FileListActivity activity){
        this.activity = activity;
    }

    @Override
    public void execute() {
        activity.setDoExport();
        CSVHelper.getInstance().writeFileSD(activity.controls.fileName_EditText.getText().toString());
        activity.finish();
        VisitTimeFixerActivity.refresh();
    }
}
