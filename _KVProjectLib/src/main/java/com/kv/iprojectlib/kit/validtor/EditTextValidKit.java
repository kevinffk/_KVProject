package com.kv.iprojectlib.kit.validtor;

import android.app.Activity;
import android.widget.EditText;

public class EditTextValidKit extends ValidtorKit {

    public EditTextValidKit(Activity act) {
        super(act);
    }

    public EditTextValidKit showRectToast() {
        this.isRectToast = true;
        return this;
    }
    
    public EditTextValidKit notNull(EditText targetView, int labelRes) {
        notNull(targetView, targetView.getText().toString(), labelRes);
        return this;
    }

    public EditTextValidKit equalLength(EditText targetView, int lenth, int labelRes) {
        equalLength(targetView, targetView.getText().toString(), lenth, labelRes);
        return this;
    }

    public EditTextValidKit equalItem(EditText targetView1, EditText targetView2, int labelRes) {
        equalItem(targetView2, targetView1.getText().toString(), targetView2.getText().toString(), labelRes);
        return this;
    }

    public EditTextValidKit lessLength(EditText editView, int lessLenth, int labelRes) {
        lessLength(editView, editView.getText().toString(), lessLenth, labelRes);
        return this;
    }
    
    
    public EditTextValidKit biggerZero(EditText editView, int labelRes) {
        biggerZero(editView, editView.getText().toString(), labelRes);
        return this;
    }

    @Override
    public boolean getIsValid() {
        return super.getIsValid();
    }
    
    
    
}
