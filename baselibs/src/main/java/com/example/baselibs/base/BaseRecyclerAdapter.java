package com.example.baselibs.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.TreeMap;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected LayoutInflater layoutInflater;

    protected List<T> dataList;
    protected T mTreeMap;
    protected int layoutId;
    protected Context mContext;

    public BaseRecyclerAdapter(Context context, List<T> dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
        this.mContext = context;
    }

    public BaseRecyclerAdapter(Context context, T dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mTreeMap = dataList;
        this.layoutId = layoutId;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        bindData(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return ((TreeMap<Object, Object>) mTreeMap).size();
        }
        return dataList.size();
    }

    protected abstract void bindData(CommonViewHolder holder, T data, int position);

    public void addData(List<T> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<T> getData() {

        return dataList;
    }

    public void clearData() {
        this.dataList.clear();
    }

    public void removeData(int postion) {
        this.dataList.remove(postion);
        this.notifyDataSetChanged();
    }
}
