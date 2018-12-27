package com.example.baselibs.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baselibs.R;

public class GlideUtils {

    public static void show(Context context, ImageView imageView, Object url) {
        Glide.with(context).load(url)
                .apply(new RequestOptions())
                .into(imageView);
    }


    public static void showHead(Context context, ImageView imageView, Object url) {
        Glide.with(context).load(url)
                .apply(new RequestOptions().error(R.drawable.icon_head).circleCrop())
                .into(imageView);


    }

}
