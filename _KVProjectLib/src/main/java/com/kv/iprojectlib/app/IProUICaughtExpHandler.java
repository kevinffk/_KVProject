package com.kv.iprojectlib.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.kv.iprojectlib.R;
import com.kv.iprojectlib.utils.AppUtils;
import com.kv.iprojectlib.utils.LogUtil;

public class IProUICaughtExpHandler implements UncaughtExceptionHandler {

    private Context mContext;

    private static final String TAG = "UICaughtExpHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //CrashHandler实例  
    private static IProUICaughtExpHandler instance = null;

    public IProUICaughtExpHandler(Context context) {
        this.mContext = context;
    }

    private IProUICaughtExpHandler() {

    }

    public static IProUICaughtExpHandler getInstance() {
        if (instance == null) {
            instance = new IProUICaughtExpHandler();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 打印错误堆栈.
     * @param ex
     * @return
     */
    private String getExceptionString(Throwable ex) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            ex.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            String errLog = getExceptionString(ex);
            LogUtil.e(TAG, errLog);
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LogUtil.e(TAG, e.toString());
            }
            //退出程序  
            exitApplication();

        }
    }

    /**
     * 退出应用：
     */
    public void exitApplication() {
        IProApplication.getInstance().finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
        //使用Toast来显示异常信息  
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                handleDumpUI();
                Looper.loop();
            }
        }.start();

        return true;
    }
    
    public void handleDumpUI() {
        Toast.makeText(mContext, mContext.getString(R.string.ip_error_exit_msg, AppUtils.getApplicationName(mContext)), Toast.LENGTH_LONG).show();
    }
}
