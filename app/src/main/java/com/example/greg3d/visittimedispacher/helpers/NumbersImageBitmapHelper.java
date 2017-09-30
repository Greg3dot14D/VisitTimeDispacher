package com.example.greg3d.visittimedispacher.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by greg3d on 22.09.17.
 */
public class NumbersImageBitmapHelper {

    private int x = 1;
    private int y = 50;

    private int width = 26;
    private int height = 20;

//    private double xScale = 1;
//    private double yScale = 1;

    private Bitmap bitmapSource;

    private HashMap<String, Bitmap> bitMap = new HashMap<>();

    private String textToImaging;

    //numbersBitMap.put(String.valueOf(i), Bitmap.createBitmap(bitmapSource, 1 + i * width, 50, width, 70));

    public <T extends AppCompatActivity> NumbersImageBitmapHelper(T activity, int resourceId){
        bitmapSource = BitmapFactory.decodeResource(activity.getResources(), resourceId);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

//        xScale = metrics.widthPixels/720.0;
//        yScale = metrics.heightPixels/1280.0;
//
//        this.x *= xScale;
//        this.width *= xScale;
//        this.y *= yScale;
//        this.height *= yScale;

        this.x = 0;
        this.width = (int)(metrics.widthPixels/27.692);
        this.y = (int)(metrics.heightPixels/25.6);
        this.height = (int)(metrics.heightPixels/64.0);
    }

    public void putBitmap(String key, int index){
        bitMap.put(key, Bitmap.createBitmap(bitmapSource, x + index * width, y, width, y + height));
    }

    public HashMap<String, Bitmap> getBitMap(){
        return this.bitMap;
    }

    public void setTextToImaging(String text){
        this.textToImaging = text;
    }

    public NumbersImageBitmapHelper setImageByCharIndex(ImageView imageView, int index){
        imageView.setImageBitmap(this.bitMap.get(this.textToImaging.substring(index, index + 1)));
        return this;
    }
    public NumbersImageBitmapHelper setImageByChar(ImageView imageView, String chr){
        imageView.setImageBitmap(this.bitMap.get(chr));
        return this;
    }
}
