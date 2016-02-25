/**
 * Copyright JollyTech Limited (c) 2015. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of JollyTech Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from JollyTech or an authorized sublicensor.
 */
package com.kv.iprojectlib.ui.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * iEditText
 * @version   Revision History
 * <pre>
 * Author      Version       Date        Changes
 * Kevin Feng   1.0        2015年1月9日       Created
 *
 * </pre>
 * @since 1.
 */

public class iEditText extends EditText {

    public iEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public iEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public iEditText(Context context) {
        super(context);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        CharSequence text = getText();
        if (text != null) {
            if (selStart != text.length() || selEnd != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }

        super.onSelectionChanged(selStart, selEnd);
    }
    
    

}
