package com.kv.iprojectdemo.http.service.protocol.request;

import com.kv.iprojectdemo.http.service.protocol.CommonId;
import com.kv.iprojectlib.core.http.inf.impl.BaseJsonReq;

public class BaseReq extends BaseJsonReq implements CommonId {

    private static final long serialVersionUID = -7802428138791515586L;

    public int commandId;

    public String ResultMessage;
    
    public BaseReq(int commonId) {
        this.commandId = commonId;
    }

}
