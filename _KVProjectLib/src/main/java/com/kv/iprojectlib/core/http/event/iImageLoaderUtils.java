package com.kv.iprojectlib.core.http.event;

import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class iImageLoaderUtils {

    public static ImageListener getImageLoaderListener(final ImageView view, final int defaultImageResId, final int errorImageResId, final String tag) {
        return new ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
            }

            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                if ((tag == null) || (tag != null && tag.equals(view.getTag()))) {
                    if (response.getBitmap() != null) {
                        view.setImageBitmap(response.getBitmap());
                    } else if (defaultImageResId != 0) {
                        view.setImageResource(defaultImageResId);
                    }
                }
            }
        };
    }
}
