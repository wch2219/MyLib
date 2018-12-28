package com.example.baselib.base;

import java.lang.ref.WeakReference;

public class IPresenter {
    protected WeakReference<IView> mViewReference;

    public IPresenter(IView view){
        this.mViewReference = new WeakReference<IView>(view);

    }

}
