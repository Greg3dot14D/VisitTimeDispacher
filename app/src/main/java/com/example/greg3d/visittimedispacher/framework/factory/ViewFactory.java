package com.example.greg3d.visittimedispacher.framework.factory;

import android.view.View;

import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;

import java.lang.reflect.Field;

/**
 * Created by greg3d on 01.10.17.
 */
public class ViewFactory {

    public static <T extends View> void InitView(T activity, Object conteiner){
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
}
