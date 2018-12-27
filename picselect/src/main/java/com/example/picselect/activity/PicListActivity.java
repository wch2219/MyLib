package com.example.picselect.activity;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.baselibs.base.BaseActivity;
import com.example.baselibs.weight.SpaceItemDecoration;
import com.example.picselect.R;
import com.example.picselect.adapter.SelectPicAdapter;
import com.example.picselect.presenter.Image;
import com.example.picselect.weight.PopuWindowLargeic;
import com.example.picselect.weight.UpPicListener;

import java.util.ArrayList;
import java.util.List;

public class PicListActivity extends BaseActivity implements View.OnClickListener,
        SelectPicAdapter.OnItemClickListener,PopuWindowLargeic.OnItemClickListener {
    private RecyclerView mRvList;
    private TextView mTvNum;
    private Button mBtnUppic;
    private List<Image> mImages;
    private SelectPicAdapter mSelectPicAdapter;
    private List<Image> mCheckDatas;
    private PopuWindowLargeic mPopuWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_list;
    }

    @Override
    protected void initView() {
        isBlack = true;
        setImmBar();
        mRvList = findViewById(R.id.rv_list);
        mTvNum = findViewById(R.id.tv_num);
        mBtnUppic = findViewById(R.id.btn_uppic);
        mBtnUppic.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        mImages = gePicList();
        mTvNum.setText("预览 (0/" + mImages.size() + ")");
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        mRvList.setLayoutManager(manager);
        mSelectPicAdapter = new SelectPicAdapter(mContext, mImages, R.layout.item_pic_selete);
        mRvList.setAdapter(mSelectPicAdapter);
        mSelectPicAdapter.setOnItemClickListener(this);
        mRvList.addItemDecoration(new SpaceItemDecoration(5, 10));
    }


    private List<Image> gePicList() {
        String parent = getIntent().getStringExtra("ParentUrl");
        List<Image> images = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?", new String[]{parent}, null);
        while (cursor.moveToNext()) {
            Image image = new Image();
            //获取图片的名称
            String path = cursor
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
            image.setPath(path);
            image.setSize(size);
            images.add(image);
        }

        return images;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_uppic) {
            if (UpPicListener.mOnUpCallBackListener != null) {
                UpPicListener.mOnUpCallBackListener.upDatas(mCheckDatas);
                mAppManager.finishActivity(ClassPicListActivity.class);
                finish();
            }
        }
    }

    @Override
    public void itemClick(int position) {
        mPopuWindow = PopuWindowLargeic.newInstance(mContext, mImages)
                .show(mBtnUppic, position);
        mPopuWindow.setOnItemClickListener(this);

    }

    @Override
    public void itemSeletct(int position) {
        Image image = mImages.get(position);
        image.setCheck(!image.isCheck());
        mSelectPicAdapter.notifyDataSetChanged();
        checkNum();
    }

    @Override
    public void itemSeletctPopu(int position) {
        Image image = mImages.get(position);
        image.setCheck(!image.isCheck());
        mSelectPicAdapter.notifyDataSetChanged();
        checkNum();
    }

    private int checkNum() {
        int num = 0;
        mCheckDatas = new ArrayList<>();
        for (Image image : mImages) {
            if (image.isCheck()) {
                num++;
                mCheckDatas.add(image);
            }
        }
        mTvNum.setText("预览 ("+num+"/" + mImages.size() + ")");
        return num;
    }




}
