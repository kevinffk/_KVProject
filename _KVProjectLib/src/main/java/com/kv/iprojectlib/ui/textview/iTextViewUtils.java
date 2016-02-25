package com.kv.iprojectlib.ui.textview;

import android.graphics.Paint;
import android.widget.TextView;

public class iTextViewUtils {

    public static void setHtmlLink(TextView tvTest) {
        tvTest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvTest.getPaint().setAntiAlias(true);
    }
}
