package com.kv.iprojectlib.core.http.inf.impl;

import java.io.Serializable;

import com.google.gson.Gson;
import com.kv.iprojectlib.core.http.inf.IRequest;

public class BaseJsonReq implements IRequest, Serializable {

    private static final long serialVersionUID = 4079841858880356977L;

    @Override
    public String toRequest() {
        return new Gson().toJson(this);
    }
}
