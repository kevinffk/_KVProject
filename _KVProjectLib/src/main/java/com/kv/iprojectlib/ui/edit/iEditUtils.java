package com.kv.iprojectlib.ui.edit;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;

public class iEditUtils {

    private static final String WORD_AND_NUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";

    private static final String WORK_IDCARD = "0123456789X";

    public static void setNumOnly(EditText et) {
        if (et != null) {
            et.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        }
    }

    public static void setMaxLength(EditText et, int maxLength) {
        if (et != null) {
            et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
        }
    }

    public static void setPassword(EditText et) {
        if (et != null) {
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public static void setDigits(EditText et, String digits) {
        if (et != null) {
            et.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }

    public static void setDigitsWordAndNum(EditText et) {
        if (et != null) {
            et.setKeyListener(DigitsKeyListener.getInstance(WORD_AND_NUM));
        }
    }

    public static void setDigitsWordIDCard(EditText et) {
        if (et != null) {
            et.setKeyListener(DigitsKeyListener.getInstance(WORK_IDCARD));
        }
    }

    public static void setAmtType(final EditText et_amt) {
        setAmtType(et_amt, null);
    }

    public static void setAmtType(final EditText et_amt, final iEditCallback callback) {

        et_amt.addTextChangedListener(new TextWatcher() {
            private boolean isChanged = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {// ----->如果字符未改变则返回
                    return;
                }

                String str = s.toString();

                isChanged = true;
                String cuttedStr = str;

                /* 删除字符串中的dot */
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if ('.' == c) {
                        cuttedStr = str.substring(0, i) + str.substring(i + 1);
                        break;
                    }
                }

                /* 加上dot，以显示小数点后两位 */
                int csl = cuttedStr.length();
                if (2 < csl) {
                    cuttedStr = cuttedStr.substring(0, csl - 2) + "." + cuttedStr.substring(csl - 2);
                    csl = cuttedStr.length();
                    if (4 < csl) {
                        // 删除前面多余的0
                        for (int i = 0; i < csl - 3; i++) {
                            char c = cuttedStr.charAt(i);
                            if (i == 0 && c == '0' && i < csl - 4) {
                                cuttedStr = cuttedStr.substring(i + 1);
                            }
                        }
                    }
                } else {
                    cuttedStr = (2 == csl ? "0." : "0.0") + cuttedStr;
                }

                et_amt.setText(cuttedStr);
                et_amt.setSelection(et_amt.length());
                isChanged = false;

                if (callback != null) {
                    callback.onCallback();
                }
            }
        });

    }

    public static void setDiscountType(final EditText et_amt, final iEditCallback callback) {

        et_amt.addTextChangedListener(new TextWatcher() {
            private boolean isChanged = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {// ----->如果字符未改变则返回
                    return;
                }

                String str = s.toString();

                isChanged = true;
                String cuttedStr = str;

                /* 删除字符串中的dot */
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if ('.' == c) {
                        cuttedStr = str.substring(0, i) + str.substring(i + 1);
                        break;
                    }
                }

                /* 加上dot，以显示小数点后两位 */
                int csl = cuttedStr.length();
                if (2 < csl) {
                    cuttedStr = cuttedStr.substring(0, csl - 2) + "." + cuttedStr.substring(csl - 2);
                    csl = cuttedStr.length();
                    if (4 < csl) {
                        // 删除前面多余的0
                        for (int i = 0; i < csl - 3; i++) {
                            char c = cuttedStr.charAt(i);
                            if (i == 0 && c == '0' && i < csl - 4) {
                                cuttedStr = cuttedStr.substring(i + 1);
                            }
                        }
                        if (Float.parseFloat(cuttedStr) > 1) {
                            cuttedStr = "1.00";
                        }
                    }
                } else {
                    cuttedStr = (2 == csl ? "0." : "0.0") + cuttedStr;
                }

                et_amt.setText(cuttedStr);
                et_amt.setSelection(et_amt.length());
                isChanged = false;

                if (callback != null) {
                    callback.onCallback();
                }
            }
        });

    }

    public static interface iEditCallback {
        public void onCallback();
    }
}