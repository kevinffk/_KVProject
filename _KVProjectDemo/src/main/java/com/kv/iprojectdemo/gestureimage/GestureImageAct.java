package com.kv.iprojectdemo.gestureimage;

import android.app.Activity;
import android.os.Bundle;

import com.kv.iprojectdemo.R;
import com.kv.iprojectlib.plugin.gestureimageview.GestureImageView;

public class GestureImageAct extends Activity {

    private GestureImageView gestureImageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_img_test);
        gestureImageView = (GestureImageView) findViewById(R.id.iv);
        gestureImageView.setImageResource(R.drawable.gest_img);
    }

}
