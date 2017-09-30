package com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls;

import android.widget.TextView;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;

/**
 * Created by greg3d on 30.09.17.
 */
public class Labels {
    @FindBy(R.id.editText_averageTimeLabel)
    public TextView averageTime;

    @FindBy(R.id.editText_missingTimeLabel)
    public TextView missingTime;
}
