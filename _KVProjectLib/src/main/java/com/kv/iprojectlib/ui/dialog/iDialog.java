package com.kv.iprojectlib.ui.dialog;

import android.app.Dialog;
import android.content.Context;

import com.kv.iprojectlib.R;


public class iDialog extends Dialog {
    
    public iDialog(Context context) {
        super(context, R.style.ip_dlg);
    }
    

    public static interface iDlgBtnOnclickListener {
        public void onClick(iDialog dialog);
    }
}
