package com.kv.iprojectlib.plugin.bluetooth.device.ui;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kv.iprojectlib.plugin.bluetooth.device.syscfg.Constant;
import com.kv.iprojectlib.plugin.bluetooth.device.ui.utils.DisplayUtil;

public class DeviceListDlg {
    
    public ListView matchPairListView;
    
    public ListView scanPairListView;
    
    public Button reDiscoveryBtn;
    
    public TextView matchTextView;
    
    public Dialog dialog;
    
    public ProgressBar progressBar;
    
    public DeviceListDlg(Activity mAct) {
        dialog = new Dialog(mAct);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(getContentView(mAct));
    }

    public void showDeviceListDlg() {
        dialog.show();
    }
    
    public void dismissDlg() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private View getContentView(Activity mAct) {
        
        LinearLayout contentView = new LinearLayout(mAct);
        ViewGroup.LayoutParams cvlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        contentView.setLayoutParams(cvlp);
        contentView.setOrientation(LinearLayout.VERTICAL);
        
        int padding8 = DisplayUtil.dip2px(8, mAct);
        int padding6 = DisplayUtil.dip2px(6, mAct);
        int pbsize = DisplayUtil.dip2px(18, mAct);
        
        //relative ly
        RelativeLayout relativeLayout = new RelativeLayout(mAct);
        relativeLayout.setPadding(padding6, padding6, padding6, padding6);
        relativeLayout.setBackgroundColor(Color.parseColor("#0099ff"));
        LinearLayout.LayoutParams rllp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(rllp);
        contentView.addView(relativeLayout);
        
        //dialog title
        TextView titleTV = new TextView(mAct);
        RelativeLayout.LayoutParams ttlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ttlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        titleTV.setLayoutParams(ttlp);
        titleTV.setTextSize(19);
        titleTV.setTextColor(Color.WHITE);
        titleTV.setText(Constant.DEVICE_TITLE);
        relativeLayout.addView(titleTV);
        
        progressBar = new ProgressBar(mAct);
        RelativeLayout.LayoutParams pblp = new RelativeLayout.LayoutParams(pbsize, pbsize);
        pblp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        pblp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        progressBar.setLayoutParams(pblp);
        relativeLayout.addView(progressBar);
        
        //match pair title
        matchTextView = new TextView(mAct);
        matchTextView.setVisibility(View.GONE);
        LinearLayout.LayoutParams mtlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        matchTextView.setPadding(padding8, 0, 0, 0);
        matchTextView.setLayoutParams(mtlp);
        matchTextView.setBackgroundColor(Color.parseColor("#777777"));
        matchTextView.setTextColor(Color.WHITE);
        matchTextView.setText(Constant.MATCH_PAIR_TITLE);
        contentView.addView(matchTextView);
        
        //match pair list
        matchPairListView = new ListView(mAct);
        LinearLayout.LayoutParams mplp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        matchPairListView.setLayoutParams(mplp);
        contentView.addView(matchPairListView);
        
        //match pair title
        TextView scanTextView = new TextView(mAct);
        LinearLayout.LayoutParams stlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scanTextView.setPadding(padding8, 0, 0, 0);
        scanTextView.setLayoutParams(stlp);
        scanTextView.setBackgroundColor(Color.parseColor("#777777"));
        scanTextView.setTextColor(Color.WHITE);
        scanTextView.setText(Constant.SCAN_PAIR_TITLE);
        contentView.addView(scanTextView);
        
        //scan pair list
        scanPairListView = new ListView(mAct);
        LinearLayout.LayoutParams splp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scanPairListView.setLayoutParams(splp);
        contentView.addView(scanPairListView);
        
        //button
        reDiscoveryBtn = new Button(mAct);
        LinearLayout.LayoutParams rblp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        reDiscoveryBtn.setLayoutParams(rblp);
        reDiscoveryBtn.setText(Constant.RE_DISCOVERY_BTN);
        contentView.addView(reDiscoveryBtn);
        
        return contentView;
    }
}
