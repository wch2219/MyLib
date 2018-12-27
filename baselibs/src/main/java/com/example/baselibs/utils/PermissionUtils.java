package com.example.baselibs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    public static final String CALL_PHONE= "";
    public static final int CACAMERA = 10003;//相机
    public static final int WriteAndRead = 10001;//读写请求码

    /**
     * 判断是否有某项权限
     *
     * @param string_permission 权限
     * @param request_code      请求码
     * @return
     */
    public static boolean checkReadPermission(String[] string_permission, int request_code, Context context) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(context, string_permission[0]) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions((Activity) context, string_permission, request_code);
        }
        return flag;
    }


}
