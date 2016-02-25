package com.kv.iprojectlib.ui.dialog;

import android.content.Context;

import com.kv.iprojectlib.R;
import com.kv.iprojectlib.ui.dialog.iDialog.iDlgBtnOnclickListener;


public class DialogUtils {
    
    /**
     * Confirm dialog.
     * @param cxt
     * @param title
     * @param text
     * @param btn1Txt
     * @param lis1
     * @param btn2Txt
     * @param lis2
     */
    public static void showConfirmDlg(Context cxt, String title, String text, String btn1Txt, iDlgBtnOnclickListener lis1, String btn2Txt, iDlgBtnOnclickListener lis2) {
        iDialog pnDialog = new PNDialogBuilder()
        .createPNDialog(cxt)
        .setTitle(title)
        .setMessage(text)
        .setBtn1Text(btn1Txt)
        .setBtn1OnclickListener(lis1)
        .setBtn2Text(btn2Txt)
        .setBtn2OnclickListener(lis2).getPNDialog();
        pnDialog.setCancelable(false);
        pnDialog.show();
    } 
    
    public static void showConfirmDlg(Context cxt, String text, iDlgBtnOnclickListener lis1) {
        showConfirmDlg(cxt, cxt.getString(R.string.ip_dialog_default_title), text, cxt.getString(R.string.ip_dialog_sure), lis1, cxt.getString(R.string.ip_dialog_cancel), null);
    } 
    
    public static void showMsgDlg(Context cxt, String title, String text, String btn1Txt, iDlgBtnOnclickListener lis1) {
        iDialog pnDialog = new PNDialogBuilder()
        .createPNDialog(cxt)
        .setTitle(title)
        .setMessage(text)
        .setBtn1Text(btn1Txt)
        .setBtn1OnclickListener(lis1)
        .setSingleButton()
        .getPNDialog();
        pnDialog.setCancelable(false);
        pnDialog.show();
    }
    
    public static void showMsgDlg(Context cxt, int titleRes, String text, String btn1Txt, iDlgBtnOnclickListener lis1) {
        showMsgDlg(cxt, cxt.getString(titleRes), text, btn1Txt, lis1);
    }
    
    public static void showMsgDlg(Context cxt, int titleRes, String text, iDlgBtnOnclickListener lis1) {
        showMsgDlg(cxt, cxt.getString(titleRes), text, cxt.getString(R.string.ip_dialog_sure), lis1);
    }
    
    public static void showMsgDlg(Context cxt, String text, iDlgBtnOnclickListener lis1) {
        showMsgDlg(cxt, R.string.ip_dialog_default_title, text, lis1);
    }
}
