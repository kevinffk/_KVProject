package com.kv.iprojectdemo.http.service.manager;

import android.app.Activity;

import com.kv.iprojectdemo.http.service.protocol.UrlConst;
import com.kv.iprojectdemo.http.service.protocol.request.LoginReq;
import com.kv.iprojectdemo.http.service.protocol.response.LoginRes;
import com.kv.iprojectlib.core.http.event.iCallBackListener;
import com.kv.iprojectlib.core.http.service.ServiceHelper;

public class MyManager {

    private static MyManager instance = null;
    
    private ServiceHelper serviceHelper = new ServiceHelper();
    
    private MyManager() {
        
    }
    
    public static synchronized MyManager getInstance() {
        if (instance == null) {
            instance = new MyManager();
        }
        return instance;
    }
    
    public void login(Activity act, LoginReq loginReq, int reqCode, iCallBackListener iCallBackListener) {
        String loadingRes = "登陆中...";
        String url = UrlConst.USER;
        serviceHelper.sendJsonRequest(act, loadingRes, url, loginReq, new LoginRes(), reqCode, iCallBackListener);
    }
}
