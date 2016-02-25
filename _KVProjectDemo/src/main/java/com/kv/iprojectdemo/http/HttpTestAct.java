package com.kv.iprojectdemo.http;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.kv.iprojectdemo.R;
import com.kv.iprojectdemo.http.service.manager.MyManager;
import com.kv.iprojectdemo.http.service.protocol.request.LoginReq;
import com.kv.iprojectdemo.http.service.protocol.response.LoginRes;
import com.kv.iprojectlib.core.http.event.iCallBackListener;
import com.kv.iprojectlib.core.http.inf.IResponse;

public class HttpTestAct extends Activity implements iCallBackListener{

    private Button btn;
    private TextView tv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_test);
        
        btn = (Button) findViewById(R.id.btn1);
        tv = (TextView) findViewById(R.id.tv);
        btn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                LoginReq loginReq = new LoginReq();
                loginReq.body.userNo = "1000002";
                loginReq.body.pwd = "123456";
                loginReq.body.terminalNo = "123456";
                MyManager.getInstance().login(HttpTestAct.this, loginReq, 1, HttpTestAct.this);
            }
        });
    }

    @Override
    public void onPreExecute(int reqcode) {
        
    }

    @Override
    public void onPostExecute(IResponse iResponse, int reqCode) {
        if (reqCode == 1&& iResponse != null && iResponse instanceof LoginRes) {
            LoginRes loginRes = (LoginRes) iResponse;
            tv.setText(loginRes.toString());
        }
    }

    @Override
    public void onErrorExecute(VolleyError volleyError, String errorMsg, int reqCode) {
        
    }

}
