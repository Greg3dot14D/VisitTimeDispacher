package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.EditRecordActivity;

import java.util.Calendar;

/**
 * Created by greg3d on 29.04.17.
 */
public class SetDateExitCommand implements DateCommand{

    @Override
    public void execute(Calendar calendar) {
        EditRecordActivity.setDateExit(calendar.getTime());
    }
}
