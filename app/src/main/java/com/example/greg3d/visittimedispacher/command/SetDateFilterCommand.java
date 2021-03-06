package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.VisitTimeFixerActivity;

import java.util.Calendar;

/**
 * Created by greg3d on 29.04.17.
 */
public class SetDateFilterCommand implements DateCommand{

    @Override
    public void execute(Calendar calendar) {
        VisitTimeFixerActivity.instance.setDate(calendar.getTime());
        VisitTimeFixerActivity.refresh();
    }
}
