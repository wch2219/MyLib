package com.hg.paylib.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.hg.paylib.listener.PayCallBackListener;

import java.util.Map;

public class AliPayUtils {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017040506559620";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "";
    private static Activity ctx;
    private static String info;
    private AliPayUtils mAliPayUtils;

    public AliPayUtils getInstance(Activity ctx, String info) {
        mAliPayUtils = new AliPayUtils();
        this.ctx = ctx;
        this.info = info;

        return mAliPayUtils;
    }

    public void startPay(@NonNull PayCallBackListener.OnPayCallBackListener backListener) {
        final String orderInfo = info;   // 订单信息
        PayCallBackListener.setOnPayCallBackListener(backListener);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ctx);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.d("ss", orderInfo);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Log.i("wch", msg.obj + "");
//                            @SuppressWarnings("unchecked")
//                            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                            /**
//                             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                             */
//                            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                            String resultStatus = payResult.getResultStatus();
//                            // 判断resultStatus 为9000则代表支付成功
//                            if (TextUtils.equals(resultStatus, "9000")) {
//                                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                                Toast.makeText(ctx, "支付成功", Toast.LENGTH_SHORT).show();
//                            } else {
//                                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                                Toast.makeText(ctx, "支付失败", Toast.LENGTH_SHORT).show();
//                            }
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus1 = authResult.getResultStatus();
                    Log.d("tag", resultStatus1);

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus1, "9000")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(ctx, "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//
                        PayCallBackListener.mOnPayCallBackListener.onPaySuccess("支付成功");
                        resultOnListener.getResult(true);
                    } else {
                        // 其他状态值则为授权失败
//                        Toast.makeText(ctx,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ctx, "支付失败", Toast.LENGTH_SHORT).show();
                        PayCallBackListener.mOnPayCallBackListener.onPayFailed("支付失败");
                        resultOnListener.getResult(false);

                    }
                    break;
                case SDK_AUTH_FLAG:

                    break;
            }


//            PayResult result = new PayResult((Map<String, String>) msg.obj);
//            Toast.makeText(ctx, result.getResult(),
//                    Toast.LENGTH_LONG).show();



        }

        ;
    };
    /**
     * 支付是否成功返回接口
     */
    private static PayResultOnListener resultOnListener;

    public interface PayResultOnListener{
        public void getResult(Boolean result);
    }

    public void setOnResultListener(PayResultOnListener resultOnListener){
        this.resultOnListener = resultOnListener;
    }
}
