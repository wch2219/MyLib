package com.example.picselect.presenter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.baselibs.base.IPresenter;
import com.example.baselibs.base.IView;
import com.example.picselect.View.SeletePicVIew;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SeletePicPresenter extends IPresenter {

    private final SeletePicVIew mSeletePicVIew;

    public SeletePicPresenter(IView view) {
        super(view);
        mViewReference = new WeakReference<>(view);
        mSeletePicVIew = (SeletePicVIew) mViewReference.get();
    }

    public void geClassList(Context context) {

        List<TreeMap<String, List<Image>>> mImages = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            TreeMap<String, List<Image>> map = new TreeMap<>();
            List<Image> images = new ArrayList<>();
            String path = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            String parents = new File(path).getParent();
            String parent = parents.substring(parents.lastIndexOf("/")).replace("/", "");
            boolean have = false;
            for (TreeMap<String, List<Image>> mImage : mImages) {
                List<Image> images1 = mImage.get(parent);
                if (images1 != null) {
                    have = true;
                    break;
                }
            }
            if (have) {
                continue;
            }

            Cursor cursor1 = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?", new String[]{parent}, null);
            while (cursor1.moveToNext()) {
                Image image = new Image();
                //获取图片的名称
                String path1 = cursor
                        .getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                long aLong = cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN));
                //获取图片的详细信息
                String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                String size = cursor
                        .getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));


                image.setDate(aLong);
                image.setPath(path1);
                image.setSize(size);
                images.add(image);
            }
            map.put(parent, images);
            mImages.add(map);
             cursor1.close();
        }
        mSeletePicVIew.backClassList(mImages);
        cursor.close();
    }
}
