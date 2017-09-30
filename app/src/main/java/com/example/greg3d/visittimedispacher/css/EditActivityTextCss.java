package com.example.greg3d.visittimedispacher.css;

/**
 * Created by greg3d on 27.09.17.
 */
public class EditActivityTextCss extends BaseCss {
    private final String FONT = Fonts.MILITARY;
    private final float SHADOW_RADIUS = 8f;      //float radius
    private final float SHADOW_DX = 8f;          //float dx
    private final float SHADOW_DY = 8f;          //float dy
    private final int SHADOW_COLOR = 0xFF676767; //int color
    private final int TEXT_COLOR = 0xFF443032;   // int color
    //private final int TEXT_COLOR = 0xFF430fff;
    private final int TEXT_SIZE = 27;

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
