package com.hg.paylib.wxpay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hg.paylib.listener.PayCallBackListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxPayUtils {
    private Context mContext;
    private static String APP_ID = "wxe10e77f4bbd13789";
    private IWXAPI wxapi;
    private WxPayUtils mWxPayUtils;

    public WxPayUtils getInstance(Context context) {

        this.mContext = context;
        regToWx();
        mWxPayUtils = new WxPayUtils();

        return mWxPayUtils;
    }

    /**
     * 注册微信
     */
    private void regToWx() {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        wxapi = WXAPIFactory.createWXAPI(mContext, APP_ID, true);
        //讲应用的appid注册到微信
        wxapi.registerApp(APP_ID);
    }



    /**
     * 微信支付
     * @param info //后台返回生成
     * @param backListener  完成 回调
     */
    public void WXPay(WxPayBean.BodyBean info, @NonNull PayCallBackListener.OnPayCallBackListener backListener) {
        PayCallBackListener.setOnPayCallBackListener(backListener);
        PayReq req = new PayReq();
        req.appId = info.getAppid();
        req.partnerId = info.getPartnerid();
        req.prepayId = info.getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = info.getNoncestr();
        req.timeStamp = info.getTimestamp();
        req.sign = info.getSign();
        if (wxapi.isWXAppInstalled()) {
            Log.i("wch","wwwwwww");
            wxapi.sendReq(req);
        } else {

            Toast.makeText(mContext, "没有安装微信", Toast.LENGTH_SHORT).show();
        }
    }

}
