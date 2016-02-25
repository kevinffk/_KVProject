package com.kv.iprojectlib.core.http.inf.impl;

import java.io.Serializable;

import com.google.gson.Gson;
import com.kv.iprojectlib.core.http.inf.IResponse;

public class BaseJsonRes implements Serializable, IResponse {

    private static final long serialVersionUID = 3646472762651893232L;

    @Override
    public IResponse parseResponse(String responseStr) {
        IResponse iJsonResponse = new Gson().fromJson(responseStr, this.getClass());
        return iJsonResponse;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    
}