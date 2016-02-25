package com.kv.iprojectlib.core.http.event;

import android.app.Dialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.kv.iprojectlib.R;
import com.kv.iprojectlib.utils.LogUtil;

public class iProRespErrorListener implements ErrorListener {

    public iCallBackListener iCallback;

    public int reqCode = -1;

    public Dialog pdlg;
    
    public Context mAct;
    
    private static final String TAG = iProRespErrorListener.class.getSimpleName();

    
    public iProRespErrorListener(Context act) {
        this.mAct = act;
    }
    
    @Override
    public void onErrorResponse(VolleyError error) {
        if (pdlg != null) {
            pdlg.dismiss();
        }

        String errorType = "";
        if (error instanceof NetworkError) {
            errorType = mAct.getString(R.string.ip_res_network_error);
        } else if (error instanceof ClientError) {
            errorType = mAct.getString(R.string.ip_res_client_error);
        } else if (error instanceof ServerError) {
            errorType = mAct.getString(R.string.ip_res_server_error);
        } else if (error instanceof AuthFailureError) {
            errorType = mAct.getString(R.string.ip_res_auth_failure_error);
        } else if (error instanceof ParseError) {
            errorType = mAct.getString(R.string.ip_res_parseerror);
        } else if (error instanceof NoConnectionError) {
            errorType = mAct.getString(R.string.ip_res_noconnection_error);
        } else if (error instanceof TimeoutError) {
            errorType = mAct.getString(R.string.ip_res_timeout_error);
        }
        
        if (iCallback != null) {
            iCallback.onErrorExecute(error, errorType, reqCode);
        }
        LogUtil.e(TAG, errorType + error.toString());
    }

    public iProRespErrorListener setiCallback(iCallBackListener iCallback) {
        this.iCallback = iCallback;
        return this;
    }

    public iProRespErrorListener setReqCode(int reqCode) {
        this.reqCode = reqCode;
        return this;
    }

    public iProRespErrorListener setProgressDialog(Dialog pdlg) {
        this.pdlg = pdlg;
        return this;
    }
}
