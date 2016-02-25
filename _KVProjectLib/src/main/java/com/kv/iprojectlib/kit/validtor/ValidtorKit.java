package com.kv.iprojectlib.kit.validtor;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.kv.iprojectlib.R;
import com.kv.iprojectlib.ui.toast.iToastUtils;

public class ValidtorKit {

    public Activity mAct = null;
    public boolean isValid = true;
    protected boolean isRectToast = false;
    
    public ValidtorKit(Activity act) {
        this.mAct = act;
    }
    
    public ValidtorKit showRectToast() {
        this.isRectToast = true;
        return this;
    }
    
    /**
     * 不为空.
     * @param tipRes
     * @return
     */
    public ValidtorKit notNull(View view, String targetText, int labelRes) {
        if (isValid && TextUtils.isEmpty(targetText)) {
            isValid = false;
            showToast(R.string.ip_va_not_null, labelRes);
            view.requestFocus();
        }
        return this;
    }
    
    /**
     * 显示提示.
     * @param labelResPri
     * @param labelRes
     */
    private void showToast(int labelResPri, int labelRes) {
        if (!isRectToast) {
            iToastUtils.showToast(mAct, mAct.getString(labelResPri, mAct.getString(labelRes)));
        } else {
            iToastUtils.showRectToastWarn(mAct, mAct.getString(labelResPri, mAct.getString(labelRes)));
        }
    }
    
    /**
     * 长度相同.
     * @param targetText
     * @param lenth
     * @param labelRes
     * @return
     */
    public ValidtorKit equalLength(View view, String targetText, int lenth, int labelRes) {
        if (isValid && (targetText == null || targetText.length() != lenth)) {
            isValid = false;
            showToast(R.string.ip_va_eq_length, labelRes);
            view.requestFocus();
        }
        return this;
    }
    
    /**
     * 长度至少为.
     * @param targetText
     * @param lenth
     * @param labelRes
     * @return
     */
    public ValidtorKit lessLength(View view, String targetText, int lessLenth, int labelRes) {
        if (isValid && (targetText == null || targetText.length() < lessLenth)) {
            isValid = false;
            showToast(R.string.ip_va_less_length, labelRes);
            view.requestFocus();
        }
        return this;
    }
    
    /**
     * 判断值相同.
     * @param targetTxt1
     * @param targetTxt2
     * @param labelRes
     * @return
     */
    public ValidtorKit equalItem(View view, String targetTxt1, String targetTxt2, int labelRes) {
        if (isValid && !(targetTxt1 != null && targetTxt1.equals(targetTxt2))) {
            isValid = false;
            showToast(R.string.ip_va_eq_length, labelRes);
            view.requestFocus();
        }
        return this;
    }
    
    /**
     * 大于0.
     * @param view
     * @param targetTx1
     * @param labelRes
     * @return
     */
    public ValidtorKit biggerZero(View view, String targetTx1, int labelRes) {
        if (isValid) {
            double amount = Double.parseDouble(targetTx1);
            if (amount <= 0) {
                isValid = false;
                showToast(R.string.ip_val_bigger_zero, labelRes);
                view.requestFocus();
            }
        }
        return this;
    }
    
    
    public boolean getIsValid() {
        return isValid;
    }
}
