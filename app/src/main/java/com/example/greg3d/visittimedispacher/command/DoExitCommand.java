package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.controller.DBController;

/**
 * Created by greg3d on 23.04.17.
 */
public class DoExitCommand implements Command{

    @Override
    public void execute() {
        DBController.doExit();
        VisitTimeFixerActivity.refresh();
    }
}
