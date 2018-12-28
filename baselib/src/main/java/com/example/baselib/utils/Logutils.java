package com.example.baselib.utils;

import android.util.Log;

public class Logutils {
    public static String Auth_log = "wch";
    public static void I(String tag,Object values){
        Log.i(tag,String.valueOf(values));
    }
    public static void I(Object values){
        Log.i(Auth_log,String.valueOf(values));
    }

     public static void E(String tag,Object values){
        Log.e(tag,String.valueOf(values));
    }

     public static void W(String tag,Object values){
        Log.w(tag,String.valueOf(values));
    }


}
