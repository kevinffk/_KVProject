package com.kv.iprojectlib.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.kv.iprojectlib.R;
import com.kv.iprojectlib.core.http.event.iCallBackListener;
import com.kv.iprojectlib.core.http.inf.IResponse;
import com.kv.iprojectlib.ui.titlebar.TiTlebarCallback;
import com.kv.iprojectlib.ui.titlebar.TitlebarBuilder;
import com.kv.iprojectlib.ui.titlebar.iTitleBarStandard;
import com.kv.iprojectlib.ui.toast.iToastUtils;

public abstract class IProBaseActivity extends Activity implements iCallBackListener, iTitleBarStandard, TiTlebarCallback {
    
    protected TitlebarBuilder mTitleBarBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IProApplication.listActivites.add(this);
        setContentView(getContentViewResource());
        initView();
        initTitlebar();
        initData();
    }
    
    public void initTitlebar() {
        mTitleBarBuilder = new TitlebarBuilder(this, R.id.titlebar)
            .setiTitleBarStandard(this)
            .setTitlebarCallback(this)
            .setSameStatusBar(true)
            .hasTitlebar(IProBaseActivity.this.hasTitleBar())
            .setTitleGravity(getTitleGravity())
            .build();
    }
    
    public abstract int getContentViewResource();
    
    public abstract void initView();
    
    public abstract void initData();
    
    public abstract String getTitleText();

    public boolean hasTitleBar() {
        return true;
    }
    
    public int getTotalTitlebarHeight() {
        return mTitleBarBuilder.getTitleBarTotalHeight();
    }
    
    public int getTitleGravity() {
        return TitlebarBuilder.TITLE_GRAVITY_CENTER;
    }
    
    @Override
    public void onRigtBtnClick(View rightView) {
        
    }

    @Override
    public void onLeftBtnClick(View leftView) {
        
    }

    @Override
    public void handleLeftView(View leftView) {
        
    }

    @Override
    public void handleRightView(View rightView) {
        
    }

    @Override
    public void handleLeftTextView(TextView leftTV) {
        leftTV.setTextColor(Color.parseColor("#ffffff"));
        leftTV.setTextSize(16);
    }

    @Override
    public void handleRightTextView(TextView rigthTV) {
        rigthTV.setTextColor(Color.parseColor("#ffffff"));
        rigthTV.setTextSize(16);
    }

    @Override
    public void handleLeftImageView(ImageView leftIV) {
        
    }

    @Override
    public void handleRigthImageView(ImageView rightIV) {
        
    }

    @Override
    public void handleTitleTextView(TextView titleTV) {
        titleTV.setTextColor(Color.parseColor("#ffffff"));
        titleTV.setTextSize(18);
        titleTV.setText(getTitleText());
    }

    @Override
    public void handleTitleRootView(View rootView) {
        rootView.setBackgroundColor(Color.parseColor("#333333"));
    }

    @Override
    public void onPreExecute(int reqcode) {
        
    }

    public TitlebarBuilder getmTitleBarBuilder() {
        return mTitleBarBuilder;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPostExecute(IResponse iResponse, int reqCode) {
        
    }

    @Override
    public void onErrorExecute(VolleyError volleyError, String errorMsg, int reqCode) {
        iToastUtils.showToast(this, errorMsg); 
    }

}
