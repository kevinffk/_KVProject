package com.kv.iprojectlib.plugin.cropimage.menu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.kv.iprojectlib.plugin.cropimage.CropImage;
import com.kv.iprojectlib.utils.LogUtil;


public class CropPhotoHelper {

    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    public static final int REQUEST_CODE_GALLERY = 0x51;

    public static final int REQUEST_CODE_TAKE_PICTURE = 0x52;

    public static final int REQUEST_CODE_CROP_IMAGE = 0x53;

    private Activity mAct;

    private File mFileTemp;

    private iGetCropImageListener mListener;
    
    private int mAspectX;
    private int mAspectY;
    private int mOutX;
    private int mOutY;
    
    private int mTag = -1;

    public CropPhotoHelper(Activity act, int aspectX, int aspectY, int outX, int outY, iGetCropImageListener listener) {
        this(act, -1, aspectX, aspectY, outX, outY, listener);
    }
    
    public CropPhotoHelper(Activity act, int tag, int aspectX, int aspectY, int outX, int outY, iGetCropImageListener listener) {
        this.mAct = act;
        this.mAspectX = aspectX;
        this.mAspectY = aspectY;
        this.mOutX = outX;
        this.mOutY = outY;
        this.mListener = listener;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        } else {
            mFileTemp = new File(mAct.getFilesDir(), System.currentTimeMillis() + ".jpg");
        }
        this.mTag = tag;
    }

    /**
     * 打开图库.
     */
    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        mAct.startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }

    /**
     * 开始拍照.
     */
    public void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
                /*
                 * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
                 */
                mImageCaptureUri = InternalStorageContentProvider.mContentUri;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            mAct.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            LogUtil.e(e.toString());
        }
    }

    /**
     * 开始截图
     */
    public void startCropImage() {

        Intent intent = new Intent(mAct, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, mAspectX);
        intent.putExtra(CropImage.ASPECT_Y, mAspectY);
        intent.putExtra(CropImage.OUTPUT_X, mOutX);
        intent.putExtra(CropImage.OUTPUT_Y, mOutY);
        
        mAct.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_CODE_TAKE_PICTURE || requestCode == REQUEST_CODE_CROP_IMAGE)
                && resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        case REQUEST_CODE_GALLERY:
            try {

                InputStream inputStream = mAct.getContentResolver().openInputStream(data.getData());
                FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                copyStream(inputStream, fileOutputStream);
                fileOutputStream.close();
                inputStream.close();

                startCropImage();

            } catch (Exception e) {
                LogUtil.e("Error while creating temp file" + e.toString());
            }

            break;
        case REQUEST_CODE_TAKE_PICTURE:

            startCropImage();
            break;
        case REQUEST_CODE_CROP_IMAGE:

            String path = data.getStringExtra(CropImage.IMAGE_PATH);
            if (path == null) {

                return;
            }
            if (mListener != null) {
                mListener.getCropImagePath(mTag, mFileTemp.getPath());
            }
            break;
        }
    }
    
    public void onDestory() {
        if (mFileTemp != null && mFileTemp.exists()) {
            mFileTemp.delete();
        }
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public static interface iGetCropImageListener {
        
        public void getCropImagePath(int tag, String path);
    }
}
