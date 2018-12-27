package com.hg.paylib.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hg.paylib.listener.PayCallBackListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

    //AndroidManifest 添加
// <!-- 微信 -->
//         <activity
//         android:name=".wxapi.WXPayEntryActivity"
//         android:exported="true"
//         android:label="@string/app_name"
//         android:launchMode="singleTop"
//         android:screenOrientation="portrait" />
//         <activity
/**
 *
 *
 *
 *
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    // private static final String TAG =
    // "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;
    private static final String APP_ID ="wx495743fabc362c92";
    private Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        ctx = this;
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        int type = req.getType();
        Log.i("wch","WX：type"+type);
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Toast.makeText(ctx, "支付成功", Toast.LENGTH_SHORT).show();
                PayCallBackListener.mOnPayCallBackListener.onPayFailed("支付成功");
                finish();
            } else {
                int errCode = resp.errCode;
                Log.i("wch","WX："+errCode);
                Toast.makeText(ctx, "支付失败", Toast.LENGTH_SHORT).show();
                PayCallBackListener.mOnPayCallBackListener.onPaySuccess("支付失败");
                finish();
            }
        }
        switch (resp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
//                Toast.makeText(ctx, "分享成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
//                Toast.makeText(this, "您取消了分享", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
                finish();
                //分享拒绝
                break;
        }

    }


}
