/**
 * Copyright JollyTech Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of JollyTech Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from JollyTech or an authorized sublicensor.
 */
package com.kv.iprojectlib.core.http.service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.kv.iprojectlib.core.AppController;
import com.kv.iprojectlib.core.http.event.iCallBackListener;
import com.kv.iprojectlib.core.http.event.iProRespErrorListener;
import com.kv.iprojectlib.core.http.event.iProRespListener;
import com.kv.iprojectlib.core.http.expand.request.RequestFactory;
import com.kv.iprojectlib.core.http.expand.request.RequestFactory.Form;
import com.kv.iprojectlib.core.http.expand.request.RequestUtils;
import com.kv.iprojectlib.core.http.inf.IRequest;
import com.kv.iprojectlib.core.http.inf.IResponse;
import com.kv.iprojectlib.core.http.service.visitor.IDialogVisitor;
import com.kv.iprojectlib.ui.progressdialog.iProgressDlg;
import com.kv.iprojectlib.utils.LogUtil;
/**
 * Service Helper.
 * @version   Revision History
 * <pre>
 * Author      Version       Date        Changes
 * Kevin Feng   1.0        2014年11月7日       Created
 *
 * </pre>
 * @since 1.
 */
public class ServiceHelper implements OnCancelListener {

    private static final String TAG = ServiceHelper.class.getSimpleName();
    
    private iProgressDlg pdlg;
    
    private static final int SOCKET_TIMEOUT = 15 * 1000;
    
    private RetryPolicy retryPolicy;
    
    public ServiceHelper() {
        retryPolicy = new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
    
    /**
     * 
     * @param formType  请求格式
     * @param act 上下文
     * @param url 网址
     * @param iReq 请求接口
     * @param ires 应答接口 
     * @param reqCode 请求码
     * @param iCallBackListener 回调接口
     * @param loadingRes loading框提示
     * @param hasProgress 是否需要loading框
     * @param isForse 是否强制执行
     */
    private void sendRequestCommon(int formType, Context act, String url, IRequest iReq, IResponse ires, int reqCode, iCallBackListener iCallBackListener, String loadingRes, boolean hasProgress, IDialogVisitor visitor) {
        //before send
        if (iCallBackListener != null) {
            iCallBackListener.onPreExecute(reqCode);
        }
        
        //success return
        iProRespListener respLtr = new iProRespListener(act).setIResponse(ires)
                .setiCallback(iCallBackListener).setReqCode(reqCode);

        //error return
        iProRespErrorListener errorLtr = new iProRespErrorListener(act)
                .setiCallback(iCallBackListener).setReqCode(reqCode);
        
        //has loading progress
        if (hasProgress) {
            iProgressDlg pdlg = new iProgressDlg(act, loadingRes);
            pdlg.setCanceledOnTouchOutside(false);
            pdlg.setOnCancelListener(this);
            pdlg.show();
            
            if (visitor != null) {
                visitor.onHandleDialog(pdlg);
            }
            
            respLtr.setProgressDialog(pdlg);
            errorLtr.setProgressDialog(pdlg);
        }
        
        //to request
        LogUtil.e("url=" + url);
        
        LogUtil.e("req=" + (iReq != null ? RequestUtils.getPrintFormRequest(iReq.toRequest()) : null));
        
        Request<String> stringRequest = RequestFactory.getInstance().getVolleyRequest(formType, url, iReq != null ? iReq.toRequest() : null, respLtr, errorLtr);
        
        //set timeout
        stringRequest.setRetryPolicy(retryPolicy);
        AppController.getInstance().addToRequestQueue(stringRequest, TAG, act);
    }
    
    
    
    public void showProgressDlg(Context act, String loadingres, IDialogVisitor dlgVisitor) {
        pdlg = new iProgressDlg(act, loadingres);
        pdlg.setCanceledOnTouchOutside(false);
        pdlg.setOnCancelListener(this);
        if (dlgVisitor != null) {
            dlgVisitor.onHandleDialog(pdlg);
        }
        pdlg.show();
    }
    
    public void dismissProgreeDlg() {
        if (pdlg != null) {
            pdlg.dismiss();
        }
    }
    
    
    @Override
    public void onCancel(DialogInterface dialog) {
        AppController.getInstance().cancelPendingRequests(TAG);
    }
    
    /**
     * 无loading框的json请求.
     * @param act
     * @param url
     * @param iJsonReq
     * @param iJsonRes
     * @param reqCode
     * @param iCallBackListener
     */
    public void sendJsonRequestPart(Context act, String url, IRequest iReq, IResponse ires, int reqCode, iCallBackListener iCallBackListener) {
        sendRequestCommon(Form.JSON_STRING, act, url, iReq, ires, reqCode, iCallBackListener, null, false, null);
    }
    
    /**
     * 无loading框的get请求
     * @param act
     * @param url
     * @param iReq
     * @param ires
     * @param reqCode
     * @param iCallBackListener
     */
    public void sendGetRequestPart(Context act, String url, IResponse ires, int reqCode, iCallBackListener iCallBackListener) {
        sendRequestCommon(Form.GET, act, url, null, ires, reqCode, iCallBackListener, null, false, null);
    }
    
    
    /**
     * 有loading框的json请求.
     * @param act
     * @param loadingRes
     * @param url
     * @param iJsonReq
     * @param iJsonRes
     * @param reqCode
     * @param iCallBackListener
     */
    public void sendJsonRequest(Context act, String loadingRes, String url, IRequest iReq, IResponse ires, int reqCode, iCallBackListener iCallBackListener) {
        sendRequestCommon(Form.JSON_STRING, act, url, iReq, ires, reqCode, iCallBackListener, loadingRes, true, null);
    }
    
    /**
     * 有loading框的json请求, 不可取消.home键屏蔽
     * @param act
     * @param loadingRes
     * @param url
     * @param iJsonReq
     * @param iJsonRes
     * @param reqCode
     * @param iCallBackListener
     */
    public void sendJsonRequestForce(Context act, String loadingRes, String url, IRequest iReq, IResponse ires, int reqCode, iCallBackListener iCallBackListener, IDialogVisitor dlgVisitor) { 
        sendRequestCommon(Form.JSON_STRING, act, url, iReq, ires, reqCode, iCallBackListener, loadingRes, true, dlgVisitor);
    }
    
}
