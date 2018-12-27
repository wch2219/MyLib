package com.example.picselect.weight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.picselect.R;
import com.example.picselect.activity.ClassPicListActivity;

/**
 * 上传图片
 */
public class PopuPicSeleteWindow implements PopupWindow.OnDismissListener, View.OnClickListener {
    private static Context mContext;
    private static PopuPicSeleteWindow mPopuPicSeleteWindow;
    private PopupWindow mPopupWindow;
    private ViewHolder mViewHolder;
    private static View mParent;
    public static PopuPicSeleteWindow newInstance(Context context,View parent) {
        mContext = context;
        mParent = parent;
        if (mPopuPicSeleteWindow == null) {
            mPopuPicSeleteWindow = new PopuPicSeleteWindow();
        }
        return mPopuPicSeleteWindow;
    }

    private PopuPicSeleteWindow() {
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popu_bottom, null);
        mViewHolder = new ViewHolder(view);
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.PopuCityStyle);
        mPopupWindow.setOnDismissListener(this);
        setBackgroundAlpha(0.7f);
        mViewHolder.mRlAlbum.setOnClickListener(this);
        mViewHolder.mRlCamera.setOnClickListener(this);
        mViewHolder.mTvClose.setOnClickListener(this);
        mPopupWindow.showAtLocation(mParent,Gravity.CENTER|Gravity.BOTTOM,0,0);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1f);
        mPopupWindow = null;
        mPopuPicSeleteWindow = null;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_camera) {
            UploadPicUtiles.opencamera((Activity) mContext,mContext.getPackageName());
        } else if (i == R.id.rl_album) {
            ((Activity) mContext).startActivity(new Intent(mContext,ClassPicListActivity.class));

        } else if (i == R.id.tv_close) {

        }
        mPopupWindow.dismiss();
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    private class ViewHolder {
        public View rootView;
        public RelativeLayout mRlCamera;
        public RelativeLayout mRlAlbum;
        public TextView mTvClose;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mRlCamera = (RelativeLayout) rootView.findViewById(R.id.rl_camera);
            this.mRlAlbum = (RelativeLayout) rootView.findViewById(R.id.rl_album);
            this.mTvClose = (TextView) rootView.findViewById(R.id.tv_close);
        }

    }
}
