package com.kv.iprojectlib.app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;

public class IProApplication extends Application {
    
    public static final ArrayList<Activity> listActivites = new ArrayList<Activity>();

    private static IProApplication instance = null;

    public static IProApplication getInstance() {
        if (instance == null) {
            instance = new IProApplication();
        }
        return instance;
    }
    
    

    @Override
    public void onCreate() {
        super.onCreate();
        IProUICaughtExpHandler.getInstance().init(this);
        finishAll();
    }



    public void finishAll() {
        for (Activity activity : listActivites) {
            if (!activity.isDestroyed()) {
                activity.finish();
            }
        }
        listActivites.clear();
    }
}
