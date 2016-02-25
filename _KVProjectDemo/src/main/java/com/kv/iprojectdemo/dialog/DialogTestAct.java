package com.kv.iprojectdemo.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kv.iprojectdemo.R;
import com.kv.iprojectdemo.dialog.theme.Dlg1;
import com.kv.iprojectdemo.dialog.theme.Dlg2;
import com.kv.iprojectdemo.dialog.theme.Dlg3;
import com.kv.iprojectlib.ui.dialog.DialogCfg;
import com.kv.iprojectlib.ui.dialog.DialogUtils;

public class DialogTestAct extends Activity implements OnClickListener {

    private Button dlg1;
    private Button dlg2;
    private Button dlg3;
    private Button dlg4;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogtest);
        dlg1 = (Button) findViewById(R.id.dlg_1);
        dlg2 = (Button) findViewById(R.id.dlg_2);
        dlg3 = (Button) findViewById(R.id.dlg_3);
        dlg4 = (Button) findViewById(R.id.dlg_4);
        dlg1.setOnClickListener(this);
        dlg2.setOnClickListener(this);
        dlg3.setOnClickListener(this);
        dlg4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.dlg_1:
            DialogCfg.dialogStruct = new Dlg1();
            DialogUtils.showConfirmDlg(this, "标题", "这是测试内容", "确定", null, "取消", null);
            break;
        case R.id.dlg_2:
            DialogCfg.dialogStruct = new Dlg2();
            DialogUtils.showConfirmDlg(this, "标题", "这是测试内容", "确定", null, "取消", null);
            break;
        case R.id.dlg_3:
            DialogCfg.dialogStruct = new Dlg3();
            DialogUtils.showConfirmDlg(this, "标题", "这是测试内容", "确定", null, "取消", null);
            break;
        case R.id.dlg_4:
            DialogCfg.dialogStruct = new Dlg3();
            DialogUtils.showMsgDlg(this, null, "这是测试内容", "确定", null);
            break;
        default:
            break;
        }
    }

}
