package com.example.baselibs.utils;

import android.app.Activity;
import android.content.Context;

import com.example.baselibs.CookieBar.CookieBar;
import com.example.baselibs.R;


public class CookbarUtils {


    public static void show(Context context,String title,boolean isSuccess){
        CookieBar.Builder builder = CookieBar.build((Activity) context)
                .setDuration(1000)
                .setMessage(title);
        if (isSuccess) {
            builder.setIcon(R.drawable.success)
                    .setBackgroundColor(R.color.blue)
//                .setMessage(R.string.plain_cookie_message)
                    .show();
        }else{
            builder.setIcon(R.drawable.errortitle)
                .setBackgroundColor(R.color.buy_color)
                    .show();
        }
    }
}
