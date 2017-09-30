package com.example.greg3d.visittimedispacher.css;

/**
 * Created by greg3d on 25.09.17.
 */
public class EditButtonCss extends BaseCss {
    private final String FONT = Fonts.RUINED;
    private final float SHADOW_RADIUS = 10f;     //float radius
    private final float SHADOW_DX = 8f;          //float dx
    private final float SHADOW_DY = 8f;          //float dy
    private final int SHADOW_COLOR = 0xFF676767; //int color
    private final int TEXT_COLOR = 0xFF7C0E10;
    private final int TEXT_SIZE = 30;


    public float getShadowRadius(){
        return SHADOW_RADIUS;
    }
    public float getShadowDx(){
        return SHADOW_DX;
    }
    public float getShadowDy(){
        return SHADOW_DY;
    }
    public int getShadowColor(){
        return SHADOW_COLOR;
    }
    public int getTextColor(){
        return TEXT_COLOR;
    }
    public int getTextSize(){
        return TEXT_SIZE;
    }
    public String getFont(){
        return FONT;
    }
}
