package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.SomeActivity;
import com.example.greg3d.visittimedispacher.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.controller.DBController;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by greg3d on 30.04.17.
 */
public class InsertCommand implements DateCommand{

    @Override
    public void execute(Calendar calendar) {
        Date date = calendar.getTime();
        DBController.addNewRecord(date);
        SomeActivity.refresh();
        VisitTimeFixerActivity.refresh();
        //ActivitiesManager.startEditRecordActivity(SomeActivity.getInstance(), DBController.getRecord(date).id);
    }
}
