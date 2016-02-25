package com.kv.iprojectlib.ui.dialog.theme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

import com.kv.iprojectlib.utils.DisplayUtil;
import com.kv.iprojectlib.utils.UiUtils;

public class DialogThemeProxy {

    public static Drawable getSharp(Context cxt, int color, boolean hasCorner) {
        Shape shape = null;
        if (hasCorner) {
            int rd = DisplayUtil.dip2px(7, cxt);
            final float [] outerRadii = {rd, rd, rd, rd, rd, rd, rd, rd};
            shape = new RoundRectShape(outerRadii, null, null);
        } else {
            shape = new RectShape();
        }
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setAntiAlias(true);
        return drawable;
    }
    
    public static Drawable getTitlebarSharp(Context cxt, int color, boolean hasCorner) {
        Shape shape = null;
        if (hasCorner) {
            int rd = DisplayUtil.dip2px(7, cxt);
            final float [] outerRadii = {rd, rd, rd, rd, 0, 0, 0, 0};
            shape = new RoundRectShape(outerRadii, null, null);
        } else {
            shape = new RectShape();
        }
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setAntiAlias(true);
        return drawable;
    }
    
    public static Drawable getPositionDrawable(Context cxt, iDialogStruct dialogStruct) {
        Drawable normalRes = getSharp(cxt, dialogStruct.getPositionBtnBGColor(), dialogStruct.hasCorner());
        Drawable pressedRes = getSharp(cxt, dialogStruct.getPositionBtnOnBGColor(), dialogStruct.hasCorner());
        return UiUtils.getStateDrawable(cxt, normalRes, pressedRes);
    }
    
    public static Drawable getNegativeDrawable(Context cxt, iDialogStruct dialogStruct) {
        Drawable normalRes = getSharp(cxt, dialogStruct.getNegativeBtnBGColor(), dialogStruct.hasCorner());
        Drawable pressedRes = getSharp(cxt, dialogStruct.getNegativeBtnOnBGColor(), dialogStruct.hasCorner());
        return UiUtils.getStateDrawable(cxt, normalRes, pressedRes);
    }
    
    public static ColorStateList getPostionTxtColor(Context cxt, iDialogStruct dialogStruct) {
        return UiUtils.getStateColor(cxt, dialogStruct.getPostionTxtColor(), dialogStruct.getPostionTxtOnColor());
    }
    
    public static ColorStateList getNegativeTxtColor(Context cxt, iDialogStruct dialogStruct) {
        return UiUtils.getStateColor(cxt, dialogStruct.getNegativeTxtColor(), dialogStruct.getNegativeTxtOnColor());
    }
    
    public static Drawable getTitleBarBg(Context cxt, iDialogStruct dialogStruct) {
        return getTitlebarSharp(cxt, dialogStruct.getDialogMainColor(), dialogStruct.hasCorner());
    }
    
    public static Drawable getDialogBg(Context cxt, iDialogStruct dialogStruct) {
        return getSharp(cxt, dialogStruct.getDialogBGColor(), dialogStruct.hasCorner());
    }
    
    
}
