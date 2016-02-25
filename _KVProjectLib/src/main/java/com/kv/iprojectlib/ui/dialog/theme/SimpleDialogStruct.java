package com.kv.iprojectlib.ui.dialog.theme;

import android.graphics.Color;

public abstract class SimpleDialogStruct implements iDialogStruct {

    @Override
    public boolean hasTitleBar() {
        return true;
    }

    @Override
    public boolean hasCorner() {
        return false;
    }

    @Override
    public int getDialogBGColor() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public abstract int getDialogMainColor();

    
    public abstract int getDialogMainOnColor();
    
    @Override
    public int getPositionBtnBGColor() {
        return getDialogMainColor();
    }

    @Override
    public int getPositionBtnOnBGColor() {
        return getDialogMainOnColor();
    }

    @Override
    public int getNegativeBtnBGColor() {
        return getDialogMainColor();
    }

    @Override
    public int getNegativeBtnOnBGColor() {
        return getDialogMainOnColor();
    }

    @Override
    public int getPostionTxtColor() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public int getPostionTxtOnColor() {
        return getPostionTxtColor();
    }

    @Override
    public int getNegativeTxtColor() {
        return getPostionTxtColor();
    }

    @Override
    public int getNegativeTxtOnColor() {
        return getPostionTxtColor();
    }

    @Override
    public int getMainTxtSize() {
        return 17;
    }

    @Override
    public int getMainTxtColor() {
        return Color.parseColor("#555555");
    }

}
