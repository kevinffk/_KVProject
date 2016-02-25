package com.kv.iprojectlib.core.http.expand.request;

import java.io.UnsupportedEncodingException;

import android.text.TextUtils;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

public class JsonStringRequest extends JsonRequest<String> {

    
    public JsonStringRequest(String url, String requestBody, Response.Listener listener, ErrorListener errorListener) {
        super(TextUtils.isEmpty(requestBody) ? Method.GET : Method.POST, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
    

}
