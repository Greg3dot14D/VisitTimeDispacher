package com.example.greg3d.visittimedispacher.activities.editrecordactivity.controls;

import android.widget.RadioButton;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;

/**
 * Created by greg3d on 01.10.17.
 */
public class Controls {
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
