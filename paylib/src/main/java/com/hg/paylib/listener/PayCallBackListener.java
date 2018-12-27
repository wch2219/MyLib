package com.hg.paylib.listener;

public class PayCallBackListener {
    public static OnPayCallBackListener mOnPayCallBackListener;


    public interface OnPayCallBackListener{
        void onPayFailed(String mee);

        void onPaySuccess(String mess);

    }

    public static void setOnPayCallBackListener(OnPayCallBackListener onPayCallBackListener) {
        mOnPayCallBackListener = onPayCallBackListener;
    }
}
