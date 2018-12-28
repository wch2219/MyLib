package com.example.baselib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baselib.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.Date;

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

    /**
     * 下载图片
     * @param context
     * @param url
     */
    public static void downImg(Context context, Object url){


    }
    public static void savePic(final Context context, String url) {



        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/YaopaiDown";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }


        OkGo.<File>post(url)
                .execute(new FileCallback(file.getAbsolutePath(), new Date().getTime() + ".jpg") {
                    @Override
                    public void onSuccess(Response<File> response) {
                        File absoluteFile = response.body().getAbsoluteFile();
                        Logutils.I(absoluteFile);
                        // 插入图库
                        long time = new Date().getTime();
                        try {
//                            MediaStore.Images.Media.insertImage(context.getContentResolver(), absoluteFile.getAbsolutePath(), time + "", null);
                            CookbarUtils.show(context, "保存成功", true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            CookbarUtils.show(context, "保存失败", false);
                        }

                        // 发送广播，通知刷新图库的显示
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + absoluteFile)));
                    }

                    @Override
                    public void onError(Response<File> response) {
                        CookbarUtils.show(context, "保存失败", false);
                    }
                });

    }
}
