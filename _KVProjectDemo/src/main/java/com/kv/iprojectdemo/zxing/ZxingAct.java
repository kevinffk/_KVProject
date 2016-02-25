package com.kv.iprojectdemo.zxing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kv.iprojectdemo.R;
import com.kv.iprojectlib.plugin.qrcodescan.MipcaActivityCapture;

public class ZxingAct extends Activity implements OnClickListener{

    private Button btn1;
    private TextView tv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxing_test);
        btn1 = (Button) findViewById(R.id.btn1);
        tv = (TextView) findViewById(R.id.tv);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn1:
            startActivityForResult(new Intent(ZxingAct.this, MipcaActivityCapture.class), 1);
            break;
        default:
            break;
        }
        
    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == MipcaActivityCapture.RESULT_OK) {
            String result = data.getStringExtra(MipcaActivityCapture.RESULT);
            tv.setText("result=" + result);
        }
    }

    
}
