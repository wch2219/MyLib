package com.example.baselibs.base;

public interface IView {

    /**
     * 显示正在加载进度框
     */
    void showLoading();
    /**
     * 隐藏正在加载进度框
     */
    void hideLoading();

    void onError(String string);
}
