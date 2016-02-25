package com.kv.iprojectdemo;

import com.kv.iprojectdemo.cropimage.CropImageTestAct;
import com.kv.iprojectdemo.db.OrmliteMainAct;
import com.kv.iprojectdemo.dialog.DialogTestAct;
import com.kv.iprojectdemo.gestureimage.GestureImageAct;
import com.kv.iprojectdemo.http.HttpTestAct;
import com.kv.iprojectdemo.pull2refresh.Pull2RefreshAct;
import com.kv.iprojectdemo.slideview.SlideViewTestAct;
import com.kv.iprojectdemo.zxing.ZxingAct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainAct extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn1: //ormlite
            startActivity(new Intent(MainAct.this, OrmliteMainAct.class));
            break;
        case R.id.btn2: //zxing
            startActivity(new Intent(MainAct.this, ZxingAct.class));
            break;
        case R.id.btn3: //crop image
            startActivity(new Intent(MainAct.this, CropImageTestAct.class));
            break;
        case R.id.btn4: //gesture image
            startActivity(new Intent(MainAct.this, GestureImageAct.class));
            break;
        case R.id.btn5: //pull2refresh
            startActivity(new Intent(MainAct.this, Pull2RefreshAct.class));
            break;
        case R.id.btn6: //swipe listView
            startActivity(new Intent(MainAct.this, SlideViewTestAct.class));
            break;
        case R.id.btn7: //http
            startActivity(new Intent(MainAct.this, HttpTestAct.class));
            break;
        case R.id.btn8: //dialog
            startActivity(new Intent(MainAct.this, DialogTestAct.class));
            break;
        default:
            break;
        }
    }

}
