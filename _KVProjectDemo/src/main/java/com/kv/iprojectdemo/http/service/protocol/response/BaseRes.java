package com.kv.iprojectdemo.http.service.protocol.response;

import com.kv.iprojectdemo.http.service.protocol.CommonId;
import com.kv.iprojectlib.core.http.inf.impl.BaseJsonRes;


public class BaseRes extends BaseJsonRes implements CommonId{

    private static final long serialVersionUID = 4653601483660771412L;
    
    private static final String TAG = BaseRes.class.getSimpleName();
    
    public int commandId;

    public String errorMessage;
    
    public BaseRes(int commonId) {
        this.commandId = commonId;
    }
}
