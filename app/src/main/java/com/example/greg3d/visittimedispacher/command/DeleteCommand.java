package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.SomeActivity;
import com.example.greg3d.visittimedispacher.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.controller.DBController;
import com.example.greg3d.visittimedispacher.model.DateRecord;

/**
 * Created by greg3d on 30.04.17.
 */
public class DeleteCommand implements Command{

    private DateRecord record;

    public DeleteCommand(DateRecord record){
        this.record = record;
    }

    @Override
    public void execute() {
        DBController.deleteRecord(record);
        SomeActivity.refresh();
        VisitTimeFixerActivity.refresh();
    }
}
