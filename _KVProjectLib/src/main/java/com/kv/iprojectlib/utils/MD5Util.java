package com.kv.iprojectlib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 Util
 * @version   Revision History
 * <pre>
 * Author      Version       Date        Changes
 * Kevin Feng   1.0        2011-5-19       Created
 *
 * </pre>
 * @since 1.
 */

public class MD5Util {

    private static final String MD5 = "MD5";

    private static final String ZERO = "0";
    
    private static final String TAG = MD5Util.class.getSimpleName();

    private static String getEncryptString(String src, boolean is16Byte) {
        try {
            String target;
            MessageDigest md = MessageDigest.getInstance(MD5);
            md.update(src.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < b.length; j++) {
                i = b[j];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append(ZERO);
                }
                sb.append(Integer.toHexString(i));
            }
            target = sb.toString();
            return is16Byte ? target.substring(8, 24) : target;
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * Encrypt String to byte 16
     * @param src
     * @return
     */
    public static String getEncryptStringBy16(String src) {
        return getEncryptString(src, true);
    }

    /**
     * Encrypt String to byte 32
     * @param src
     * @return
     */
    public static String getEncryptStringBy32(String src) {
        return getEncryptString(src, false);
    }
}
