package com.kv.iprojectlib.ui.toast;

import android.content.Context;
import android.widget.Toast;

import com.kv.iprojectlib.R;


public class iToastUtils {

    public static void showRectToastSucc(Context context, String msg) {
        iRectToast.makeText(context, R.drawable.ip_toast_success_icon, msg, Toast.LENGTH_SHORT).show();
    }
    
    public static void showRectToastWarn(Context context, String msg) {
        iRectToast.makeText(context, R.drawable.ip_toast_warning_icon, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    
    public static void showToast(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
