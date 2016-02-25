package com.kv.iprojectlib.ui.statusbar;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.kv.iprojectlib.utils.LogUtil;

public class StatusBarUtils {

    /**
     * 设置状态栏背景状态
     */
    public static void setTranslucentStatus(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = act.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(act);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);//状态栏无背景
    }
    
    public static int getTatusHeight(Context cxt) {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int y = cxt.getResources().getDimensionPixelSize(x);
            return y;
        }catch(Exception e) {
            LogUtil.e(e.toString());
        }
        return 0;
    }
    

}
