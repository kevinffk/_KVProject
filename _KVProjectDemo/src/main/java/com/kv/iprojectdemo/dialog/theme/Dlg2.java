package com.kv.iprojectdemo.dialog.theme;

import android.graphics.Color;

import com.kv.iprojectlib.ui.dialog.theme.SimpleDialogStruct;

public class Dlg2 extends SimpleDialogStruct {

    @Override
    public boolean hasTitleBar() {
        return true;
    }

    @Override
    public boolean hasCorner() {
        return false;
    }

    @Override
    public int getDialogMainColor() {
        return Color.parseColor("#59ba16");
    }

    @Override
    public int getDialogMainOnColor() {
        return Color.parseColor("#4bb004");
    }

}
