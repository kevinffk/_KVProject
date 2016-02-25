package com.kv.iprojectlib.ui.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kv.iprojectlib.R;


public class iRectToast extends Toast {

    public iRectToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, int resId, CharSequence text, int duration) {
        Toast result = new Toast(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.ip_toast, null);

        ImageView imageView = (ImageView) layout.findViewById(R.id.iv_icon);
        TextView textView = (TextView) layout.findViewById(R.id.tv_msg);

        imageView.setImageResource(resId);
        textView.setText(text);

        result.setView(layout);
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);

        return result;
    }

}