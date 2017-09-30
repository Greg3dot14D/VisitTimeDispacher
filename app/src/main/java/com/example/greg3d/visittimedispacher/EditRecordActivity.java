package com.example.greg3d.visittimedispacher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.command.DeleteCommand;
import com.example.greg3d.visittimedispacher.command.SetDateEntranceCommand;
import com.example.greg3d.visittimedispacher.command.SetDateExitCommand;
import com.example.greg3d.visittimedispacher.command.SetTimeEntranceCommand;
import com.example.greg3d.visittimedispacher.command.SetTimeExitCommand;
import com.example.greg3d.visittimedispacher.controller.DBController;
import com.example.greg3d.visittimedispacher.css.CssManager;
import com.example.greg3d.visittimedispacher.css.EditButtonCss;
import com.example.greg3d.visittimedispacher.dialog.DatePickerDialogImpl;
import com.example.greg3d.visittimedispacher.dialog.TimePickerDialogImpl;
import com.example.greg3d.visittimedispacher.dialog.YesNoDialog;
import com.example.greg3d.visittimedispacher.framework.factory.ActivityFactory;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;
import com.example.greg3d.visittimedispacher.framework.helpers.ViewHelper;
import com.example.greg3d.visittimedispacher.helpers.DBHelper;
import com.example.greg3d.visittimedispacher.helpers.Tools;
import com.example.greg3d.visittimedispacher.model.DateRecord;

import java.util.Date;

/**
 * Created by greg3d on 29.04.17.
 *
 */
public class EditRecordActivity extends Activity implements View.OnClickListener{

    private static final String LOG = "EditRecordActivity";

    private DateRecord record;
    private Controls controls;

    private static EditRecordActivity instance;

    public static class Controls{

        @FindBy(R.id.tv_LabelEntrance)
        public TextView labelEntrance;

        @FindBy(R.id.tv_LabelExit)
        public TextView labelExit;

        @FindBy(R.id.rb_DayIsShort)
        public RadioButton dayIsShort_RadioButton;

        @FindBy(R.id.rb_DayIsLong)
        public RadioButton dayIsLong_RadioButton;

        @FindBy(R.id.rb_DayTypeWork)
        public RadioButton dayTypeWork_RadioButton;

        @FindBy(R.id.rb_DayTypeRest)
        public RadioButton dayTypeRest_RadioButton;

        @FindBy(R.id.rb_DayTypeOut)
        public RadioButton dayTypeOut_RadioButton;

        @FindBy(R.id.tv_DateEntrance)
        public TextView dateEntrance_TextView;

        @FindBy(R.id.tv_DateExit)
        public TextView dateExit_TextView;

        @FindBy(R.id.tv_TimeEntrance)
        public TextView timeEntrance_TextView;

        @FindBy(R.id.tv_TimeExit)
        public TextView timeExit_TextView;

        @FindBy(R.id.tv_Update)
        public TextView updateButton;

        @FindBy(R.id.tv_Delete)
        public TextView delete_Button;

        @FindBy(R.id.tv_Cancel)
        public TextView cancel_Button;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_record_activity);

        instance = this;

        controls = new Controls();

        ActivityFactory.InitActivity(this, controls);

        setFieldsById(getIntent().getLongExtra("id", 0));

        ActivityFactory.setListener(this, controls);

        ActivityFactory.InitFonts(this, controls, CssManager.getEditActivityTextCss());

        ActivityFactory.InitFonts(this, controls.cancel_Button, CssManager.getEditButtonCss());
        ActivityFactory.InitFonts(this, controls.delete_Button, CssManager.getEditButtonCss());
        ActivityFactory.InitFonts(this, controls.updateButton, CssManager.getEditButtonCss());
    }

    public static EditRecordActivity getInstance(){
        return instance;
    }

    public static void setDateEntrance(Date date){
        instance.controls.dateEntrance_TextView.setText(Tools.getDateToString(date));
        instance.record.dateIn = date;
    }

    public static void setDateExit(Date date){
        instance.controls.dateExit_TextView.setText(Tools.getDateToString(date));
        instance.record.dateOut = date;
    }

    public static void setTimeEntrance(Date date){
        instance.controls.timeEntrance_TextView.setText(Tools.getTimeToString(date));
        instance.record.dateIn = date;
    }

    public static void setTimeExit(Date date){
        instance.controls.timeExit_TextView.setText(Tools.getTimeToString(date));
        instance.record.dateOut = date;
    }

    //@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        setFieldsById(data.getLongExtra("id", 0));
//    }

    private void setFieldsById(long id){
        record = DBHelper.getInstance().getRecordById(id);

        controls.dateEntrance_TextView.setText(Tools.getDateToString(record.dateIn));
        controls.timeEntrance_TextView.setText(Tools.getTimeToString(record.dateIn));

        controls.dateExit_TextView.setText(Tools.getDateToString(record.dateOut));
        controls.timeExit_TextView.setText(Tools.getTimeToString(record.dateOut));

        if(record.isShortDay == 1)
            controls.dayIsShort_RadioButton.toggle();
        else
            controls.dayIsLong_RadioButton.toggle();

        switch(record.isDayOff){
            case 0:
                controls.dayTypeWork_RadioButton.toggle();
                break;
            case 1:
                controls.dayTypeRest_RadioButton.toggle();
                break;
            case -1:
                controls.dayTypeOut_RadioButton.toggle();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        ViewHelper v = new ViewHelper(view);

        if(v.idEquals(controls.dayIsLong_RadioButton))
            record.isShortDay = 0;
        else if(v.idEquals(controls.dayIsShort_RadioButton))
            record.isShortDay = 1;
        else if(v.idEquals(controls.dayTypeOut_RadioButton))
            record.isDayOff = -1;
        else if(v.idEquals(controls.dayTypeRest_RadioButton))
            record.isDayOff = 1;
        else if(v.idEquals(controls.dayTypeWork_RadioButton))
            record.isDayOff = 0;
        else if(v.idEquals(controls.dateEntrance_TextView))
            new DatePickerDialogImpl(this, record.dateIn, new SetDateEntranceCommand()).show();
        else if(v.idEquals(controls.dateExit_TextView))
            new DatePickerDialogImpl(this, record.dateOut, new SetDateExitCommand()).show();
        else if(v.idEquals(controls.timeEntrance_TextView))
            new TimePickerDialogImpl(this, record.dateIn, new SetTimeEntranceCommand()).show();
        else if(v.idEquals(controls.timeExit_TextView))
            new TimePickerDialogImpl(this, record.dateOut, new SetTimeExitCommand()).show();

        else if(v.idEquals(controls.updateButton)){
            DBController.updateRecord(record);
            SomeActivity.refresh();
            VisitTimeFixerActivity.refresh();
            this.finish();
        }
        else if(v.idEquals(controls.cancel_Button)){
            setFieldsById(record.id);
            this.finish();
        }
        else if(v.idEquals(controls.delete_Button)){
            new YesNoDialog(this, new DeleteCommand(record), "Удаляем запись ?").showAndCloseActivity();
        }
    }
}
