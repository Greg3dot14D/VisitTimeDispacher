package com.example.greg3d.visittimedispacher.dialog;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

import com.example.greg3d.visittimedispacher.command.DateCommand;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by greg3d on 29.04.17.
 */
public class TimePickerDialogImpl {

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    public static String timeMask = "HH:mm:ss";

    private TimePickerDialog dialog;

    private Date date;

    private DateCommand command;

    TimePickerDialog.OnTimeSetListener CallBack = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute);
            command.execute(calendar);
        }

    };

    public TimePickerDialogImpl(final Activity activity, Date date, DateCommand command){
        this.command = command;
        this.date = date;
        calendar.setTime(date);

        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);

        dialog = new TimePickerDialog(activity, CallBack, this.hour, this.minute, true);
    }

    public void show(){
        dialog.updateTime(this.hour, this.minute);
        dialog.show();
    }
}
