/**
 * Copyright JollyTech Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of JollyTech Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from JollyTech or an authorized sublicensor.
 */
package com.kv.iprojectlib.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;

/**
 * i Upload Utils.
 * @version   Revision History
 * <pre>
 * Author      Version       Date        Changes
 * Kevin Feng   1.0        2014年11月11日       Created
 *
 * </pre>
 * @since 1.
 */

public class iUploadUtils {

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /** 
     * 获得指定文件的byte数组 
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
