package com.example.picselect.weight;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.picselect.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadPicUtiles {

    private static Uri uriForFile;//图片保存地址
    private static Uri tempUri;
    private static Bitmap photo;
    public static int OPEN_ALBM_GET_PIC = 2;//开启相册
    public static int OPEN_CAMERA_GET_PIC = 1;//开启相机
    public static int PIC_PHOTE_ZOOM = 5;//裁剪
    private static File outputImage;
    private static AlertDialog dialog;
    public static String mCurrentPhotoPath;
    public static boolean isCamera;
    public static int GET_Permission_Camer_code = 1001;//相册权限
    public static int GET_Permission_Albm_code = 1002;//相机权限
    public static Bitmap bm;
    private static Uri outputUri;

    /**
     * 显示Dialog
     */
    public static void showDia(final Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.create();
        View view = View.inflate(ctx, R.layout.item_dialog_head, null);
        TextView head_Txt = (TextView) view.findViewById(R.id.head_Txt);
        head_Txt.setText("上传图片");
        builder.setView(view);
        LinearLayout mLlPhotoalbum = (LinearLayout) view.findViewById(R.id.ll_photoalbum);
        LinearLayout mLlPhotograph = (LinearLayout) view.findViewById(R.id.ll_photograph);
        mLlPhotoalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album(ctx);
            }
        });
        mLlPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.CAMERA}, GET_Permission_Albm_code);
                }else {
                    isCamera = true;
                    dialog.dismiss();
                    opencamera((Activity) ctx, ctx.getPackageName());
                }
            }
        });
        dialog = builder.show();
    }

    /**
     * 打开相册
     * @param ctx
     */
    public static void album(Context ctx) {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, GET_Permission_Camer_code);
        } else {
            if (dialog != null) {

                dialog.dismiss();
            }
            isCamera = false;
            openAlbum((Activity) ctx,ctx.getPackageName());
//                    Intent intent = new Intent(ctx, ImageListActivity.class);
//                    ((Activity)ctx).startActivityForResult(intent,OPEN_ALBM_GET_PIC);

        }
    }

    /**
     * 相册获取返回的图片
     * @param data
     * @param
     * @param ctx
     * @return
     */
    public static String getPicAlbum(Intent data, Context ctx){

        String path = handleImageOnKitKat(data, ctx);
        return path;
    }
    /**
     * 获得拍照的图片
     * @param data
     * @param act
     * @return
     */
    public static String getPicCamera(Intent data,Activity act){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {

            photo = BitmapFactory.decodeFile(outputImage.getAbsolutePath());
        }else {
            photo = BitmapFactory.decodeFile(outputImage.getAbsolutePath());
        }
        String path = outputImage.getAbsolutePath();
        return path;
    }

    /**
     *
     * @param activity
     * @param data
     * @param wide//宽
     * @param hight//高
     */

    public static void startZoomPic(Activity activity,Intent data,int wide,int hight,int x,int y){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            if (isCamera) {
                cropPhoto(activity,wide,hight,x,y);

            }else{

                uriForFile = data.getData();
                cropPhoto(activity,wide,hight,x,y);
            }
        }else{
            if (isCamera) {
                startPhotoZoom(uriForFile,activity,wide,hight,x,y);
            }else{

                startPhotoZoom(data.getData(),activity,wide,hight,x,y);

            }
        }
    }

    /**
     * 获取裁剪完成后的图片
     * @param data
     * @return
     */
    public static Bitmap getCropPhoto(Intent data){
//
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            Log.i("wcc","wwww");
            photo = BitmapFactory.decodeFile(mCurrentPhotoPath);
//            cropPhoto(outputImage.getAbsolutePath(),act,"com.dlwx.offerwork");
        }else {

            setImageToView(data);
        }
        return photo;
    }



    /**
     * 打开相册
     */
    public static void openAlbum(Activity activity,String packname) {
        Intent intent = new Intent();
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            uriForFile = FileProvider.getUriForFile(activity, packname+".fileprovider",
                    new File(Environment.getExternalStorageDirectory()+"/images/","output_image.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(intent, OPEN_ALBM_GET_PIC);
        } else {

            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, OPEN_ALBM_GET_PIC);
        }


    }
    /**
     * 打开相机
     */
    public static void opencamera(Activity activity,String packname) {

            String fileName = packname.substring(packname.lastIndexOf(".")).replace(".","");
        //创建一个File对象用于存储拍照后的照片
        outputImage = new File(Environment.getExternalStorageDirectory()+"/"+fileName,new Date().getTime()+"output_image.jpg");
        if (!outputImage.getParentFile().exists()) {
            outputImage.getParentFile().mkdirs();
        }

        //判断Android版本是否是Android7.0以上
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            uriForFile = FileProvider.getUriForFile(activity, packname+".fileProvider", outputImage);
        }else{
            uriForFile=Uri.fromFile(outputImage);
        }
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        tempUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "image.jpg"));
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        activity.startActivityForResult(openCameraIntent, OPEN_CAMERA_GET_PIC);


    }
    /**
     * 7.0适配
     * @param ctx
     */
    public static void cropPhoto(Activity ctx,int wide,int hight,int x,int y) {

        File file = getCreateFile(ctx);
        Uri outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");



        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setDataAndType(uriForFile, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", x);
        intent.putExtra("aspectY", y);
        intent.putExtra("outputX", wide);
        intent.putExtra("outputY", hight);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);

        ctx.startActivityForResult(intent, PIC_PHOTE_ZOOM);
    }
    private static File getCreateFile(Context ctx) {

        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = ctx.getExternalCacheDir().getPath();
        } else {
            cachePath = ctx.getCacheDir().getPath();
        }
        File dir = new File(cachePath);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = format.format(new Date());
        String fileName = "robot_" + timeStamp + ".png";
        File file = new File(dir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
    }


    /**
     * 裁剪方法 7.0以下
     *
     * @param uri
     */
    private static void startPhotoZoom(Uri uri,Activity ctx,int wide,int hight,int x,int y) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        File file = getCreateFile(ctx);
        outputUri = Uri.fromFile(file);
        String path = getPath(ctx, uri);

        tempUri = Uri.parse(path);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 设置裁剪
        intent.putExtra("scale", true);
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", x);
        intent.putExtra("aspectY", y);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", wide);
        intent.putExtra("outputY", hight);
        intent.putExtra("return-data", true);
        ctx.startActivityForResult(intent, PIC_PHOTE_ZOOM);
    }

    /**
     * 返回获得相册的图片
     * @param data
     * @param
     */
    @TargetApi(19)
    private static String handleImageOnKitKat(Intent data, Context ctx) {

        String imagePath = null;
        Uri uri = outputUri;
        if (uri == null) {

            return "";

        }
        if (DocumentsContract.isDocumentUri(ctx, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的iD
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection,ctx);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null,ctx);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null,ctx);
        } else {
            //如果是file类型的uri，直接获取路径即可
            imagePath = uri.getPath();
        }
        uriForFile = Uri.parse(imagePath);


        return imagePath;
//        displayImage(imagePath, ctx);
    }
    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    private static String getImagePath(Uri uri, String selection,Context ctx) {
        String path = null;
        //通过 Uri和selection来获取 真实的图片路径

        Cursor cursor = ctx.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }



    /**
     * 7.0以下获取裁剪图片
     *
     *
     *
     *
     * @param data
     */
    private static void  setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        Log.i("sssssssss", extras + "ssssssssss");
        if (extras != null) {
            photo = extras.getParcelable("data");
        }
    }

    public static File getFilePath(Intent data,Context ctx){
        File file = null;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            file = new File(mCurrentPhotoPath);
        }else{

//            file = new File(String.valueOf(outputUri));
            String path = handleImageOnKitKat(data, ctx);
            file = new File(path);
        }
        return file;

    }
    public static File getFilePath1(Intent data,Context ctx){
        File file = null;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {

            if (isCamera) {
                file = new File(String.valueOf(uriForFile));
            }else{
                uriForFile = data.getData();
                file = new File(String.valueOf(uriForFile));
            }

        }else{
            String path = handleImageOnKitKat(data, ctx);
            file = new File(path);
        }
        return file;

    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // MediaStore (and general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // File
            return uri.getPath();
        }

        return null;
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
