package com.kv.iprojectlib.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kv.iprojectlib.utils.SysUtils;

public class AppController {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private static AppController mInstance;
    
    private List<Activity> mActList = new ArrayList<Activity>();

    public static synchronized AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
        }
        return mInstance;
    }
    
    /**
     * Add Activity.
     * @param activity
     */
    public void addActivity(Activity activity) {
        mActList.add(activity);
    }

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {

            //if extend T-Card exited
            if (SysUtils.checkSDCard()) {
                File cacheDir = getMyAppCacheDir(context);
                HttpStack stack;
                if (Build.VERSION.SDK_INT >= 9) {
                    stack = new HurlStack();
                } else {
                    String userAgent = "volley/0";
                    try {
                        String packageName = context.getApplicationContext().getPackageName();
                        PackageInfo info = context.getApplicationContext().getPackageManager().getPackageInfo(packageName,0);
                        userAgent = packageName + "/" + info.versionCode;
                    } catch (NameNotFoundException e) {
                    }
                    stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
                }
                Network network = new BasicNetwork(stack);
                mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir), network);
                mRequestQueue.start();
            } else {
                mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
            }
        }
        return mRequestQueue;
    }

    private File getMyAppCacheDir(Context context) {
        File rootDir = Environment.getExternalStorageDirectory();
        return new File (rootDir, "."+ context.getPackageName());
    }

    public ImageLoader getImageLoader(Context context) {
        getRequestQueue(context);
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag, Context context) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(context).add(req);
    }

    public <T> void addToRequestQueue(Request<T> req, Context context) {
        req.setTag(TAG);
        getRequestQueue(context).add(req);
    }
    
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
