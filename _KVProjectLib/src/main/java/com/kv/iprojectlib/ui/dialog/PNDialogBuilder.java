package com.kv.iprojectlib.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kv.iprojectlib.R;
import com.kv.iprojectlib.ui.dialog.iDialog.iDlgBtnOnclickListener;
import com.kv.iprojectlib.ui.dialog.theme.DialogThemeProxy;
import com.kv.iprojectlib.ui.dialog.theme.iDialogStruct;

public class PNDialogBuilder{

    public iDialog pnDialog;
    public View contentLy;
    public View titleBar;
    public TextView titleTV;
    public TextView msgTV;
    public Button btn1;
    public Button btn2;
    private iDlgBtnOnclickListener listener1 = null;
    private iDlgBtnOnclickListener listener2 = null;
    private iDialogStruct dialogStruct;
    
    
    public PNDialogBuilder createPNDialog(Context context) {
        dialogStruct = DialogCfg.dialogStruct;
        
        pnDialog = new iDialog(context);
        pnDialog.setContentView(R.layout.ip_dialog);
        
        contentLy = pnDialog.findViewById(R.id.content_ly);
        titleBar = pnDialog.findViewById(R.id.titlebar);
        titleTV = (TextView) pnDialog.findViewById(R.id.title);
        
        msgTV = (TextView) pnDialog.findViewById(R.id.tv);
        
        btn1 = (Button) pnDialog.findViewById(R.id.btn_1);
        btn2 = (Button) pnDialog.findViewById(R.id.btn_2);
        
        //是否有对话框
        if (!dialogStruct.hasTitleBar()) {
            titleBar.setVisibility(View.GONE);
        }
        
        //内容背景色
        contentLy.setBackground(DialogThemeProxy.getDialogBg(context, dialogStruct));
        
        //标题栏背景色
        titleBar.setBackground(DialogThemeProxy.getTitleBarBg(context, dialogStruct));

        //内容文字色
        msgTV.setTextColor(dialogStruct.getMainTxtColor());
        
        //内容文字大小
        msgTV.setTextSize(dialogStruct.getMainTxtSize());
        
        //主按钮背景色        
        btn1.setBackground(DialogThemeProxy.getPositionDrawable(context, dialogStruct));
        btn1.setTextColor(DialogThemeProxy.getPostionTxtColor(context, dialogStruct));
        
        //副按钮背景色
        btn2.setBackground(DialogThemeProxy.getNegativeDrawable(context, dialogStruct));
        btn2.setTextColor(DialogThemeProxy.getNegativeTxtColor(context, dialogStruct));
        
        
        btn1.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                pnDialog.dismiss();
                if (listener1 != null) {
                    listener1.onClick(pnDialog);
                }
            }
        });
        
        btn2.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                pnDialog.dismiss();
                if (listener2 != null) {
                    listener2.onClick(pnDialog);
                }
            }
        });
        return this;
    }
    
    public iDialog getPNDialog() {
        return pnDialog;
    }

    public PNDialogBuilder setBtn1OnclickListener(iDlgBtnOnclickListener btnOnclickListener) {
        this.listener1 = btnOnclickListener;
        return this;
    }
    
    public PNDialogBuilder setBtn2OnclickListener(iDlgBtnOnclickListener btnOnclickListener) {
        this.listener2 = btnOnclickListener;
        return this;
    }
    
    public PNDialogBuilder setMessage(String text) {
        msgTV.setText(text);
        return this;
    }
    
    public PNDialogBuilder setTitle(String text) {
        titleTV.setText(text);
        return this;
    }
    
    public PNDialogBuilder setSingleButton() {
        btn2.setVisibility(View.GONE);
        return this;
    }
    
    public PNDialogBuilder setBtn1Text(String text) {
        btn1.setText(text);
        return this;
    }
    
    public PNDialogBuilder setBtn2Text(String text) {
        btn2.setText(text);
        return this;
    }
}
