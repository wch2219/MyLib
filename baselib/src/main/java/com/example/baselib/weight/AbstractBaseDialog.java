package com.example.baselib.weight;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

public interface AbstractBaseDialog<T> {


    T setHeadView(@LayoutRes int layoutId);

    T setHeadView(View headView);

    T setBottomVIew(@LayoutRes int layoutId);

    T setBottomVIew(View bottomView);

    T removeView(View view);

    T removeView(@IdRes int viewId);
}
