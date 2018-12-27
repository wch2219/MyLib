package com.example.picselect.weight;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.baselibs.utils.CookbarUtils;
import com.example.baselibs.weight.CustomToolBar;
import com.example.picselect.R;
import com.example.picselect.activity.ClassPicListActivity;
import com.example.picselect.activity.PicListActivity;
import com.example.picselect.adapter.PicPpageAdapter;
import com.example.picselect.presenter.Image;

import java.util.ArrayList;
import java.util.List;

public class PopuWindowLargeic implements PopupWindow.OnDismissListener
        , CustomToolBar.OnLeftClickListener, CustomToolBar.OnRightClickListener
        , ViewPager.OnPageChangeListener,View.OnClickListener {

    private static Context mContext;
    private static PopuWindowLargeic mPopuPicSeleteWindow;
    private PopupWindow mPopupWindow;
    private ViewHolder mViewHolder;
    private int mCurrenPersion;
    private static List<Image> mImages;
    private ArrayList<Image> mCheckDatas;
    private PicPpageAdapter mPicPpageAdapter;

    public static PopuWindowLargeic newInstance(Context context, List<Image> images) {
        mContext = context;
        mImages = images;
        mPopuPicSeleteWindow = new PopuWindowLargeic();
        return mPopuPicSeleteWindow;
    }

    private PopuWindowLargeic() {
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popu_largepic, null);
        mViewHolder = new ViewHolder(view);
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.PopuCityStyle);
        mPopupWindow.setOnDismissListener(this);
        mViewHolder.customtoobar.setOnLeftClickListener(this);
        mViewHolder.customtoobar.setOnRightClickListener(this);
        mViewHolder.mViewpage.addOnPageChangeListener(this);
        mViewHolder.mBtnUppic.setOnClickListener(this);
        mPicPpageAdapter = new PicPpageAdapter(mContext, mImages);
        mViewHolder.mViewpage.setAdapter(mPicPpageAdapter);
    }

    public PopuWindowLargeic show(View parent, int currPosition) {
        this.mCurrenPersion = currPosition;
        checkNum();
        mViewHolder.mViewpage.setCurrentItem(currPosition);
        mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        return mPopuPicSeleteWindow;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mCurrenPersion = i;
        Image image = mImages.get(i);
        if (image.isCheck()) {
            mViewHolder.customtoobar.setRightSrc(R.drawable.icon_check_blue);
        } else {
            mViewHolder.customtoobar.setRightSrc(R.drawable.icon_check_gary);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTitleLeft() {
        mPopupWindow.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_uppic) {
            if (UpPicListener.mOnUpCallBackListener != null) {
                UpPicListener.mOnUpCallBackListener.upDatas(mCheckDatas);
                ((PicListActivity)mContext).mAppManager.finishActivity(ClassPicListActivity.class);
                ((Activity)mContext).finish();
            }
        }
    }

    @Override
    public void onRight() {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.itemSeletctPopu(mCurrenPersion);
        }
        itemSeletct(mCurrenPersion);
    }

    public void itemSeletct(int position) {
        if (checkNum() == 9) {
            CookbarUtils.show(mContext, "一次上传至多九张", false);
            return;
        }
        Image image = mImages.get(position);
        if (image.isCheck()) {
            mViewHolder.customtoobar.setRightSrc(R.drawable.icon_check_blue);
        } else {
            mViewHolder.customtoobar.setRightSrc(R.drawable.icon_check_gary);
        }
        mPicPpageAdapter.notifyDataSetChanged();
        checkNum();
    }

    private int checkNum() {
        int num = 0;
        mCheckDatas = new ArrayList<>();
        for (Image image : mImages) {
            if (image.isCheck()) {
                num++;
                mCheckDatas.add(image);
            }
        }
        mViewHolder.customtoobar.setTitle("已选择" + num + "个文件");
        return num;
    }

    private class ViewHolder {
        public View rootView;
        public ViewPager mViewpage;
        public Button mBtnUppic;
        public CustomToolBar customtoobar;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mViewpage = (ViewPager) rootView.findViewById(R.id.viewpage);
            this.mBtnUppic = (Button) rootView.findViewById(R.id.btn_uppic);
            this.customtoobar = rootView.findViewById(R.id.customtoobar);
        }

    }


    @Override
    public void onDismiss() {
        mPopupWindow = null;
        mPopuPicSeleteWindow = null;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {


        void itemSeletctPopu(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
