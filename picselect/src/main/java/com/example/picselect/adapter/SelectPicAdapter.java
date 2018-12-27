package com.example.picselect.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.baselibs.base.BaseRecyclerAdapter;
import com.example.baselibs.base.CommonViewHolder;
import com.example.baselibs.utils.DensityUtil;
import com.example.baselibs.utils.GlideUtils;
import com.example.picselect.R;
import com.example.picselect.presenter.Image;

import java.util.List;

public class SelectPicAdapter extends BaseRecyclerAdapter<Image> {
    public SelectPicAdapter(Context context, List<Image> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    protected void bindData(CommonViewHolder holder, Image data, final int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.iv_pic);
        RelativeLayout relativeLayout = holder.getView(R.id.rl_select);
        GlideUtils.show(mContext, imageView, data.getPath());
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        ViewGroup.LayoutParams lp1 = relativeLayout.getLayoutParams();
        int itemW = (screenWidth - (DensityUtil.dip2px(mContext, 30))) / 4;


        lp.width = itemW;
        lp.height = itemW;

        lp1.width = itemW;
        lp1.height = itemW;
        relativeLayout.setLayoutParams(lp1);

        imageView.setLayoutParams(lp);
        imageView.setMaxWidth(itemW);
        imageView.setMaxHeight(itemW);

        if (data.isCheck()) {
            holder.setViewVisibility(R.id.rl_select, View.VISIBLE);
            holder.setImageResource(R.id.iv_select, R.drawable.icon_selectblue);
        } else {
            holder.setViewVisibility(R.id.rl_select, View.GONE);
            holder.setImageResource(R.id.iv_select, R.drawable.icon_selectblack);
        }

        holder.setOnClickListener(R.id.iv_select, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.itemSeletct(position);
                }
            }
        });

        holder.setOnClickListener(R.id.iv_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.itemClick(position);
                }
            }
        });
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void itemClick(int position);

        void itemSeletct(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
