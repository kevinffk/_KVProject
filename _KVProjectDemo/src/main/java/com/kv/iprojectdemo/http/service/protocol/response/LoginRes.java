package com.kv.iprojectdemo.http.service.protocol.response;


public class LoginRes extends BaseRes{

    private static final long serialVersionUID = 6346710130161070245L;

    public LoginRes() {
        super(USERlOGIN_RESP);
    }
    
    public body body = new body();
    
    public class body {
        public int type;
        public String userNo;
        public String sessionId;
        public String time;
        public String terminalNo;
    }
}
