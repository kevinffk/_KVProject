package com.kv.iprojectlib.core.http.expand.request;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class RequestFactory {

    public static RequestFactory instance = null;
    
    private RequestFactory() {
        
    }
    
    public static synchronized RequestFactory getInstance() {
        if (instance == null) {
            instance = new RequestFactory();
        }
        return instance;
    }
    
    public static interface Form {
        public static final int STRING = 0;
        public static final int JSON_STRING = 1;
        public static final int GET = 2;
    }
    
    public Request<String> getVolleyRequest(int type, String url, final Object requestBody, Listener listener, ErrorListener errorListener) {
        if (Form.STRING == type) {
            return new StringRequest(Method.POST, url, listener, errorListener) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return (Map<String, String>)requestBody;
                }
                
            };
        } else if (Form.JSON_STRING == type) {
            return new JsonStringRequest(url, (String)requestBody, listener, errorListener);
        } else if (Form.GET == type) {
            return new StringRequest(url, listener, errorListener);
        }
        return null;
    }
}
