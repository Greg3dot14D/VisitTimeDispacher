package com.example.greg3d.visittimedispacher.activities.filelistactivity.controls;

import android.widget.Button;
import android.widget.EditText;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;

/**
 * Created by greg3d on 01.10.17.
 */
public class Controls {
    @FindBy(R.id.et_fileName)
    public EditText fileName_EditText;

    @FindBy(R.id.b_saveFile)
    public Button saveFile_Button;
}
