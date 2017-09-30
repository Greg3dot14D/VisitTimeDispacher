package com.example.greg3d.visittimedispacher.helpers;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by greg3d on 23.09.17.
 */
public class NumbersTextViewHelper {

    private String textToView;

    public void setTextToView(String text){
        this.textToView = text;
    }

    public NumbersTextViewHelper setImageByCharIndex(TextView view, int index){
        view.setText(this.textToView.substring(index, index + 1));
        return this;
    }
    public NumbersTextViewHelper setImageByChar(TextView view, String chr){
        view.setText(chr);
        return this;
    }
}
