package com.kv.iprojectlib.plugin.qrcodescan;

import com.google.zxing.Result;
import com.kv.iprojectlib.plugin.qrcodescan.mining.app.zxing.view.ViewfinderView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.SurfaceHolder.Callback;

public abstract class BaseMipcaActivityCapture extends Activity implements Callback {

    public abstract Handler getHandler();
    
    public abstract ViewfinderView getViewfinderView();
    
    public abstract void handleDecode(Result result, Bitmap barcode);
    
    public abstract void drawViewfinder();
}
