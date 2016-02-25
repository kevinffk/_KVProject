package com.kv.iprojectlib.ui.progressdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.kv.iprojectlib.R;


public class iProgressDlg extends Dialog {

    private String message;
    private TextView tv;
    
    public iProgressDlg(Context context, String message) {
        super(context, R.style.ip_loading_dialog);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_progressdlg);
        tv = (TextView) findViewById(R.id.tv_msg);
        tv.setText(message);
    }
    
    public void setMessage(String message) {
        tv.setText(message);
    }
}
