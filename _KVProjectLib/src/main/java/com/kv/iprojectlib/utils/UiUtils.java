package com.kv.iprojectlib.utils;

import java.lang.reflect.Field;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class UiUtils {

    public static int getIdRes(Context context, String uiName) {
        return context.getResources().getIdentifier(uiName, "id", context.getPackageName());
    }

    public static int getLayoutRes(Context context, String uiName) {
        return context.getResources().getIdentifier(uiName, "layout", context.getPackageName());
    }
    
    public static int getStringRes(Context context, String uiName) {
        return context.getResources().getIdentifier(uiName, "string", context.getPackageName());
    }
    
    public static int getDrawableRes(Context context, String uiName) {
        return context.getResources().getIdentifier(uiName, "drawable", context.getPackageName());
    }
    
    public static int findIdByResName(Context context, String uiType, String idName) {
        try {
            Class localClass = Class.forName(context.getPackageName() + ".R$" + uiType);
            Field localField = localClass.getField(idName);
            int i = Integer.parseInt(localField.get(localField.getName()).toString());
            return i;
        } catch (Exception localException) {
            
        }
        return 0;
    }

    public static int findStringIResId(Context context, String idName) {
        return findIdByResName(context, "string", idName);
    }
    
    public static StateListDrawable initSelectedDrawable(Context context, String idNormalName, String idSelectedName) {
        int idNormal = getDrawableRes(context, idNormalName);
        int idSelect = getDrawableRes(context, idSelectedName);
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[] { android.R.attr.state_selected}, context.getResources().getDrawable(idSelect));
        sd.addState(new int[] {}, context.getResources().getDrawable(idNormal));
        return sd;
    }
    
    public static ColorStateList initSelectedColor(int normal, int selected){
        int [][] states = new int[2][];
        int [] colors = new int[2];
        states[0]= new int[] {android.R.attr.state_selected};
        colors[0] = selected;
        
        states[1] = new int[]{};
        colors[1] = normal;
        return new ColorStateList(states, colors);        
    }
    
    public static StateListDrawable getCommonStateDrawable(Context context, String idNormalName) {
        return getStateDrawable(context, idNormalName, idNormalName + "_on");
    }
    
    public static StateListDrawable getStateDrawable(Context context, Drawable normalRes, Drawable pressedRes) {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, pressedRes);
        sd.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressedRes);
        sd.addState(new int[] { android.R.attr.state_focused }, pressedRes);
        sd.addState(new int[] { android.R.attr.state_pressed }, pressedRes);
        sd.addState(new int[] { android.R.attr.state_enabled }, normalRes);
        sd.addState(new int[] {}, normalRes);
        return sd;
    }
    
    public static StateListDrawable getStateDarawable(Context context, String idNormalName) {
        return getStateDrawable(context, idNormalName, idNormalName + "_on");
    }
    
    public static StateListDrawable getStateDrawable(Context context, String idNormalName, String idPressedName) {
        int idNormal = getDrawableRes(context, idNormalName);
        int idPressed = getDrawableRes(context, idPressedName);
        
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focus = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        sd.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        sd.addState(new int[] { android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed }, pressed);
        sd.addState(new int[] { android.R.attr.state_enabled }, normal);
        sd.addState(new int[] {}, normal);
        return sd;
    }
    
    public static ColorStateList getStateColor(Context context, int idNormalColor, int idPressedColor) {
        int[] colors = new int[] { idPressedColor, idPressedColor, idNormalColor, idPressedColor, idNormalColor, idNormalColor };  
        int[][] states = new int[6][];  
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };  
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };  
        states[2] = new int[] { android.R.attr.state_enabled };  
        states[3] = new int[] { android.R.attr.state_focused };  
        states[4] = new int[] { android.R.attr.state_window_focused };  
        states[5] = new int[] {};  
        ColorStateList cs = new ColorStateList(states, colors);  
        return cs;
    }
    
}
