package com.kv.iprojectlib.utils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class ActivityUtils {

    public static ComponentName getTopActivity(Context context) {
        ActivityManager am =  (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        return am.getRunningTasks(1).get(0).topActivity;
    }
    
    public static String getStartActivity(Context cxt, String packName) {
        
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent  
           Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);  
           resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
           resolveIntent.setPackage(packName);  
         
           // 通过getPackageManager()的queryIntentActivities方法遍历  
           List<ResolveInfo> resolveinfoList = cxt.getPackageManager().queryIntentActivities(resolveIntent, 0);  
         
           if (resolveinfoList != null && !resolveinfoList.isEmpty()) {
               ResolveInfo resolveinfo = resolveinfoList.get(0);
               if (resolveinfo != null) {  
                   return resolveinfo.activityInfo.name;
               }
           }
           return null;
       }
}
