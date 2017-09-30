package com.example.greg3d.visittimedispacher.timer;

import com.example.greg3d.visittimedispacher.VisitTimeFixerActivity;

import java.util.TimerTask;

/**
 * Created by greg3d on 11.06.17.
 */
public class MissingTimerTask extends TimerTask{
    @Override
    public void run() {
        VisitTimeFixerActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                VisitTimeFixerActivity.instance.setMissingTimeByTimer();
            }
        });
    }
}
