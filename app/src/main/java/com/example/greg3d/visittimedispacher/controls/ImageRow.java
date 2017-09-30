package com.example.greg3d.visittimedispacher.controls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;
import com.example.greg3d.visittimedispacher.framework.annotations.Name;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by greg3d on 20.06.17.
 */
public class ImageRow {

    private HashMap<String, Bitmap>  charMap;

    public ImageRow(HashMap<String, Bitmap>  charMap){
        this.charMap = charMap;
    }

    @Name("0")
    @FindBy(R.id.iv_a1)
    public ImageView a1;

    @Name("1")
    @FindBy(R.id.iv_a2)
    public ImageView a2;

    @Name("2")
    @FindBy(R.id.iv_a3)
    public ImageView a3;

    @Name("3")
    @FindBy(R.id.iv_a4)
    public ImageView a4;

    @Name("4")
    @FindBy(R.id.iv_a5)
    public ImageView a5;

    @Name("5")
    @FindBy(R.id.iv_a6)
    public ImageView a6;

    @Name("6")
    @FindBy(R.id.iv_a7)
    public ImageView a7;

    @Name("7")
    @FindBy(R.id.iv_a8)
    public ImageView a8;

    @Name("8")
    @FindBy(R.id.iv_a9)
    public ImageView a9;

    @Name("9")
    @FindBy(R.id.iv_a10)
    public ImageView a10;

    @Name("10")
    @FindBy(R.id.iv_a11)
    public ImageView a11;

    @Name("11")
    @FindBy(R.id.iv_a12)
    public ImageView a12;

    @Name("12")
    @FindBy(R.id.iv_a13)
    public ImageView a13;

    @Name("13")
    @FindBy(R.id.iv_a14)
    public ImageView a14;

    @Name("14")
    @FindBy(R.id.iv_a15)
    public ImageView a15;

    @Name("15")
    @FindBy(R.id.iv_a16)
    public ImageView a16;

    @Name("16")
    @FindBy(R.id.iv_a17)
    public ImageView a17;

    @Name("17")
    @FindBy(R.id.iv_a18)
    public ImageView a18;

    @Name("18")
    @FindBy(R.id.iv_a19)
    public ImageView a19;

    @Name("19")
    @FindBy(R.id.iv_a20)
    public ImageView a20;

    @Name("20")
    @FindBy(R.id.iv_a21)
    public ImageView a21;

    @Name("21")
    @FindBy(R.id.iv_a22)
    public ImageView a22;

    @Name("22")
    @FindBy(R.id.iv_a23)
    public ImageView a23;

    @Name("23")
    @FindBy(R.id.iv_a24)
    public ImageView a24;

    @Name("24")
    @FindBy(R.id.iv_a25)
    public ImageView a25;

//    @Name("25")
//    @FindBy(R.id.iv_a26)
//    public ImageView a26;
//
//    @Name("26")
//    @FindBy(R.id.iv_a27)
//    public ImageView a27;
//
//    @Name("27")
//    @FindBy(R.id.iv_a28)
//    public ImageView a28;
//
//    @Name("28")
//    @FindBy(R.id.iv_a29)
//    public ImageView a29;
//
//    @Name("29")
//    @FindBy(R.id.iv_a30)
//    public ImageView a30;


    private ImageView getImageViewByName(String name){
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field field: fields){
            if(field.isAnnotationPresent(Name.class)){
               if(field.getAnnotation(Name.class).value().equals(name))
                    try {
                        Log.d("ROW", name);
                        return (ImageView)field.get(this);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e.getStackTrace().toString());
                    }
                }
            }
        throw new RuntimeException(String.format("No field Name found [%s]", name));
    }

    public void fillRow(String text){
        String[] charArray = text.split("");
        for(int i = 0; i < charArray.length; i ++)
            getImageViewByName(String.valueOf(i)).setImageBitmap(charMap.get(charArray[i]));
            //getImageViewByName(String.valueOf(i)).setBackground(new BitmapDrawable(charMap.get(charArray[i])));
    }

}
