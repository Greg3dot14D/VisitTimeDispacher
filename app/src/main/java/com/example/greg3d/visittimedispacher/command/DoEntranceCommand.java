package com.example.greg3d.visittimedispacher.command;

import android.widget.ImageButton;

import com.example.greg3d.visittimedispacher.VisitTimeFixerActivity;
import com.example.greg3d.visittimedispacher.controller.DBController;

/**
 * Created by greg3d on 23.04.17.
 */
public class DoEntranceCommand implements Command{

    @Override
    public void execute() {
        DBController.doEntrance();
        VisitTimeFixerActivity.refresh();
        //VisitTimeFixerActivity.setTimeToExit();
//
//        VisitTimeFixerActivity.instance.setX(VisitTimeFixerActivity.instance.controls.entrance_Button);
//        VisitTimeFixerActivity.instance.setArrow(VisitTimeFixerActivity.instance.controls.exit_Button);
//        VisitTimeFixerActivity.instance.unScaleControls();
    }
}
