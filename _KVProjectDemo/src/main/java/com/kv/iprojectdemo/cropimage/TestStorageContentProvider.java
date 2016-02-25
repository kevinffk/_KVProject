package com.kv.iprojectdemo.cropimage;

import android.net.Uri;

import com.kv.iprojectlib.plugin.cropimage.menu.InternalStorageContentProvider;

public class TestStorageContentProvider extends InternalStorageContentProvider {

    @Override
    public Uri getContentUri() {
        return Uri.parse("content://com.kv.iprojectdemo.plugin.cropphoto/");
    }

}
