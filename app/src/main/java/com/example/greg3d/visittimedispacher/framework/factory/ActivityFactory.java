package com.example.greg3d.visittimedispacher.framework.factory;

import java.lang.reflect.Field;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.css.BaseCss;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;

/**
 * Created by greg3d on 30.04.17.
 */
public class ActivityFactory {

    public static <T extends Activity>void InitActivity(T activity, Object conteiner){
        Field[] fields = conteiner.getClass().getDeclaredFields();

        for(Field field: fields){
            if(field.isAnnotationPresent(FindBy.class)){
                int id = field.getAnnotation(FindBy.class).value();

                try {
                    field.set(conteiner, activity.findViewById(id));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getStackTrace().toString());
                }
            }
        }
    }

    public static <T extends View.OnClickListener> void setListener(T activity, Object conteiner){
        Field[] fields = conteiner.getClass().getDeclaredFields();

        for(Field f: fields){
            try {
                ((View)f.get(conteiner)).setOnClickListener(activity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getStackTrace().toString());
            }
        }
    }

    public static <T extends Activity, C extends BaseCss> void InitFonts(T activity, Object conteiner, C font){
        Field[] fields = conteiner.getClass().getDeclaredFields();

        for(Field field: fields){
            try {
                Object o = field.get(conteiner);

                if(o instanceof TextView)
                    InitFonts(activity, (TextView)o, font);

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getStackTrace().toString());
            }
        }
    }

    public static <T extends Activity, C extends BaseCss> void InitFonts(T activity, TextView textView, C font){
        Typeface type = Typeface.createFromAsset(activity.getAssets(), font.getFont());
        textView.setTypeface(type);
        textView.setTextSize(font.getTextSize());
        textView.setTextColor(font.getTextColor());
        textView.setShadowLayer(
                font.getShadowRadius(),     //float radius
                font.getShadowDx(),         //float dx
                font.getShadowDy(),         //float dy
                font.getShadowColor()       //int color
        );
    }
}