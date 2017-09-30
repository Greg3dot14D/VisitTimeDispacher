package com.example.greg3d.visittimedispacher.command;

import com.example.greg3d.visittimedispacher.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.controller.DBController;

/**
 * Created by greg3d on 23.04.17.
 */
public class DoExitCommand implements Command{

    @Override
    public void execute() {
        DBController.doExit();
        VisitTimeFixerActivity.refresh();
//
//        VisitTimeFixerActivity.instance.setArrow(VisitTimeFixerActivity.instance.controls.entrance_Button);
//        VisitTimeFixerActivity.instance.setX(VisitTimeFixerActivity.instance.controls.exit_Button);
//        VisitTimeFixerActivity.instance.unScaleControls();
    }
}
