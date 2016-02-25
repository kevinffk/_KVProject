package com.kv.iprojectdemo.http.service.protocol.request;




public class LoginReq extends BaseReq{

    private static final long serialVersionUID = 418323743530572059L;

    public LoginReq() {
       super(USERlOGIN);
    }
    
    
    public body body = new body();
    
    public class body {
        public String userNo;
        public String pwd;
        public String terminalNo;
    }
}
