package com.example.baselib.weight.holder;

import android.support.annotation.DrawableRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class AbstractViewHolder<H> implements InterfaceViewHolder<H> {

    private View mView;
    private H  mH;
    private SparseArray<View> viewSparseArray;
    public AbstractViewHolder(View view) {
        this.mView = view;
        viewSparseArray = new SparseArray<>();
        mH = (H) this;
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = mView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }


    public H setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return mH;
    }

    public H setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return mH;
    }

    public H setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return mH;
    }

    public H setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return mH;
    }
    public H setBackGroundRes(int viewId, @DrawableRes int resourceId){
        View view = getView(viewId);
        view.setBackgroundResource(resourceId);

        return mH;
    }

    public H setCheckBox(int viewId, boolean ischeck) {
        CheckBox checkBox = getView(viewId);

        checkBox.setChecked(ischeck);

        return mH;
    }
}
