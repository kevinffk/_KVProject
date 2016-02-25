package com.kv.iprojectlib.ui.titlebar;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.kv.iprojectlib.R;
import com.kv.iprojectlib.ui.statusbar.StatusBarUtils;
import com.kv.iprojectlib.utils.DisplayUtil;

public class TitlebarBuilder {

    private Activity mCxt = null;
    private int mRootViewId;    
    private View mContentLy = null;
    private TextView mTitleView = null;
    private View mRootView = null;
    private View mStatusView = null;
    private View mLeftView = null;
    private View mRightView = null;
    private TextView mLeftTV = null;
    private TextView mRightTV = null;
    private ImageView mLeftIV = null;
    private ImageView mRightIV = null;
    
    public static final int TITLE_GRAVITY_LEFT = 0;
    
    public static final int TITLE_GRAVITY_CENTER = 1;
    
    public static final int TITLE_GRAVITY_RIGHT = 2;
    
    private TiTlebarCallback mTiTlebarCallback = null;
    
    private iTitleBarStandard mITitleBarStandard = null;
    
    private int titleBarTotalHeight = 0;
    
    public TitlebarBuilder(Activity cxt, int rootViewId) {
        this.mCxt = cxt;
        this.mRootViewId = rootViewId;
        init();
    }
    
    public TitlebarBuilder setTitlebarCallback(TiTlebarCallback tiTlebarCallback) {
        this.mTiTlebarCallback = tiTlebarCallback;
        return this;
    }
    
    public TitlebarBuilder setiTitleBarStandard(iTitleBarStandard iTitleBarStandard) {
        this.mITitleBarStandard = iTitleBarStandard;
        return this;
    }
    
    public TitlebarBuilder setSameStatusBar(boolean isSameStatusBar) {
        int height = StatusBarUtils.getTatusHeight(mCxt);
        if (isSameStatusBar) {
            StatusBarUtils.setTranslucentStatus(mCxt);
            mStatusView.getLayoutParams().height = height;
        }
        titleBarTotalHeight = height + DisplayUtil.dip2px(48, mCxt);
        return this;
    }
    
    
    public TitlebarBuilder build() {

        if (mITitleBarStandard != null) {
            mITitleBarStandard.handleLeftImageView(mLeftIV);
            mITitleBarStandard.handleLeftTextView(mLeftTV);
            mITitleBarStandard.handleLeftView(mLeftView);
            
            mITitleBarStandard.handleRightTextView(mRightTV);
            mITitleBarStandard.handleRigthImageView(mRightIV);
            mITitleBarStandard.handleRightView(mRightView);
            
            mITitleBarStandard.handleTitleTextView(mTitleView);
            
            mITitleBarStandard.handleTitleRootView(mRootView);
        }
        return this;
    }
    
    private void init() {
        mRootView = mCxt.findViewById(mRootViewId).findViewById(R.id.ip_title_rl);
        if (mRootView != null) {
            mContentLy = mRootView.findViewById(R.id.content_ly);
            mLeftView = mRootView.findViewById(R.id.ip_left_btn);
            mRightView = mRootView.findViewById(R.id.ip_right_btn);
            mStatusView = mRootView.findViewById(R.id.ip_status_ly);
            mTitleView = (TextView) mRootView.findViewById(R.id.ip_title);
            if (mLeftView != null) {
                mLeftIV = (ImageView) mLeftView.findViewById(R.id.ip_left_btn_iv);
                mLeftTV = (TextView) mLeftView.findViewById(R.id.ip_left_btn_txt);
                mLeftView.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        if (mTiTlebarCallback != null) {
                            mTiTlebarCallback.onLeftBtnClick(mLeftView);
                        }
                    }
                });
            }
            if (mRightView != null) {
                mRightIV = (ImageView) mRightView.findViewById(R.id.ip_right_btn_iv);
                mRightTV = (TextView) mRightView.findViewById(R.id.ip_right_btn_txt);
                mRightView.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        if (mTiTlebarCallback != null) {
                            mTiTlebarCallback.onRigtBtnClick(mRightView);
                        }
                    }
                });
            }
        }
    }
    
    public TitlebarBuilder hasTitlebar(boolean hasTitlebar) {
        if (mContentLy != null) {
            if (hasTitlebar) {
                mContentLy.setVisibility(View.VISIBLE);
            } else {
                mContentLy.setVisibility(View.GONE);
            }
        }
        return this;
    }
    
    public TitlebarBuilder setTitleGravity(int titleGravity) {
        RelativeLayout.LayoutParams lp = (LayoutParams) mTitleView.getLayoutParams();
        if (titleGravity == TITLE_GRAVITY_CENTER) {
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (titleGravity == TITLE_GRAVITY_LEFT) {
            lp.addRule(RelativeLayout.RIGHT_OF, mLeftView.getId());
        } else if (titleGravity == TITLE_GRAVITY_RIGHT) {
            lp.addRule(RelativeLayout.LEFT_OF, mRightView.getId());
        }
        return this;
    }

    public View getmLeftView() {
        return mLeftView;
    }

    public View getmRightView() {
        return mRightView;
    }

    public TextView getmLeftTV() {
        return mLeftTV;
    }

    public TextView getmRightTV() {
        return mRightTV;
    }

    public ImageView getmLeftIV() {
        return mLeftIV;
    }

    public ImageView getmRightIV() {
        return mRightIV;
    }
    
    public boolean hasTitleBar() {
        return true;
    }

    public int getTitleBarTotalHeight() {
        return titleBarTotalHeight;
    } 
    
    
}
