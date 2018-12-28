package com.example.baselib.weight.holder;

import android.support.annotation.DrawableRes;
import android.view.View;

public interface InterfaceViewHolder<H> extends View.OnLongClickListener, View.OnClickListener {

    <T extends View> T getView(int viewId);

    H setText(int viewId, CharSequence text);

    H setViewVisibility(int viewId, int visibility);


    H setImageResource(int viewId, int resourceId);

    H setTextColor(int viewId, int textColor);

    H setBackGroundRes(int viewId, @DrawableRes int resourceId);

    H setCheckBox(int viewId, boolean ischeck);


    /**
     * 关于事件的
     */
    H setOnClickListener(int viewId, View.OnClickListener listener);

    /**
     * 关于事件的
     */
    H setOnLongClickListener(int viewId, View.OnLongClickListener listener);
}
