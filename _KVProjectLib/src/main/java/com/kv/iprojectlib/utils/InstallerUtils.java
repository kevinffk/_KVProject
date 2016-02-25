package com.kv.iprojectlib.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class InstallerUtils {

    /**
     * 静默安装
     * @param file
     * @return
     */
    public static boolean slientInstall(File file) {
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
            dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + file.getPath());
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();

            // 代表成功
            if (value == 0) {
                result = true;
            } else if (value == 1) { // 失败
                result = false;
            } else { // 未知情况
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
    * 静默安装
    * @param file
    * @return
    */
    public static boolean slientUnInstall(String packName) {
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + packName);
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();

            // 代表成功
            if (value == 0) {
                result = true;
            } else if (value == 1) { // 失败
                result = false;
            } else { // 未知情况
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 安装apk.
     * @param file
     */
    public static void installApk(Context cxt, File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        cxt.startActivity(intent);

    }

    public static void uninstallApk(Context cxt, String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        //也可以这样写：Uri uri=Uri.parse("package:"+packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        cxt.startActivity(intent);
    }
}
