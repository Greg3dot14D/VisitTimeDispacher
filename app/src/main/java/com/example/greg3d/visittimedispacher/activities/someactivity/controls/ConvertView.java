package com.example.greg3d.visittimedispacher.activities.someactivity.controls;

import android.widget.TextView;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;

/**
 * Created by greg3d on 01.10.17.
 */
public class ConvertView {
    @FindBy(R.id.c_Date)
    public TextView date_TextView;

    @FindBy(R.id.c_DayOfWeek)
    public TextView dayOfWeek_TextView;

    @FindBy(R.id.c_FromDate)
    public TextView fromDate_TextView;

    @FindBy(R.id.c_ToDate)
    public TextView toDate_TextView;

    @FindBy(R.id.c_MissingTime)
    public TextView missingTime_TextView;
}
