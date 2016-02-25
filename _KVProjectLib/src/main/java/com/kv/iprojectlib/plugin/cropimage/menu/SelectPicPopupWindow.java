package com.kv.iprojectlib.plugin.cropimage.menu;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.kv.iprojectlib.R;

public class SelectPicPopupWindow extends PopupWindow {

    private View btnGallery;
    private View btnTakePhoto;

    private View mMenuView;
    private View mRootView;
    private SelectMenuListener mSelectMenuListener;

    public SelectPicPopupWindow(Activity context, View mRootView, SelectMenuListener selectMenuListener) {
        super(context);
        this.mRootView = mRootView;
        this.mSelectMenuListener = selectMenuListener;
        mMenuView = context.getLayoutInflater().inflate(R.layout.ip_ci_pop_gallery_menu, null);
        btnGallery = (Button) mMenuView.findViewById(R.id.btn_1);
        btnTakePhoto = (Button) mMenuView.findViewById(R.id.btn_2);

        btnGallery.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
                if (mSelectMenuListener != null) {
                    mSelectMenuListener.onSelectGallery();
                }
            }
        });
        
        btnTakePhoto.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                dismiss();
                if (mSelectMenuListener != null) {
                    mSelectMenuListener.onSelectTakePhoto();
                }
            }
        });
        
        
        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.ip_ci_menu_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    
    public void showMenu() {
        showAtLocation(mRootView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public static interface SelectMenuListener {
        public void onSelectGallery();
        public void onSelectTakePhoto();
    }
}
