package com.kv.iprojectlib.plugin.bluetooth.device.ui;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kv.iprojectlib.plugin.bluetooth.device.ui.utils.DisplayUtil;

public class DeviceListItemView {

    public static final int DEVICE_NAME = 0x101;
    public static final int DEVICE_MAC = 0x102;
    
    public View getView(Activity mAct) {
        LinearLayout contentView = new LinearLayout(mAct);
        AbsListView.LayoutParams cvLp = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        contentView.setLayoutParams(cvLp);
        contentView.setOrientation(LinearLayout.VERTICAL);
        
        int padding5 = DisplayUtil.dip2px(5, mAct);
        
        TextView deviceNameTV = new TextView(mAct);
        deviceNameTV.setId(DEVICE_NAME);
        LinearLayout.LayoutParams dnlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        deviceNameTV.setPadding(padding5, 0, 0, 0);
        deviceNameTV.setLayoutParams(dnlp);
        deviceNameTV.setTextColor(Color.WHITE);
        contentView.addView(deviceNameTV);
        
        TextView deviceMacTV = new TextView(mAct);
        deviceMacTV.setId(DEVICE_MAC);
        LinearLayout.LayoutParams dmlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        deviceMacTV.setPadding(padding5, 0, 0, 0);
        deviceMacTV.setLayoutParams(dmlp);
        deviceMacTV.setTextColor(Color.WHITE);
        contentView.addView(deviceMacTV);
        
        return contentView;
    }
}
