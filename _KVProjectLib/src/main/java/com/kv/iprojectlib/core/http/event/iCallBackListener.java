package com.kv.iprojectlib.core.http.event;

import com.android.volley.VolleyError;
import com.kv.iprojectlib.core.http.inf.IResponse;

public interface iCallBackListener {

    void onPreExecute(int reqcode);
    
    void onPostExecute(IResponse iResponse, int reqCode);
    
    void onErrorExecute(VolleyError volleyError, String errorMsg, int reqCode);
}
