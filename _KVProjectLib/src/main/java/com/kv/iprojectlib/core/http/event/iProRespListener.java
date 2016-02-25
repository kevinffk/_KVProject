package com.kv.iprojectlib.core.http.event;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;

import com.android.volley.Response.Listener;
import com.kv.iprojectlib.core.http.inf.IResponse;
import com.kv.iprojectlib.utils.LogUtil;

public class iProRespListener<T> implements Listener<T> {
    
    public iCallBackListener iCallback;

    public int reqCode = -1;

    public Dialog pdlg;
    
    public Context mAct;
    
    public IResponse response;
    
    public iProRespListener(Context act) {
        this.mAct = act;
    }
    
    @Override
    public void onResponse(T arg0) {
        if (pdlg != null) {
            pdlg.dismiss();
        }

        String jsonStr = "";
        if (arg0 instanceof JSONObject) { // json
            jsonStr = arg0.toString();
        } else if (arg0 instanceof String) { // string
            jsonStr = (String) arg0;
        }
        LogUtil.e("res=" + jsonStr);
        iCallback.onPostExecute(response.parseResponse(jsonStr), reqCode);
    }

    public iProRespListener<T> setiCallback(iCallBackListener iCallback) {
        this.iCallback = iCallback;
        return this;
    }

    public iProRespListener<T> setReqCode(int reqCode) {
        this.reqCode = reqCode;
        return this;
    }

    public iProRespListener<T> setProgressDialog(Dialog pdlg) {
        this.pdlg = pdlg;
        return this;
    }

    public iProRespListener<T> setIResponse(IResponse response) {
        this.response = response;
        return this;
    }

}
