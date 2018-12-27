package com.example.picselect.weight;

import com.example.picselect.presenter.Image;

import java.util.List;

public class UpPicListener {
    public static OnUpCallBackListener mOnUpCallBackListener;
    public interface OnUpCallBackListener{

        void upDatas(List<Image> images);
    }

    public static void setOnUpCallBackListener(OnUpCallBackListener onUpCallBackListener) {
        mOnUpCallBackListener = onUpCallBackListener;
    }
}
