package com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;

/**
 * Created by greg3d on 30.09.17.
 */
public class Controls {
    @FindBy(R.id.ib_in)
    public ImageButton entrance_Button;

    @FindBy(R.id.ib_out)
    public ImageButton exit_Button;

    @FindBy(R.id.f_filterDateTextView)
    public TextView filterDate_TextView;
}
