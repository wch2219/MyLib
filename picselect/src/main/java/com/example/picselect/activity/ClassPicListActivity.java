package com.example.picselect.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.baselibs.base.BaseActivity;
import com.example.picselect.R;
import com.example.picselect.View.SeletePicVIew;
import com.example.picselect.adapter.SeleteClassPicAdapter;
import com.example.picselect.presenter.Image;
import com.example.picselect.presenter.SeletePicPresenter;

import java.util.List;
import java.util.TreeMap;

public class ClassPicListActivity extends BaseActivity implements SeletePicVIew {
    private RecyclerView mRvList;
    private SeletePicPresenter mSeletePicPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_pic_list;
    }

    @Override
    protected void initView() {
        mRvList = findViewById(R.id.rv_list);
        isBlack = true;
        setImmBar();
        mSeletePicPresenter = new SeletePicPresenter(this);
    }

    @Override
    protected void initData() {
        mSeletePicPresenter.geClassList(mContext);
    }

    @Override
    public void backClassList(List<TreeMap<String,List<Image>>> datas) {

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRvList.setLayoutManager(manager);
        mRvList.setAdapter(new SeleteClassPicAdapter(mContext,datas,R.layout.item_class_pic));
    }
}
