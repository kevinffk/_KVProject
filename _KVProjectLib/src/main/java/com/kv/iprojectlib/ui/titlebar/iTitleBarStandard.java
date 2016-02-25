package com.kv.iprojectlib.ui.titlebar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public interface iTitleBarStandard {

    public void handleLeftView(View leftView);
    
    public void handleRightView(View rightView);
    
    public void handleLeftTextView(TextView leftTV);
    
    public void handleRightTextView(TextView rigthTV);
    
    public void handleLeftImageView(ImageView leftIV);
    
    public void handleRigthImageView(ImageView rightIV);
    
    public void handleTitleTextView(TextView titleTV);
    
    public void handleTitleRootView(View rootView);
}
