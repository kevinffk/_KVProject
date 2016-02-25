package com.kv.iprojectdemo.cropimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.kv.iprojectdemo.R;
import com.kv.iprojectlib.plugin.cropimage.menu.CropPhotoHelper;
import com.kv.iprojectlib.plugin.cropimage.menu.SelectPicPopupWindow;
import com.kv.iprojectlib.plugin.cropimage.menu.SelectPicPopupWindow.SelectMenuListener;

public class CropImageTestAct extends Activity {

    private Button btn1;
    private ImageView iv;
    
    private CropPhotoHelper cropPhotoHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image_test);
        btn1 = (Button) findViewById(R.id.btn1);
        iv = (ImageView) findViewById(R.id.iv);
        
        cropPhotoHelper  = new CropPhotoHelper(this, 1, 1, 300, 300, new CropPhotoHelper.iGetCropImageListener() {
            
            @Override
            public void getCropImagePath(int tag, String path) {
                iv.setImageBitmap(BitmapFactory.decodeFile(path));
            }
        });
        
        btn1.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                new SelectPicPopupWindow(CropImageTestAct.this, findViewById(R.id.rootly), new SelectMenuListener() {
                    
                    @Override
                    public void onSelectTakePhoto() {
                        cropPhotoHelper.takePicture();
                    }
                    
                    @Override
                    public void onSelectGallery() {
                        cropPhotoHelper.openGallery();
                    }
                }).showMenu();
            }
        });
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropPhotoHelper.onActivityResult(requestCode, resultCode, data);
    }
}
